package com.kq.perimission.dto;

import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.BaseResourceAuthority;
import com.kq.perimission.domain.PickPerimission;
import com.kq.perimission.domain.ProductPermission;

import java.util.List;

/**
 * 用于一次性保存的对象
 */
public class PerimissionView {
    private BaseGroup baseGroup;
    private List<String> fontMenu;
    private List<String> backMenu;
    private List<ProductPermission> productPermission;
    private List<PickPerimission> pickPerimission;

    public List<String> getFontMenu() {
        return fontMenu;
    }

    public void setFontMenu(List<String> fontMenu) {
        this.fontMenu = fontMenu;
    }

    public List<String> getBackMenu() {
        return backMenu;
    }

    public void setBackMenu(List<String> backMenu) {
        this.backMenu = backMenu;
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

    public BaseGroup getBaseGroup() {
        return baseGroup;
    }

    public void setBaseGroup(BaseGroup baseGroup) {
        this.baseGroup = baseGroup;
    }

    public void setPickPerimission(List<PickPerimission> pickPerimission) {
        this.pickPerimission = pickPerimission;
    }


}
