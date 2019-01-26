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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yerui
 * @since 2018-05-12
 */
@ApiModel(value = "产品生产权限")
@TableName("product_permission")
public class ProductPermission extends Model<ProductPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableField("ID")
    @ApiModelProperty(value = "主键", required = true)
    private String id;

    /**
     * 卫星类型
     */
    @TableField("SATELLITE_TYPE")
    @ApiModelProperty(value = "卫星类型", required = false)
    private String satelliteType;

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
     * 订购总数
     */
    @ApiModelProperty(value = "订购总数", required = false)
    @TableField("TOTAL_ORDER_NUMBER")
    private Integer totalOrderNumber;

    /**
     * 剩余使用数量
     */
    @ApiModelProperty(value = "剩余使用数量", required = false)
    @TableField("REMAINING_NUMBER")
    private Integer remainingNumber;

    /**
     * 有效开始日期
     */
    @ApiModelProperty(value = "有效开始日期", required = false)
    @TableField("EFFECTIVE_BEGTIN_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date effectiveBegtinDate;

    /**
     * 有效结束日期
     */
    @ApiModelProperty(value = "有效结束日期", required = false)
    @TableField("EFFECTIVE_END_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date effectiveEndDate;

    /**
     * 产品分辨率
     */
    @ApiModelProperty(value = "产品分辨率", required = false)
    @TableField("PRODUCT_RESOLUTION")
    private String productResolution;


    /**
     * 表达式，用于快速排表格
     */
    @ApiModelProperty(value = "表达式，用于快速排表格", required = false)
    @TableField("EXPRESS")
    private String express;

    /**
     * 系统生成开始时间
     */
    @ApiModelProperty(value = "创建时间(自动填充)", required = false)
    @TableField(value = "CRT_TIME",strategy = FieldStrategy.NOT_EMPTY,fill = FieldFill.INSERT)
    @JSONField(format="yyyy-MM-dd")
    private Date crtTime;
    /**
     * 创建名字
     */
    @TableField("CRT_NAME")
    @ApiModelProperty(value = "创建名字", required = false)
    private String crtName;

    /**
     * 创建的IP
     */
    @ApiModelProperty(value = "创建的IP", required = false)
    @TableField("CRT_HOST")
    private String crtHost;


    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = false)
    @TableField(value = "UPT_TIME",update = "now()",strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format="yyyy-MM-dd")
    private Date updTime;


    @ApiModelProperty(value = "所属组ID", required = false)
    @TableField("GROUP_ID")
    private String groupId;


    @ApiModelProperty(value = "有效年", required = false)
    @TableField("effective_year")
    private String effectiveYear;


    @ApiModelProperty(value = "有效月", required = false)
    @TableField("effective_mouth")
    private String effectiveMouth;

    @ApiModelProperty(value = "有效天", required = false)
    @TableField("effective_day")
    private String effectiveDay;



    /**
     * 时间无限的标志位
     */
    @ApiModelProperty(value = "时间无限的标志  1 有限时间，0 无限时间")
    @TableField("time_infinite_status")
    private String timeInfiniteStatus;

    @ApiModelProperty(value = "卫星名称")
    @TableField("satellite_name")
    private String satelliteName;

    @ApiModelProperty(value = "卫星名称")
    @TableField("upd_user")
    private String updUser;

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getSatelliteName() {
        return satelliteName;
    }

    public void setSatelliteName(String satelliteName) {
        this.satelliteName = satelliteName;
    }

    public String getEffectiveDay() {
        return effectiveDay;
    }

    public void setEffectiveDay(String effectiveDay) {
        this.effectiveDay = effectiveDay;
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

    public String getTimeInfiniteStatus() {
        return timeInfiniteStatus;
    }

    public void setTimeInfiniteStatus(String timeInfiniteStatus) {
        this.timeInfiniteStatus = timeInfiniteStatus;
    }



    public Date getEffectiveBegtinDate() {
        return effectiveBegtinDate;
    }

    public void setEffectiveBegtinDate(Date effectiveBegtinDate) {
        this.effectiveBegtinDate = effectiveBegtinDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getProductResolution() {
        return productResolution;
    }

    public void setProductResolution(String productResolution) {
        this.productResolution = productResolution;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getTotalOrderNumber() {
        return totalOrderNumber;
    }

    public void setTotalOrderNumber(Integer totalOrderNumber) {
        this.totalOrderNumber = totalOrderNumber;
    }

    public Integer getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(Integer remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getEffectiveYear() {
        return effectiveYear;
    }

    public void setEffectiveYear(String effectiveYear) {
        this.effectiveYear = effectiveYear;
    }

    public String getEffectiveMouth() {
        return effectiveMouth;
    }

    public void setEffectiveMouth(String effectiveMouth) {
        this.effectiveMouth = effectiveMouth;
    }

    @Override
    public String toString() {
        return "ProductPermission{" +
        ", id=" + id +
        ", satelliteType=" + satelliteType +
        ", sensorId=" + sensorId +
        ", productLevle=" + productLevle +
        ", satelliteId=" + satelliteId +
        ", totalOrderNumber=" + totalOrderNumber +
        ", remainingNumber=" + remainingNumber +
        ", effectiveBegtinDate=" + effectiveBegtinDate +
        ", effectiveEndDate=" + effectiveEndDate +
        ", productResolution=" + productResolution +
        ", express=" + express +
        ", crtTime=" + crtTime +
        ", crtName=" + crtName +
        ", crtHost=" + crtHost +
        ", updTime=" + updTime +
        "}";
    }
}
