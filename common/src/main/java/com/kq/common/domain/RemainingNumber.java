package com.kq.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author yerui
 * @since 2018-07-03
 */
public class RemainingNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 组ID
     */
    private String groupId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 剩余订单数量
     */
    private Integer remainingNumber;

    /**
     * 1订单2 采集单
     */
    private String type;
    /**
     * 生产单ID或采集单id
     */
    private String businessId;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 修改时间
     */
    private Date updTime;

    /**
     * 从生产单或采集单获取到的上限数量
     */
    private Integer totalNumber;

    /**
     * 数据权限做了扩展，以方便用户有同样的角色但是有不同的数据权限
     * 2018年7月12日10:09:16
     */
    private String satelliteType;

    private String sensorId;


    private String productLevle;

    private String satelliteId;

    private String satelliteName;

    private Date effectiveBegtinDate;

    /**
     * 有效结束日期
     */
    private Date effectiveEndDate;

    private Date originTime;

    /**
     * 产品分辨率
     */
    private String productResolution;

    /**
     * 采集数量
     */
    private Integer pickNumber;

    /**
     * 采集时间上限 -1为无限
     */
    private Integer pickDay;

    /**
     * 采集范围ID01权限
     */
    private Integer pickLimitOne;

    /**
     * 采集范围ID02权限
     */
    private Integer pickLimitTwo;

    private String status;


    /**
     * 时间无限的标志位
     */
    private String timeInfiniteStatus;

    /**
     * 已经购买的数量
     */
    private Integer boughtNumber;


    private String effectiveYear;


    private String effectiveMouth;

    private String effectiveDay;

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
