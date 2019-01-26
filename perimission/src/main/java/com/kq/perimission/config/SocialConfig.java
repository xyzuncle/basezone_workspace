/*
package com.kq.perimission.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

*/
/**
 *  这个应该是一个总的配置类
 *//*

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Resource
    DataSource dataSource;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //dataSource 拿到当前项目的的数据源，通过依赖注入
        //connectionFactoryLocator 利用参数中的链接器，以为可能有很多链接工厂，比如QQ，微信，等，通过
        //各个工厂的配置来判断
        //TextEncryptor 插入数据库的数据 进行加解密 这里为了学习，先不加密，为了更好的观察，后面自己改掉
        //改成带盐值的
        return new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
    }


    */
/**
     * 这个类的目的就是为了获取social 第三方用户信息的数据
     * @param connectionFactoryLocator
     * @return
     *//*

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
*/
