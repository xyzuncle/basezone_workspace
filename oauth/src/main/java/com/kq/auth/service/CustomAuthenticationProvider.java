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
 *
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

        //根据不同的用户类型进行，进行不同用户密码效验
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
        //如果是老用户，由于增加了字段造成的null 也需要被过滤掉，默认是security的验证方法
        else{
            super.additionalAuthenticationChecks(userDetails, authentication);
        }

    }



}
