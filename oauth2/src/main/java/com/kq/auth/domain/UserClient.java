package com.kq.auth.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yerui
 * @since 2018-07-28
 */
@ApiModel(value = "用户客户端关系表")
@TableName("user_client")
public class UserClient extends Model<UserClient> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    private String id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @TableField("user_id")
    private String userId;

    /**
     * 客户端ID
     */
    @ApiModelProperty(value = "客户端ID", required = true)
    @TableField("client_id")
    private String clientId;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = true)
    private String desc;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    @TableField(value = "crt_time", strategy = FieldStrategy.NOT_EMPTY, fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private Date ctrTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = false)
    @TableField(value = "upd_time",update = "now()",strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format="yyyy-MM-dd")
    private Date updTime;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户姓名", required = true)
    @TableField("user_name")
    private String userName;
    /**
     * 用户token
     */
    @ApiModelProperty(value = "用户所属token", required = true)
    @TableField("user_token")
    private String userToken;

    /**
     * 客户端秘钥
     */
    @ApiModelProperty(value = "客户端秘钥", required = true)
    @TableField("client_secrect")
    private String clientSecrect;

    /**
     * 作用域
     */
    @ApiModelProperty(value = "客户端作用域", required = true)
    private String scope;
    /**
     * 认证模式
     */
    @ApiModelProperty(value = "客户端授权类型", required = true)
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCtrTime() {
        return ctrTime;
    }

    public void setCtrTime(Date ctrTime) {
        this.ctrTime = ctrTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getClientSecrect() {
        return clientSecrect;
    }

    public void setClientSecrect(String clientSecrect) {
        this.clientSecrect = clientSecrect;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserClient{" +
        ", id=" + id +
        ", userId=" + userId +
        ", clientId=" + clientId +
        ", desc=" + desc +
        ", ctrTime=" + ctrTime +
        ", updTime=" + updTime +
        ", userName=" + userName +
        ", userToken=" + userToken +
        ", clientSecrect=" + clientSecrect +
        ", scope=" + scope +
        ", type=" + type +
        "}";
    }
}
