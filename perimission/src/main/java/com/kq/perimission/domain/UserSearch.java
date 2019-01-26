package com.kq.perimission.domain;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yerui
 * @since 2018-07-23
 */
@ApiModel(value = "用户自定义条件搜索")
@TableName("user_search")
public class UserSearch extends Model<UserSearch> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "唯一标识", required = true)
    private String id;
    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户唯一标识", required = false)
    private String userid;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", required = false)
    private String username;

    /**
     * 被用户保存的关键字
     */
    @ApiModelProperty(value = "用户自定义的搜索字段", required = false)
    @TableField("searchkey")
    private String searchkey;

    /**
     * 被使用的次数
     */
    @ApiModelProperty(value = "该条件被使用的次数", required = false)
    private Integer used;

    /**
     * 排序
     */

    @ApiModelProperty(value = "根据顺序排序", required = false)
    private Integer order;

    /**
     * 创建时间
     */
    @TableField(value = "crt_time", strategy = FieldStrategy.NOT_EMPTY, fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private Date crtTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = false)
    @TableField(value = "upd_time",update = "now()",strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format="yyyy-MM-dd")
    private Date updTime;

    /**
     * 扩展属性1
     */
    @ApiModelProperty(value = "扩展属性1", required = false)
    private String attr1;

    /**
     * 扩展属性2
     */
    @ApiModelProperty(value = "扩展属性2", required = false)
    private String attr2;
    /**
     * 扩展属性3
     */
    @ApiModelProperty(value = "扩展属性3", required = false)
    private String attr3;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    @Override
    public String toString() {
        return "UserSearch{" +
        ", id=" + id +
        ", userid=" + userid +
        ", username=" + username +
        ", searchKey=" + searchkey +
        ", used=" + used +
        ", order=" + order +
        ", crtTime=" + crtTime +
        ", updTime=" + updTime +
        ", attr1=" + attr1 +
        ", attr2=" + attr2 +
        ", attr3=" + attr3 +
        "}";
    }
}
