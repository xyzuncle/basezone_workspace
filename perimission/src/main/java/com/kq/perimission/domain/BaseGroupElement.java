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
 * @since 2018-09-20
 */
@ApiModel(value = "用户组元素API匹配")
@TableName("base_group_element")
public class BaseGroupElement extends Model<BaseGroupElement> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "唯一标识（主键）", required = true)
    private String id;

    /**
     * 所属组ID
     */
    @ApiModelProperty(value = "所属组ID", required = false)
    @TableField("group_id")
    private String groupId;
    /**
     * 所属用户id
     */
    @ApiModelProperty(value = "用户ID", required = false)
    @TableField("user_id")
    private String userId;
    /**
     * 所属元素id
     */
    @ApiModelProperty(value = "所属元素ID", required = false)
    @TableField("element_id")
    private String elementId;
    /**
     * 被调用的上限次数
     */
    @ApiModelProperty(value = "被调用的上限次数", required = false)
    @TableField("calls_num")
    private Integer callsNum;

    /**
     * 被调用的剩余数量
     */
    @ApiModelProperty(value = "被调用的剩余数量", required = false)
    @TableField("calls_remain_num")
    private Integer callsRemainNum;
    /**
     * 被调用的频率
     */
    @ApiModelProperty(value = "被调用的频率", required = false)
    @TableField("calls_frequency")
    private Integer callsFrequency;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间(自动填充)", required = false)
    @TableField(value = "crt_time", strategy = FieldStrategy.NOT_EMPTY, fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private Date ctrTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间(自动填充)", required = false)
    @TableField(value = "upd_time", update = "now()", strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format = "yyyy-MM-dd")
    private Date updTime;

    /**
     * 所属组名称
     */
    @ApiModelProperty(value = "所属组名称", required = false)
    @TableField("group_name")
    private String groupName;

    /**
     * 所属用户名称
     */
    @ApiModelProperty(value = "所属用户名称", required = false)
    @TableField("user_name")
    private String userName;
    /**
     * 0 不可用 1 可用 
     */
    @ApiModelProperty(value = "API可用状态，0 不可用 1 可用", required = false)
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Integer getCallsNum() {
        return callsNum;
    }

    public void setCallsNum(Integer callsNum) {
        this.callsNum = callsNum;
    }

    public Integer getCallsRemainNum() {
        return callsRemainNum;
    }

    public void setCallsRemainNum(Integer callsRemainNum) {
        this.callsRemainNum = callsRemainNum;
    }

    public Integer getCallsFrequency() {
        return callsFrequency;
    }

    public void setCallsFrequency(Integer callsFrequency) {
        this.callsFrequency = callsFrequency;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseGroupElement{" +
        ", id=" + id +
        ", groupId=" + groupId +
        ", userId=" + userId +
        ", elementId=" + elementId +
        ", callsNum=" + callsNum +
        ", callsRemainNum=" + callsRemainNum +
        ", callsFrequency=" + callsFrequency +
        ", ctrTime=" + ctrTime +
        ", updTime=" + updTime +
        ", groupName=" + groupName +
        ", userName=" + userName +
        ", status=" + status +
        "}";
    }
}
