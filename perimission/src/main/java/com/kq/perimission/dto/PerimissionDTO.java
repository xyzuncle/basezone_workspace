package com.kq.perimission.dto;

import com.kq.perimission.domain.*;

import java.util.List;

/**
 * 用于一次性保存的对象
 */
public class PerimissionDTO {

    private BaseGroup baseGroup;
    private List<BaseResourceAuthority> baseResource;
    private List<ProductPermission> productPermission;
    public List<BaseResourceAuthority> getBaseResource() {
        return baseResource;
    }

    public void setBaseResource(List<BaseResourceAuthority> baseResource) {
        this.baseResource = baseResource;
    }

    private List<PickPerimission> pickPerimission;

    public BaseGroup getBaseGroup() {
        return baseGroup;
    }

    public void setBaseGroup(BaseGroup baseGroup) {
        this.baseGroup = baseGroup;
    }

    public List<ProductPermission> getProductPermission() {
        return productPermission;
    }

    public void setProductPermission(List<ProductPermission> productPermission) {
        this.productPermission = productPermission;
    }

    public List<PickPerimission> getPickPerimission() {
        return pickPerimission;
    }

    public void setPickPerimission(List<PickPerimission> pickPerimission) {
        this.pickPerimission = pickPerimission;
    }
}
