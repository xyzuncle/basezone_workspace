package com.kq.perimission.dto;

/**
 * 社交用户的信息字段
 */
public class SocialUserInfo {

    //应用的ID 如qq
    private String providerId;
    //返回的例如openid
    private String providerUserId;
    //昵称
    private String nickName;

    //用户头像
    private String headImg;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
