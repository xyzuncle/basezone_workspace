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
 *
 * </p>
 *
 * @author yerui
 * @since 2018-05-12
 */
@TableName("pick_perimission")
public class PickPerimission extends Model<PickPerimission> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 卫星类型
     */
    @ApiModelProperty(value = "卫星类型", required = false)
    @TableField("SATELLITE_TYPE")
    private String satelliteType;
    /**
     * 卫星类型名称
     */
    @ApiModelProperty(value = "卫星类型名称", required = false)
    @TableField("SATELLITE_NAME")
    private String satelliteName;
    /**
     * 传感器ID
     */
    @ApiModelProperty(value = "传感器ID", required = false)
    @TableField("SENSOR_ID")
    private String sensorId;
    /**
     * 产品级别
     */
    @ApiModelProperty(value = "产品级别", required = false)
    @TableField("PRODUCT_LEVLE")
    private String productLevle;

    /**
     * 卫星ID
     */
    @ApiModelProperty(value = "卫星ID", required = false)
    @TableField("SATELLITE_ID")
    private String satelliteId;
    /**
     * 采集数量
     */
    @ApiModelProperty(value = "采集数量", required = false)
    @TableField("PICK_NUMBER")
    private Integer pickNumber;
    /**
     * 采集时间上限 -1为无限
     */
    @ApiModelProperty(value = "采集时间上限 -1为无限", required = false)
    @TableField("PICK_DAY")
    private Integer pickDay;

    /**
     * 采集范围ID01权限
     */
    @ApiModelProperty(value = "采集范围ID01权限", required = false)
    @TableField("PICK_LIMIT_ONE")
    private Integer pickLimitOne;


    /**
     * 采集范围ID02权限
     */
    @ApiModelProperty(value = "采集范围ID02权限", required = false)
    @TableField("PICK_LIMIT_TWO")
    private Integer pickLimitTwo;

    /**
     * 剩余使用数量
     */
    @ApiModelProperty(value = "剩余使用数量", required = false)
    @TableField("REMAINING_NUMBER")
    private Integer remainingNumber;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间(自动填充)", required = false)
    @TableField(value = "CRT_TIME",strategy = FieldStrategy.NOT_EMPTY,fill = FieldFill.INSERT)
    @JSONField(format="yyyy-MM-dd")
    private Date crtTime;


    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = false)
    @TableField(value = "UPT_TIME",update = "now()",strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format="yyyy-MM-dd")
    private Date uptTime;
    /**
     * 所属组ID
     */
    @ApiModelProperty(value = "所属组ID", required = false)
    @TableField("group_id")
    private String groupId;

    /**
     * 快速刷选的表达式，用于快速刷选列表
     */
    @TableField("EXPRESS")
    private String express;

    @ApiModelProperty(value = "修改人", required = false)
    @TableField("UPD_USER")
    private String updUser;


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

    public String getSatelliteName() {
        return satelliteName;
    }

    public void setSatelliteName(String satelliteName) {
        this.satelliteName = satelliteName;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getProductLevle() {
        return productLevle;
    }

    public void setProductLevle(String productLevle) {
        this.productLevle = productLevle;
    }

    public String getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(String satelliteId) {
        this.satelliteId = satelliteId;
    }

    public Integer getPickNumber() {
        return pickNumber;
    }

    public void setPickNumber(Integer pickNumber) {
        this.pickNumber = pickNumber;
    }

    public Integer getPickDay() {
        return pickDay;
    }

    public void setPickDay(int pickDay) {
        this.pickDay = pickDay;
    }

    public Integer getPickLimitOne() {
        return pickLimitOne;
    }

    public void setPickLimitOne(int pickLimitOne) {
        this.pickLimitOne = pickLimitOne;
    }

    public Integer getPickLimitTwo() {
        return pickLimitTwo;
    }

    public void setPickLimitTwo(int pickLimitTwo) {
        this.pickLimitTwo = pickLimitTwo;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(int remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    @Override
    public String toString() {
        return "PickPerimission{" +
        ", id=" + id +
        ", satelliteType=" + satelliteType +
        ", satelliteName=" + satelliteName +
        ", sensorId=" + sensorId +
        ", productLevle=" + productLevle +
        ", satelliteId=" + satelliteId +
        ", pickNumber=" + pickNumber +
        ", pickDay=" + pickDay +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        ", groupId=" + groupId +
        "}";
    }
}
