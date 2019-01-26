package com.kq.auth.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 收集所有的AuthorizePrivode
 */
@Component
public class CustomAuthorizeManager implements AuthorizeManager{

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    /**
     * 收集所有的AuthorizeProvider这个类的实现，然后循环调用每个实现类的cofig方法
     * @param config
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for(AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders){
            authorizeConfigProvider.config(config);
        }
        //token正确登录
        config.anyRequest().authenticated() ;
    }
}
