package com.kq.common.DTO;

import com.kq.common.util.jwt.IJWTInfo;

import java.util.List;


public class ClientInfo implements IJWTInfo {

    private String clientId;
    private String name;
    private String id;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PerimissionInfo> getPerimissionInfo() {
        return perimissionInfo;
    }

    public void setPerimissionInfo(List<PerimissionInfo> perimissionInfo) {
        this.perimissionInfo = perimissionInfo;
    }

    private List<PerimissionInfo> perimissionInfo;

    public ClientInfo(String clientId, String name, String id) {
        this.clientId = clientId;
        this.name = name;
        this.id = id;
    }

    @Override
    public String getUniqueName() {
        return clientId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<PerimissionInfo> getPerimissionList() {
        return null;
    }

    @Override
    public String getUserType() {
        return null;
    }


}
