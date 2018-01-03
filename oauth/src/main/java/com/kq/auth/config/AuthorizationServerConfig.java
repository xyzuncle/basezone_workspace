package com.kq.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 配置outh的 授权模式
 *   authorization_code：授权码类型。
     implicit：隐式授权类型。
     password：资源所有者（即用户）密码类型。
     client_credentials：客户端凭据（客户端ID以及Key）类型。
     refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 设置client 的配置方式，设置client id 和 secret
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        //基于内存的授权，后期或改成基于服务配置中心的管理
        clients.inMemory()
                .withClient("android")
                .scopes("xx") //此处的scopes是无用的，可以随意设置
                .secret("android")
                //应用直接都是受信任的(都是由一家公司开发的，本例子使用)
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .and()
                .withClient("webapp")
                .scopes("xx")
                .authorizedGrantTypes("implicit"); //简要模式，只用clientId和secret
    }


    /**
     * 定义了token的访问配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}
