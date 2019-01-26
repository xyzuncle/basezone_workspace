package com.kq.auth.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yerui
 * @since 2018-07-03
 */
@ApiModel(value = "剩余数量表单")
@TableName("REMAINING_NUMBER")
public class RemainingNumber extends Model<RemainingNumber> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "唯一标识", required = true)
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 组ID
     */
    @ApiModelProperty(value = "所属组ID", required = false)
    @TableField("group_id")
    private String groupId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "所属的用户ID", required = false)
    @TableField("user_id")
    private String userId;

    /**
     * 剩余订单数量
     */
    @ApiModelProperty(value = "订单的剩余数量", required = false)
    @TableField("remaining_number")
    private Integer remainingNumber;

    /**
     * 1订单2 采集单
     */
    @ApiModelProperty(value = "所属订单类型1生产单、2 采集单", required = false)
    private String type;
    /**
     * 生产单ID或采集单id
     */
    @ApiModelProperty(value = "生产单ID或采集单id", required = false)
    @TableField("business_id")
    private String businessId;

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
    @TableField(value = "upd_time", update = "now()", strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format = "yyyy-MM-dd")
    private Date updTime;

    /**
     * 从生产单或采集单获取到的上限数量
     */
    @ApiModelProperty(value = "从生产单或采集单获取到的上限数量", required = false)
    @TableField("total_number")
    private Integer totalNumber;

    /**
     * 数据权限做了扩展，以方便用户有同样的角色但是有不同的数据权限
     * 2018年7月12日10:09:16
     */
    @ApiModelProperty(value = "卫星类型", required = false)
    @TableField("SATELLITE_TYPE")
    private String satelliteType;

    @ApiModelProperty(value = "传感器", required = false)
    @TableField("SENSOR_ID")
    private String sensorId;


    @ApiModelProperty(value = "产品级别", required = false)
    @TableField("PRODUCT_LEVLE")
    private String productLevle;

    @ApiModelProperty(value = "卫星ID", required = false)
    @TableField("SATELLITE_ID")
    private String satelliteId;

    @ApiModelProperty(value = "卫星名称", required = false)
    @TableField("SATELLITE_NAME")
    private String satelliteName;

    @ApiModelProperty(value = "有效开始日期", required = false)
    @TableField("EFFECTIVE_BEGTIN_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date effectiveBegtinDate;

    /**
     * 有效结束日期
     */
    @ApiModelProperty(value = "有效结束日期", required = false)
    @TableField("EFFECTIVE_END_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date effectiveEndDate;

    @ApiModelProperty(value = "被初始化的原始时间，用于角色修改上限", required = false)
    @TableField("origin_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date originTime;

    /**
     * 产品分辨率
     */
    @ApiModelProperty(value = "产品分辨率", required = false)
    @TableField("PRODUCT_RESOLUTION")
    private String productResolution;

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

    @ApiModelProperty(value = "1：有效 0 无效 ，用于记录剩余数量小于零，并且不在有效期范围内的不正常", required = false)
    private String status;


    /**
     * 时间无限的标志位
     */
    @ApiModelProperty(value = "时间无限的标志  1 有限时间，0 无限时间")
    @TableField("time_infinite_status")
    private String timeInfiniteStatus;

    /**
     * 已经购买的数量
     */
    @ApiModelProperty(value = "已经购买的数量")
    @TableField("bought_number")
    private Integer boughtNumber;


    @ApiModelProperty(value = "有效年", required = false)
    @TableField("effective_year")
    private String effectiveYear;


    @ApiModelProperty(value = "有效月", required = false)
    @TableField("effective_mouth")
    private String effectiveMouth;

    @ApiModelProperty(value = "有效天", required = false)
    @TableField("effective_day")
    private String effectiveDay;

    @ApiModelProperty(value = "修改人", required = false)
    @TableField("effective_day")
    private String updUser;

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
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

    public String getEffectiveDay() {
        return effectiveDay;
    }

    public void setEffectiveDay(String effectiveDay) {
        this.effectiveDay = effectiveDay;
    }

    public Integer getBoughtNumber() {
        return boughtNumber;
    }

    public void setBoughtNumber(Integer boughtNumber) {
        this.boughtNumber = boughtNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public Integer getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(Integer remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
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

    public String getSatelliteName() {
        return satelliteName;
    }

    public void setSatelliteName(String satelliteName) {
        this.satelliteName = satelliteName;
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

    public Integer getPickNumber() {
        return pickNumber;
    }

    public void setPickNumber(Integer pickNumber) {
        this.pickNumber = pickNumber;
    }

    public Integer getPickDay() {
        return pickDay;
    }

    public void setPickDay(Integer pickDay) {
        this.pickDay = pickDay;
    }

    public Integer getPickLimitOne() {
        return pickLimitOne;
    }

    public void setPickLimitOne(Integer pickLimitOne) {
        this.pickLimitOne = pickLimitOne;
    }

    public Integer getPickLimitTwo() {
        return pickLimitTwo;
    }

    public void setPickLimitTwo(Integer pickLimitTwo) {
        this.pickLimitTwo = pickLimitTwo;
    }

    public void setPickLimitTwo(int pickLimitTwo) {
        this.pickLimitTwo = pickLimitTwo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getOriginTime() {
        return originTime;
    }

    public void setOriginTime(Date originTime) {
        this.originTime = originTime;
    }

    public String getTimeInfiniteStatus() {
        return timeInfiniteStatus;
    }

    public void setTimeInfiniteStatus(String timeInfiniteStatus) {
        this.timeInfiniteStatus = timeInfiniteStatus;
    }

    @Override
    public String toString() {
        return "RemainingNumber{" +
                ", id=" + id +
                ", userId=" + userId +
                ", remainingNumber=" + remainingNumber +
                ", type=" + type +
                ", businessId=" + businessId +
                ", crtTime=" + crtTime +
                ", updTime=" + updTime +
                ", totalNumber=" + totalNumber +
                "}";
    }
}
