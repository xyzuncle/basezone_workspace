package com.kq.perimission.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *   资源权限组相关
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@ApiModel(value = "资源权限表单")
@TableName("base_resource_authority")
public class BaseResourceAuthority extends Model<BaseResourceAuthority> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识", required = true)
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", required = false)
    @TableField("authority_id")
    private String authorityId;
    /**
     * 角色类型
     * group PC
     * wx
     * andiord
     * 这个类型 也有可能是 clientID，这样就分开了
     */
    @ApiModelProperty(value = "角色ID group(pc) wx  andiord", required = false)
    @TableField("authority_type")
    private String authorityType;

    /**
     * 资源ID
     */
    @ApiModelProperty(value = "资源ID", required = false)
    @TableField("resource_id")
    private String resourceId;

    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型", required = false)
    @TableField("resource_type")
    private String resourceType;

    @ApiModelProperty(value = "父类ID", required = false)
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "路径", required = false)
    private String path;

    @ApiModelProperty(value = "描述", required = false)
    private String description;

    @ApiModelProperty(value = "创建时间(自动填充)", required = false)
    @TableField(value = "crt_time", strategy = FieldStrategy.NOT_EMPTY, fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private Date crtTime;

    @ApiModelProperty(value = "创建人ID(自动填充)", required = false)
    @TableField("crt_user")
    private String crtUser;

    @ApiModelProperty(value = "创建人名称(自动填充)", required = false)
    @TableField("crt_name")
    private String crtName;
    @ApiModelProperty(value = "创建人IP(自动填充)", required = false)
    @TableField("crt_host")
    private String crtHost;

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

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(String authorityType) {
        this.authorityType = authorityType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        return "BaseResourceAuthority{" +
        ", id=" + id +
        ", authorityId=" + authorityId +
        ", authorityType=" + authorityType +
        ", resourceId=" + resourceId +
        ", resourceType=" + resourceType +
        ", parentId=" + parentId +
        ", path=" + path +
        ", description=" + description +
        ", crtTime=" + crtTime +
        ", crtUser=" + crtUser +
        ", crtName=" + crtName +
        ", crtHost=" + crtHost +
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
