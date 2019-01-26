package com.kq.auth.social.qq.config;

import com.kq.auth.social.qq.connection.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * QQ的连接工厂类
 * 如果前缀是qq.basezone ，并且 qq.basezone.appid 不为空，这个配置生效
 */
@Configuration
@ConditionalOnProperty(prefix = "qq.basezone",name = "appid")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Value("${qq.basezone.appid}")
    String appId;
    @Value("${qq.basezone.appSecret}")
    String appSecret;
    @Value("${qq.basezone.provider-id}")
    String providerId;

    /**
     * 创建链接工厂，返回我们自己写的QQ工厂
     *
     * @return
     */

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new QQConnectionFactory(providerId,appId,appSecret);
    }
}
