package com.kq.auth.validate.username.provider;

import com.kq.auth.domain.BaseUser;
import com.kq.auth.validate.username.token.UPAuthenticationToken;
import com.kq.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * 提供usernamepasswordProvider 进行usernamepassword认证，
 * 原先使用手机号进行验证的，现在为了更好的封装，单独一个fiflter
 */
public class UPProvider implements AuthenticationProvider {

    protected final Log logger = LogFactory.getLog(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private UserDetailsService userDetailsService;

    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SaltSource saltSource;

    //防止用户未找到的错误
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private String userNotFoundEncodedPassword;

    /**
     * 真正认证的方法
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UPAuthenticationToken upAuthenticationToken = (UPAuthenticationToken) authentication;
        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername((String) upAuthenticationToken.getPrincipal());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }

        if (user == null) {
            throw new BaseException("无法获取用户信息");
        }

        try {
            preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user,
                    (UPAuthenticationToken) authentication);
        }catch (Exception e){
            e.printStackTrace();
        }




        UPAuthenticationToken authenticationResult = new UPAuthenticationToken(user, user.getAuthorities());

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UPAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    /**
     * 这里是附加比对的方法，可用户不同的用户密码进行比较
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UPAuthenticationToken authentication)
            throws AuthenticationException {

        BaseUser user = (BaseUser) userDetails;
        //如果是资源卫星用户，采用SHA1的算法得到的秘钥,那么开始
        if(StringUtils.isNotBlank(user.getSatelliteType())){
            if(user.getSatelliteType().equals("1")){
                Object salt = null;

                if (this.saltSource != null) {
                    salt = this.saltSource.getSalt(userDetails);
                }

                if (authentication.getCredentials() == null) {
                    logger.debug("Authentication failed: no credentials provided");

                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                }

                String presentedPassword = authentication.getCredentials().toString();
                ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
                if (!shaPasswordEncoder.isPasswordValid(userDetails.getPassword(),
                        presentedPassword, salt)) {
                    logger.debug("Authentication failed: password does not match stored value");

                    throw new BadCredentialsException(messages.getMessage(
                            "资源中心密码不对！",
                            "Bad credentials"));
                }

            }else{
                //如果不是资源卫星用户，就走默认的security盐值的方法
                Object salt = null;

                if (this.saltSource != null) {
                    salt = this.saltSource.getSalt(userDetails);
                }

                if (authentication.getCredentials() == null) {
                    logger.debug("Authentication failed: no credentials provided");

                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                }
                //判断密码是否正确
                String presentedPassword = authentication.getCredentials().toString();

                if (!passwordEncoder.isPasswordValid(userDetails.getPassword(),
                        presentedPassword, salt)) {
                    logger.debug("Authentication failed: password does not match stored value");

                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                }
            }
        }




    }


    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setPasswordEncoder(Object passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

        if (passwordEncoder instanceof PasswordEncoder) {
            setPasswordEncoder((PasswordEncoder) passwordEncoder);
            return;
        }

        if (passwordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
            final org.springframework.security.crypto.password.PasswordEncoder delegate = (org.springframework.security.crypto.password.PasswordEncoder) passwordEncoder;
            setPasswordEncoder(new PasswordEncoder() {
                public String encodePassword(String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }

                private void checkSalt(Object salt) {
                    Assert.isNull(salt,
                            "Salt value must be null when used with crypto module PasswordEncoder");
                }
            });

            return;
        }

        throw new IllegalArgumentException(
                "passwordEncoder must be a PasswordEncoder instance");
    }

    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

        this.userNotFoundEncodedPassword = passwordEncoder.encodePassword(
                USER_NOT_FOUND_PASSWORD, null);
        this.passwordEncoder = passwordEncoder;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }



    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                logger.debug("User account is locked");

                throw new LockedException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.locked",
                        "User account is locked"));
            }

            if (!user.isEnabled()) {
                logger.debug("User account is disabled");

                throw new DisabledException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.disabled",
                        "User is disabled"));
            }

            if (!user.isAccountNonExpired()) {
                logger.debug("User account is expired");

                throw new AccountExpiredException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.expired",
                        "User account has expired"));
            }
        }
    }

}


