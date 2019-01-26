package com.kq.auth.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 声明认证配置的提供器
 */
public interface AuthorizeConfigProvider {
    /**
     * 这个接口接受securityConfig中的http中的URL 作为参数，供AuthorizeManager统一管理
     * @param config
     */
    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
