package com.kq.perimission.domain;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *  用户服务维护
 * </p>
 *
 * @author yerui
 * @since 2018-03-28
 */
@ApiModel(value = "用户服务表单实体")
@TableName("base_user_service")
public class BaseUserServiceEntity extends Model<BaseUserServiceEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识", required = true)
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = false)
    @TableField("user_id")
    private String userId;
    /**
     * 服务路径
     */
    @ApiModelProperty(value = "服务路径", required = false)
    @TableField("server_uri")
    private String serverUri;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = false)
    private String description;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间(自动填充)", required = false)
    @TableField(value = "crt_time", strategy = FieldStrategy.NOT_EMPTY, fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private Date crtTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人ID(自动填充)", required = false)
    @TableField("crt_user")
    private String crtUser;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人名称(自动填充)", required = false)
    @TableField("crt_name")
    private String crtName;
    /**
     * 创建主机
     */
    @ApiModelProperty(value = "创建人IP(自动填充)", required = false)
    @TableField("crt_host")
    private String crtHost;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "修改时间(自动填充)", required = false)
    @TableField(value = "upd_time", update = "now()", strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format = "yyyy-MM-dd")
    private Date updTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "修改人ID(自动填充)", required = false)
    @TableField("upd_user")
    private String updUser;
    /**
     * 更新姓名
     */
    @ApiModelProperty(value = "修改人姓名(自动填充)", required = false)
    @TableField("upd_name")
    private String updName;
    /**
     * 更新主机
     */
    @ApiModelProperty(value = "修改人IP(自动填充)", required = false)
    @TableField("upd_host")
    private String updHost;

    @ApiModelProperty(value = "扩展属性1", required = false)
    private String attr1;
    @ApiModelProperty(value = "扩展属性2", required = false)
    private String attr2;
    @ApiModelProperty(value = "扩展属性3", required = false)
    private String attr3;
    @ApiModelProperty(value = "扩展属性4", required = false)
    private String attr4;
    @ApiModelProperty(value = "扩展属性5", required = false)
    private String attr5;
    @ApiModelProperty(value = "扩展属性6", required = false)
    private String attr6;
    @ApiModelProperty(value = "扩展属性7", required = false)
    private String attr7;
    @ApiModelProperty(value = "扩展属性8", required = false)
    private String attr8;


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

    public String getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public String getCrtHost() {
        return crtHost;
    }

    public void setCrtHost(String crtHost) {
        this.crtHost = crtHost;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdName() {
        return updName;
    }

    public void setUpdName(String updName) {
        this.updName = updName;
    }

    public String getUpdHost() {
        return updHost;
    }

    public void setUpdHost(String updHost) {
        this.updHost = updHost;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    public String getAttr6() {
        return attr6;
    }

    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    public String getAttr7() {
        return attr7;
    }

    public void setAttr7(String attr7) {
        this.attr7 = attr7;
    }

    public String getAttr8() {
        return attr8;
    }

    public void setAttr8(String attr8) {
        this.attr8 = attr8;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseUserServiceEntity{" +
        ", id=" + id +
        ", userId=" + userId +
        ", serverUri=" + serverUri +
        ", description=" + description +
        ", crtTime=" + crtTime +
        ", crtUser=" + crtUser +
        ", crtName=" + crtName +
        ", crtHost=" + crtHost +
        ", updTime=" + updTime +
        ", updUser=" + updUser +
        ", updName=" + updName +
        ", updHost=" + updHost +
        ", attr1=" + attr1 +
        ", attr2=" + attr2 +
        ", attr3=" + attr3 +
        ", attr4=" + attr4 +
        ", attr5=" + attr5 +
        ", attr6=" + attr6 +
        ", attr7=" + attr7 +
        ", attr8=" + attr8 +
        "}";
    }
}
