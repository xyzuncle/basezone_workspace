package com.kq.auth.DTO;

import org.springframework.security.core.session.SessionInformation;

import java.util.Date;

public class CustomSessionInfo extends SessionInformation {

    public CustomSessionInfo(Object principal, String sessionId, Date lastRequest) {
        super(principal, sessionId, lastRequest);
    }
    private String name;
    private String type;
    //应用领域
    private String pplappfield;
    private String mobilePhone;
    //登录时候获得IP渠道的地址
    private String area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPplappfield() {
        return pplappfield;
    }

    public void setPplappfield(String pplappfield) {
        this.pplappfield = pplappfield;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
