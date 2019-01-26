package com.kq.auth.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 在线用户列表
 * </p>
 *
 * @author yerui
 * @since 2019-01-12
 */
@TableName("base_online_user")
public class BaseOnlineUser extends Model<BaseOnlineUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 用户姓名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 行业
     */
    private String industry;
    /**
     * 用户行业
     */
    @TableField("user_type")
    private String userType;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 用户登录的地域
     */
    private String area;

    /**
     * 用户在线id
     */
    @TableField("session_id")
    private String sessionId;

    /**
     * 用户真实姓名
     */
    @TableField("name")
    private String name;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseOnlineUser{" +
        ", id=" + id +
        ", userName=" + userName +
        ", industry=" + industry +
        ", userType=" + userType +
        ", mobile=" + mobile +
        ", area=" + area +
        "}";
    }
}
