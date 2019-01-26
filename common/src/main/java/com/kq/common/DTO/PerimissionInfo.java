package com.kq.common.DTO;

import java.io.Serializable;


/**
 * ${DESCRIPTION}
 *  用来包装权限需要用到的元素权限和菜单
 * @author yerui uncle
 * @create
 */
public class PerimissionInfo implements Serializable{
    private String code;
    private String type;
    private String uri;
    private String method;
    private String name;
    private String menu;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPerimissionType() {
        return perimissionType;
    }

    public void setPerimissionType(String perimissionType) {
        this.perimissionType = perimissionType;
    }

    private String perimissionType;

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

