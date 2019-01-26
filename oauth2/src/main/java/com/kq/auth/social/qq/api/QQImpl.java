package com.kq.auth.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kq.auth.domain.QQUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;


public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    //获取openid的地址,需要taccess_token，用%s,来代替
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    //获取userinfo的地址
    //https://graph.qq.com/user/get_user_info?
    //
    //access_token=*************&
    //
    //oauth_consumer_key=12345&  申请QQ登录成功后，分配给应用的ID，比如什么程序去访问QQ，他要知道谁访问的，
    //
    //openid=**************** //通过调用QQ获取地址获取openid
    //QQ的调用地址是需要三个参数的，但是AbstractOAuth2ApiBinding 这个类，
    // 帮我们处理了access_token 这个类，所以我们只需处置另外两个参数
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }


    @Override
    public QQUserInfo getUserInfo() {
        //通过替换appid,openid，来获得用户信息
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            //把从服务器返回的信息openid，放入用户信息里
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }


}
