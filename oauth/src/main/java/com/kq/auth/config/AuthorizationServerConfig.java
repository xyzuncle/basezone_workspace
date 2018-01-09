package com.kq.auth.config;

import com.kq.auth.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserApprovalHandler userApprovalHandler1;

    /**
     * 设置client 的配置方式，设置client id 和 secret
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //基于内存的授权，后期或改成基于服务配置中心的管理
       /* clients.inMemory()
                .withClient("acme")
                .secret("acmesecret")
                .scopes("openid")
                .autoApprove(true) //这个什么意思
                .authorities("ROLE_ADMIN","ROLE_USER") //确认是否是读写权限
                //client_credentials 只匹配client和secret
                //password 需要用户的用户名和密码
                .authorizedGrantTypes("implicit","refresh_token","password","authorization_code","client_credentials")
                .and()
                .withClient("android")
                .scopes("xx")
                .authorizedGrantTypes("implicit");*/

        clients.inMemory()
                .withClient("android")
                .scopes("xx")
                .secret("android")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .and()
                .withClient("webapp")
                .scopes("xx")
                .authorizedGrantTypes("implicit");
    }


    /**
     * 用来配置令牌断端点的安全约束
     * 哪些允许访问，哪些允许不访问
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
               // .checkTokenAccess("isAuthenticated()");
               .allowFormAuthenticationForClients();//

    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    UserDetailsService customUserService(){ //2
        return new CustomUserService();
    }


    /**
     * 声明授权和token的端点以及token的服务的一些配置信息，比如采用什么存储方式、token的有效期等
     * 当一个令牌被创建了，你必须对其进行保存，这样当一个客户端使用这个令牌对资源服务进行请求的时候才能够引用这个令牌。
     当一个令牌是有效的时候，它可以被用来加载身份信息，里面包含了这个令牌的相关权限。
        implicitGrantService：这个属性用于设置隐式授权模式，用来管理隐式授权模式的状态。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints

                .tokenStore(tokenStore())
                //在refres_token 中包含一个检查，检查你的这个账号是否有效
              /*  .userDetailsService(customUserService())*/
                // 当你选择了资源所有者密码的模式，需要注入一个用户对象
                .authenticationManager(authenticationManager);



    }
}
