package com.kq.perimission.domain;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 卫星查询权限表
 * </p>
 *
 * @author yerui
 * @since 2018-11-22
 */
@TableName("satellite_perimission")
public class SatellitePerimission extends Model<SatellitePerimission> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 卫星类型
     */
    @TableField("SATELLITE_TYPE")
    private String satelliteType;
    /**
     * 传感器组合的内容，用#分隔多个传感，用;号区分是否有外网
     */
    @TableField("SENSOR_CONTENT")
    private String sensorContent;
    /**
     * 卫星ID
     */
    @TableField("SATELLITE_ID")
    private String satelliteId;
    /**
     * 卫星名称
     */
    @TableField("SATELLITE_NAME")
    private String satelliteName;
    /**
     * 修改人
     */
    @TableField("upd_user")
    private String updUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间(自动填充)", required = false)
    @TableField(value = "crt_time",strategy = FieldStrategy.NOT_EMPTY,fill = FieldFill.INSERT)
    @JSONField(format="yyyy-MM-dd")
    private Date crtTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间(自动填充)", required = false)
    @TableField(value = "upd_time",update = "now()",strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format="yyyy-MM-dd")
    private Date updTime;
    /**
     * 发射时间
     */
    @ApiModelProperty(value = "发射时间", required = false)
    @TableField("launch_time")
    @JSONField(format="yyyy-MM-dd")
    private Date launchTime;
    /**
     * 发射的状态
     */
    @TableField("launch_status")
    private String launchStatus;

    @TableField("SENSOR_IDS")
    private String sensroIds;

    public String getSensroIds() {
        return sensroIds;
    }

    public void setSensroIds(String sensroIds) {
        this.sensroIds = sensroIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSatelliteType() {
        return satelliteType;
    }

    public void setSatelliteType(String satelliteType) {
        this.satelliteType = satelliteType;
    }

    public String getSensorContent() {
        return sensorContent;
    }

    public void setSensorContent(String sensorContent) {
        this.sensorContent = sensorContent;
    }

    public String getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(String satelliteId) {
        this.satelliteId = satelliteId;
    }

    public String getSatelliteName() {
        return satelliteName;
    }

    public void setSatelliteName(String satelliteName) {
        this.satelliteName = satelliteName;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
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

    public Date getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(Date launchTime) {
        this.launchTime = launchTime;
    }

    public String getLaunchStatus() {
        return launchStatus;
    }

    public void setLaunchStatus(String launchStatus) {
        this.launchStatus = launchStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SatellitePerimission{" +
        ", id=" + id +
        ", satelliteType=" + satelliteType +
        ", sensorContent=" + sensorContent +
        ", satelliteId=" + satelliteId +
        ", satelliteName=" + satelliteName +
        ", updUser=" + updUser +
        ", crtTime=" + crtTime +
        ", updTime=" + updTime +
        ", launchTime=" + launchTime +
        ", launchStatus=" + launchStatus +
        "}";
    }
}
