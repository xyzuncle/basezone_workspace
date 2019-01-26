package com.kq.perimission.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 在线用户列表
 * </p>
 *
 * @author yerui
 * @since 2019-01-12
 */
@ApiModel(value = "在线用户列表Model")
@TableName("base_online_user")
public class BaseOnlineUser extends Model<BaseOnlineUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "唯一标识", required = true)
    private String id;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名", required = false)
    @TableField("user_name")
    private String userName;
    /**
     * 行业
     */
    @ApiModelProperty(value = "行业", required = false)
    private String industry;
    /**
     * 用户行业
     */
    @ApiModelProperty(value = "用户类型", required = false)
    @TableField("user_type")
    private String userType;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "手机联系方式", required = false)
    private String mobile;
    /**
     * 用户登录的地域
     */
    @ApiModelProperty(value = "登录所在地", required = false)
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


    @ApiModelProperty(value = "创建时间(自动填充)", required = false)
    @TableField(value = "crt_time", strategy = FieldStrategy.NOT_EMPTY, fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private Date crtTime;

    @ApiModelProperty(value = "修改时间(自动填充)", required = false)
    @TableField(value = "upd_time", update = "now()", strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format = "yyyy-MM-dd")
    private Date updTime;

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

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
