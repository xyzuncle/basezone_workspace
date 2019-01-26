package com.kq.auth.social.qq.connection;

import com.kq.auth.social.qq.adapter.QQAdapter;
import com.kq.auth.social.qq.api.QQ;
import com.kq.auth.social.qq.provider.QQServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 构建 链接工厂 继承OAuth2ConnectionFactory 这个类
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ>{

    /**
     *
     * @param providerId  提供商的唯一标识 也就是openid
     * @param appId   应用id
     * @param appSecrt  应用的秘钥
     *
     * @desc appid 和 appSecret 都是需要注册的，
     *       比如QQ需要注册，微信需要注册 appid,和appSecrect
     *       但是自己的oauth服务，就是自己简单的配置了
     */
    public QQConnectionFactory(String providerId, String appId, String appSecrt) {

        //传入一个服务构造器 和一个 QQ信息的适配器，

        super(providerId, new QQServiceProvider(appId,appSecrt), new QQAdapter());
    }
}
