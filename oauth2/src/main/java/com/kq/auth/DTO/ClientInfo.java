package com.kq.auth.DTO;


import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.util.jwt.IJWTInfo;

import java.util.List;


public class ClientInfo implements IJWTInfo {

    private String clientId;
    private String name;
    private String id;
    private PerimissionInfo perimissionInfo;

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
