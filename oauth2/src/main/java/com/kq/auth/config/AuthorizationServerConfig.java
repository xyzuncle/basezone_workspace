package com.kq.auth.config;



import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.common.util.jwt.RsaKeyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

/**
 * 配置outh的 授权模式
 *   authorization_code：授权码类型。
     implicit：隐式授权类型。
     password：资源所有者（即用户）密码类型。
     client_credentials：客户端凭据（客户端ID以及Key）类型。
     refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。*/



@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private ApplicationContext applicationContext;

    //初始化工具类
    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

    //私钥
    @Value("${jwt.pri.path}")
    private String priKey;

    @Value("${jwt.pub.path}")
    private String pubKey;

  /*  @Autowired(required = false)
    private JwtTokenStore jwtTokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;*/

    @Autowired(required = false)
    private RandomValueAuthorizationCodeServices authorizationCodeServices ;

    @Autowired
    private RedisConnectionFactory connectionFactory;

/*    @Autowired(required = false)
    private RedisClientDetailsService clientDetailsService ;*/

    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    @Autowired
    private BaseUserServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  /*  @Bean(name="daoAuhthenticationProvider")
    public AuthenticationProvider daoAuhthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }*/


    @Resource
    DataSource dataSource;

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 这个是管理认证多个provider的管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired(required = false)
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //把clientId的管理和redis结合在一起

        String finalpassword = "{bcrypt}"+passwordEncoder().encode("123456");
      /*  clients.inMemory()
                //授权码模式
                .withClient("zyx")
                .scopes("openid")
                .secret("123456")
                .autoApprove(true) //这个是默认用户同意授权
            *//*    .authorities("ROLE_USER") //确认是否是读写权限*//*
                //client_credentials 只匹配client和secret
                //password 需要用户的用户名和密码
                .authorizedGrantTypes("authorization_code","refresh_token","password")
                .and()
                //密码模式
                .withClient("android")
                .secret("123456")
                .scopes("xx")
                .authorizedGrantTypes("password","refresh_token");
*/
        clients.withClientDetails(clientDetails());
     /*   clientDetailsService.loadAllClientToCache();*/
    }
    //主要控制认证路径，是否可以被检查，
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //token acesss is permiAll()
        security.tokenKeyAccess("permitAll()") /// url:/oauth/token_key,exposes
                /// public key for token
                /// verification if using
                /// JWT tokens
                // check token is  authenitcated
                .checkTokenAccess("isAuthenticated()")// url:/oauth/check_token
                // allow check token
                .allowFormAuthenticationForClients();


        // security.allowFormAuthenticationForClients();
        //// security.tokenKeyAccess("permitAll()");
        // security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 认证入口点
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //利用tokeneEnhanceChain来为token 增加额外信息
       /* Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                new ArrayList<>(tokenEnhancers));*/

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
                //在refres_token 中包含一个检查，检查你的这个账号是否有效
            /*    .tokenEnhancer(tokenEnhancerChain);*/
                // 当你选择了资源所有者密码的模式，需要注入一个用户对象

                //这个关联service 主要是为了刷新token而用的
              /*  .userDetailsService(userDetailsService);*/

        endpoints.authorizationCodeServices(authorizationCodeServices);
      /*  endpoints.exceptionTranslator(webResponseExceptionTranslator);*/
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws Exception {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String userName = authentication.getUserAuthentication().getName();
                final Map<String, Object> additionalInformation = new HashMap<>();
                additionalInformation.put("user_name", userName);
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken token = super.enhance(accessToken, authentication);
                return token;
            }
        };
        //秘钥的生成方式，不能借鉴别的方法吗？
       /* KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(priKey), "123456".toCharArray())
                .getKeyPair("kq_key");
*/
        PrivateKey privateKey = rsaKeyHelper.getPrivateKey(priKey);
        PublicKey publicKey = rsaKeyHelper.getPublicKey(pubKey);

        KeyPair keyPair = new KeyPair(publicKey,privateKey);
        converter.setKeyPair(keyPair);
        return converter;
    }

 }
