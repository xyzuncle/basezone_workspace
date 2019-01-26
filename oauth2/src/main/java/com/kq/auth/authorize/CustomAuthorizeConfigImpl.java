package com.kq.auth.authorize;

import com.kq.common.properties.PermitUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限模块实现这个自己定义的认证接口
 */
@Component
@Order(Integer.MAX_VALUE - 1)
@EnableConfigurationProperties(PermitUrlProperties.class)
public class CustomAuthorizeConfigImpl implements AuthorizeConfigProvider{


    @Autowired(required = false)
    private PermitUrlProperties permitUrlProperties;

    /**
     * 把securityConfig 中的配置 抽象到了这里
     * @param config
     */
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        //免token登录的设置，都抽出去
      /*  config.antMatchers("\"/oauth/**\", \"/auth/**\"," +
                " \"/qqLogin/**\", \"/usersave\", \"/home\", \"/me\",\"/loginToken\"," +
                "\"/images/captcha\"").permitAll();*/
        // 免token登录设置
        config.antMatchers(permitUrlProperties.getOauth_urls()).permitAll();

        //前后端分离需要带上
        config.antMatchers(HttpMethod.OPTIONS).permitAll();

        return true;
    }
}
