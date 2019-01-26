//package com.kq.perimission.config;
//
///*import com.kq.perimission.token.RedisTemplateTokenStore;*/
//import com.kq.common.util.jwt.RsaKeyHelper;
//import com.kq.perimission.token.RedisTemplateTokenStore;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.sql.DataSource;
//import java.security.KeyPair;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//
//
//
//@Component
//@Configuration
//@EnableResourceServer
///*@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)*/ //这个作用是为了security的注解权限用的。
//public class ResourceConfig extends ResourceServerConfigurerAdapter {
//
//    @Autowired(required = false)
//    private AuthenticationEntryPoint authenticationEntryPoint;
//
//    //初始化工具类
//    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();
//
//    @Value("${jwt.pub.path}")
//    private String pubKeyPath;
//
//    //私钥
//    @Value("${jwt.pri.path}")
//    private String priKey;
//
///*    @Autowired(required = false)
//    private RedisClientDetailsService clientDetailsService ;*/
//
//    @Autowired
//    private RedisConnectionFactory connectionFactory;
//
//   /* @Autowired(required = false)
//    private RedisClientDetailsService clientDetailsService ;*/
//
//    @Bean
//    public RedisTokenStore tokenStore() {
//        return new RedisTokenStore(connectionFactory);
//    }
//
//
//    @Autowired
//    private OAuth2WebSecurityExpressionHandler expressionHandler;
//    @Autowired
//    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;
//
//    @Autowired(required = false)
//    private RemoteTokenServices remoteTokenServices;
//
//    //对应oauth_client_details的 resource_ids字段 如果表中有数据 client_id只能访问响应resource_ids的资源服务器
//    private static final String RESOURCE_ID = "perimission";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        //定义token存储的方式
//       /* resources.tokenStore(redisTokenStore);*/
//        resources.resourceId(RESOURCE_ID).stateless(true);
//        //定义了token的处理逻辑类
//        resources.tokenServices(defaultTokenServices());
//        //不加这个handler，表达式无法使用，
//        //这个表达式 就是65行的配置，所有的请求都会走
//        //baseElement这个实现类，具体逻辑自己处理，如果符合就true,
//        //如果不符合就false
//
//        resources.expressionHandler(expressionHandler);
//        resources.accessDeniedHandler(oAuth2AccessDeniedHandler);
//        //没有经过认证统一作出错误页面处理
//        resources.authenticationEntryPoint(authenticationEntryPoint) ;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//        // @formatter:off
//        http
//                .authorizeRequests().anyRequest().authenticated()
//                .and().
//                exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//        // @formatter:on
//
//
//
//
//
//    }
//
//    private static class OAuthRequestedMatcher implements RequestMatcher {
//        public boolean matches(HttpServletRequest request) {
//            String auth = request.getHeader("Authorization");			// Determine if the client request contained an OAuth Authorization
//             boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
//             boolean haveAccessToken = request.getParameter("access_token") != null;
//             return haveOauth2Token || haveAccessToken;
//        }
//    }
//
//
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter()  {
//        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
//            //	/***
//            //	* 重写增强token方法,用于自定义一些token返回的信息
//            //	*/
//            //	@Override
//            //	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//                    //	String userName = authentication.getUserAuthentication().getName();
//                    //	User user = (User) authentication.getUserAuthentication().getPrincipal();// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
//                    //	/** 自定义一些token属性 ***/
//                    //	final Map<String, Object> additionalInformation = new HashMap<>();
//                    //	additionalInformation.put("userName", userName);
//                    //	additionalInformation.put("roles", user.getAuthorities());
//                    //	((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
//                    //	OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
//                    //	return enhancedToken;
//            //	}
//        };
//        //accessTokenConverter.setSigningKey("123");// 测试用,授权服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
//
//       /* KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(pubKeyPath), "123456".toCharArray())
//                .getKeyPair("kq_key");*/
//
//        PrivateKey privateKey = null;
//        PublicKey publicKey = null;
//        try {
//            privateKey = rsaKeyHelper.getPrivateKey(priKey);
//            publicKey = rsaKeyHelper.getPublicKey(pubKeyPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        KeyPair keyPair = new KeyPair(publicKey,privateKey);
//        accessTokenConverter.setKeyPair(keyPair);
//        return accessTokenConverter;
//    }
//
//    /**
//     * 创建一个默认的资源服务token
//     *
//     * @return
//     */
//    @Bean
//    public ResourceServerTokenServices defaultTokenServices() {
//        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
//        defaultTokenServices.setTokenStore(tokenStore());
//        //指定clientDetailsService，这样才可以验证resourceid,
//        defaultTokenServices.setClientDetailsService(clientDetails());
//        return defaultTokenServices;
//    }
//
//    @Resource
//    DataSource dataSource;
//
//    @Bean
//    public ClientDetailsService clientDetails() {
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//
//
//
//}
