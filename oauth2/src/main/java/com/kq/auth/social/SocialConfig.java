package com.kq.auth.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;

import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *  这个应该是一个总的配置类
 */
@Configuration
@EnableSocial
@Order(1)
public class SocialConfig extends SocialConfigurerAdapter {

    @Resource
    DataSource dataSource;

    //这个是自定义处理的processUrl，是需要被拦截的请求,
    //默认是  /auth 为了跟回调函数配置的一致，我需要更改这个地址跟回调域一样
    @Value("${qq.basezone.processUrl}")
    String processUrl;

    //注册页面
    @Value("${qq.basezone.registerUrl}")
    String signinUrl;

    @Autowired(required = false)
    public ConnectionSignUp connectionSignUp;

    @Override
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //dataSource 拿到当前项目的的数据源，通过依赖注入
        //connectionFactoryLocator 利用参数中的链接器，以为可能有很多链接工厂，比如QQ，微信，等，通过
        //各个工厂的配置来判断
        //TextEncryptor 插入数据库的数据 进行加解密 这里为了学习，先不加密，为了更好的观察，后面自己改掉
        //改成带盐值的
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());

        //如果想默认注册用户，就需要配置
        if(connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }

        return repository;
    }


    /**
     * 这个初始化类，是为了在security的配置文件中注入这个配置类
     * @return
     */
    @Bean
    public SpringSocialConfigurer SocialSecurityConfig() {
        //这里返回重写过URL的配置，否则会发生跳转地址非法
        CustomSpringSocialConfigurer configurer = new CustomSpringSocialConfigurer(processUrl);
        //解决第一次关联用户注册的问题,第一次没有用户，就直接调转到注册页面
        //security同时要设置不拦截
        configurer.signupUrl(signinUrl);
        return configurer;
    }

    /**
     * 这个类的目的就是为了获取social 第三方用户信息的数据
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
