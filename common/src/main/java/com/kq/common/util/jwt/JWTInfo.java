package com.kq.common.util.jwt;

import com.kq.common.DTO.PerimissionInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ace on 2017/9/10.
 */
public class JWTInfo implements Serializable,IJWTInfo {

    private String username;
    private String userId;
    private String name;
    private String userType;

    public JWTInfo(String username) {
        this.username = username;
    }

    private List<PerimissionInfo> perimission;

    public JWTInfo() {
    }



    public JWTInfo(String username, String userId, String name) {
        this.username = username;
        this.userId = userId;
        this.name = name;

    }

    public JWTInfo(String username, String userId, String name, String userType) {
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.userType = userType;
    }

    @Override
    public String getUniqueName() {
        return this.getUsername();
    }


    @Override
    public String getId() {
        return this.getUserId();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public List<PerimissionInfo> getPerimissionList() {
        return this.getPerimission();
    }

    public List<PerimissionInfo> getPerimission() {
        return perimission;
    }

    public void setPerimission(List<PerimissionInfo> perimission) {
        this.perimission = perimission;
    }

    @Override
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JWTInfo jwtInfo = (JWTInfo) o;

        if (username != null ? !username.equals(jwtInfo.username) : jwtInfo.username != null) {
            return false;
        }
        return userId != null ? userId.equals(jwtInfo.userId) : jwtInfo.userId == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
