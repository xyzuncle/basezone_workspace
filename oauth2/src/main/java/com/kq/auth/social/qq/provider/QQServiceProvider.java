package com.kq.auth.social.qq.provider;

import com.kq.auth.social.qq.api.QQ;
import com.kq.auth.social.qq.api.QQImpl;
import com.kq.auth.social.qq.template.QQTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 创建QQ的serverprovider
 * 为了后续的链接工厂
 *
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    //appid 可以是一个全局变量
    private String appId;
    //基于oauth2协议，获取QQ 认证的路径，比如为了获取code,
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    // 基于oauth2协议，获取token的地址，基于code
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     *
     * @param appId   一个应用的ID，可以是全局的。就是申请QQ服务的程序的ID，有服务起派发
     * @param appSecret 申请QQ服务的程序的秘钥 服务器派发
     */
    public QQServiceProvider(String appId,String appSecret) {
        //URL_AUTHORIZE 就是QQ的认证路径，
        //URL_ACCESS_TOKEN 是QQ的token获取路径
        super(new QQTemplate(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        //很重要 把appid 放入作用域
        this.appId = appId;
    }

    /**
     *
     * @param accessToken
     *            是默认的API实现类携带的accessToken参数
     * @return
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
