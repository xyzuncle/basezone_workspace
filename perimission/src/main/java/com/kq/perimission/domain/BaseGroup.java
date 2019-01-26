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
 * 用户组
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */

@ApiModel(value = "用户组表单")
@TableName("base_group")
public class BaseGroup extends Model<BaseGroup> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识(后台配置，不用传)", required = true)
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码", required = false)
    private String code;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称(必填)", required = true)
    private String name;
    /**
     * 上级节点
     */
    @ApiModelProperty(value = "上级节点", required = false)
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 树状关系
     */
    @ApiModelProperty(value = "树状关系", required = false)
    private String path;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = false)
    private String type;
    /**
     * 角色组类型
     */
    @ApiModelProperty(value = "组类型(必填) 1 内部 2 外部", required = true)
    @TableField("group_type")
    private String groupType;

    /**
     * 描述
     */
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

    @ApiModelProperty(value = "修改时间(自动填充)", required = false)
    @TableField(value = "upd_time", update = "now()", strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format = "yyyy-MM-dd")
    private Date updTime;

    @ApiModelProperty(value = "修改人ID(自动填充)", required = false)
    @TableField("upd_user")
    private String updUser;

    @ApiModelProperty(value = "修改人姓名(自动填充)", required = false)
    @TableField("upd_name")
    private String updName;

    @ApiModelProperty(value = "修改人IP(自动填充)", required = false)
    @TableField("upd_host")
    private String updHost;


    @ApiModelProperty(value = "扩展属性1,这个字段用于角色的流水号", required = false)
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


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    @Override
    public String toString() {
        return "BaseGroup{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", parentId=" + parentId +
                ", path=" + path +
                ", type=" + type +
                ", groupType=" + groupType +
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
