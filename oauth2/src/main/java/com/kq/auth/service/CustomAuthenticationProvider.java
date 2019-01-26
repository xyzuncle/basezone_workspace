package com.kq.auth.service;


import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.common.exception.UserException.UserInvaildException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 *  重写默认的附加检查方法，根据不同的用户类型，按照不同的密码解析类型来解析
 */

public class CustomAuthenticationProvider extends DaoAuthenticationProvider  {



    @Autowired
    private SaltSource saltSource;


    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private UserDetailsService userDetailsService;



    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        System.out.println(authentication.getCredentials());
        //根据不同的用户类型进行，进行不同用户密码效验
        //由于整合了social 这个地方不能强转了

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
                super.additionalAuthenticationChecks(userDetails, authentication);
            }
        }

    }



}
