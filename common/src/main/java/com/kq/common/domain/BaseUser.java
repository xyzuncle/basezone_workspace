package com.kq.common.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  用户基本信息
 * </p>
 *
 * @author yerui
 * @since 2018-04-12
 */
public class BaseUser implements Serializable{

    private static final long serialVersionUID = 1L;

    public BaseUser() {
    }

    public BaseUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * (公有)用户ID
     */
    private String id;

    /**
     * (公有)用户登录名
     */
    private String username;

    /**
     * (公有)用户密码
     */
    private String password;

    /**
     * (公有)用户姓名
     */
    private String name;
    /**
     * (公有)用户出生日期
     */
    private Date birthday;

    /**
     * 住址
     */
    private String address;

    /**
     * 移动电话
     */
    private String mobilePhone;
    /**
     * 固话
     */
    private String telPhone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     * (公有)用户类型(1:个人，2:机构，3:内部，其他（用户类型code）:外部)
     */
    private String type;
    /**
     * (公有)用户状态(0:禁用，1:正常)
     */
    private String status;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 创建人ID
     */
    private String crtUser;

    /**
     * 创建人姓名
     */
    private String crtName;

    /**
     * 创建人所在IP
     */
    private String crtHost;

    /**
     * 修改人时间
     */
    private Date updTime;

    /**
     * 修改用户ID
     */
    private String updUser;

    /**
     * 修改人姓名
     */
    private String updName;
    /**
     * 修改人IP
     */
    private String updHost;

    /**
     * 应用领域
     */
    private String pplappfield;

    /**
     * 证件号码
     */

    private String identifyNum;

    /**
     * 证件类别 1:身份证 2 军官证 3 护照
     */
    private String identifyType;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单位code
     */
    private String unitCode;

    /**
     * 部门名称
     */
    private String idepartName;
    /**
     * (机构)审核人
     */
    private String verifyUser;

    /**
     * (机构)审核状态(0:未通过，1:通过，2：待审核)
     */

    private String verifyStatus;

    /**
     * (机构)未通过的审核意见
     */
    private String verifyMark;
    /**
     * (公有)来源（0：注册，1：后台添加）
     */
    private Integer source;

    /**
     * (机构)审核时间
     */
    private Date verifyDate;

    /**
     * 所属组Id
     */
    private String groupId;

    /**
     * 所属组名称
     */
    private String groupName;
    /**
     * 保留属性、备用
     */
    private String systemType;


    /**
     * 密码错误次数
     */
    private int missNum;


    /**
     * 客户端ID，用于管理客户端的令牌的关联字段
     * 令牌时长，授权范围，令牌时间，授权模式等
     */
    private String clientId;

    /**
     * 资源卫星账户类型，1 是老用户 0 不是老用户，或者被修改的用户
     */
    private String satelliteType;

    public String getSatelliteType() {
        return satelliteType;
    }

    public void setSatelliteType(String satelliteType) {
        this.satelliteType = satelliteType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPplappfield() {
        return pplappfield;
    }

    public void setPplappfield(String pplappfield) {
        this.pplappfield = pplappfield;
    }

    public String getIdentifyNum() {
        return identifyNum;
    }

    public void setIdentifyNum(String identifyNum) {
        this.identifyNum = identifyNum;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getIdepartName() {
        return idepartName;
    }

    public void setIdepartName(String idepartName) {
        this.idepartName = idepartName;
    }

    public String getVerifyUser() {
        return verifyUser;
    }

    public void setVerifyUser(String verifyUser) {
        this.verifyUser = verifyUser;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getVerifyMark() {
        return verifyMark;
    }

    public void setVerifyMark(String verifyMark) {
        this.verifyMark = verifyMark;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public int getMissNum() {
        return missNum;
    }

    public void setMissNum(int missNum) {
        this.missNum = missNum;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "BaseUser{" +
        ", id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", name=" + name +
        ", birthday=" + birthday +
        ", address=" + address +
        ", mobilePhone=" + mobilePhone +
        ", telPhone=" + telPhone +
        ", email=" + email +
        ", sex=" + sex +
        ", type=" + type +
        ", status=" + status +
        ", description=" + description +
        ", crtTime=" + crtTime +
        ", crtUser=" + crtUser +
        ", crtName=" + crtName +
        ", crtHost=" + crtHost +
        ", updTime=" + updTime +
        ", updUser=" + updUser +
        ", updName=" + updName +
        ", updHost=" + updHost +
        ", pplappfield=" + pplappfield +
        ", identifyNum=" + identifyNum +
        ", identifyType=" + identifyType +
        ", province=" + province +
        ", city=" + city +
        ", unit=" + unit +
        ", unitCode=" + unitCode +
        ", idepartName=" + idepartName +
        ", verifyUser=" + verifyUser +
        ", verifyStatus=" + verifyStatus +
        ", verifyMark=" + verifyMark +
        ", source=" + source +
        ", verifyDate=" + verifyDate +
        "}";
    }
}
