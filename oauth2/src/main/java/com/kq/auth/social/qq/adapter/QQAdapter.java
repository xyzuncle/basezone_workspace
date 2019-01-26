package com.kq.auth.social.qq.adapter;

import com.kq.auth.domain.QQUserInfo;
import com.kq.auth.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 实现 ApiAdapter 来实现完成 QQ信息的适配
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试QQ是否是通的
     * @param qq
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        return false;
    }

    /**
     *
     * @param api
     * @param values
     *          这个QQ就是qq API的调用实现类，获取到QQ的用户信息，
     *          放入到这个ConnectionValues这个标准模板里，达到适配的效果
     *
     * @desc 这就是适配器的过程，把QQ，微信，微博，oauth等信息进行适配，每家的用户信息和字段都不一样
     *
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo info = api.getUserInfo();

        //获取用户名称，这里是QQ空间的名称
        values.setDisplayName(info.getNickname());
        //获取用户的40*40的用户头像
        values.setImageUrl(info.getFigureurl_1());
        //个人主页,QQ是没有的
        values.setProfileUrl(null);
        //服务上的用户ID，也就是openId
        values.setProviderUserId(info.getOpenId());
    }

    /**
     * 绑定 解绑
     * @param qq
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    /**
     * 微博 发消息更新微博
     * @param qq
     * @param s
     */
    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
