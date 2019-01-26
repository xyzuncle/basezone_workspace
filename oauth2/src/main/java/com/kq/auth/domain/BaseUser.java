package com.kq.auth.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Collection;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author yerui
 * @since 2018-03-21
 */
@TableName("base_user")
public class BaseUser extends Model<BaseUser> implements UserDetails,Serializable{

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
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

    public BaseUser() {

    }

    public BaseUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * (公有)用户ID
     */
    @ApiModelProperty(value = "(公有)用户ID", required = true)
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * (公有)用户登录名
     */
    @ApiModelProperty(value = "(公有)用户登录名", required = true)
    private String username;

    /**
     * (公有)用户密码
     */
    @ApiModelProperty(value = "(公有)用户密码", required = false)
    private String password;

    /**
     * (公有)用户姓名
     */
    @ApiModelProperty(value = "(公有)用户姓名", required = false)
    private String name;
    /**
     * (公有)用户出生日期
     */
    @ApiModelProperty(value = "(公有)用户出生日期", required = false)
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 住址
     */
    @ApiModelProperty(value = "住址", required = false)
    private String address;

    /**
     * 移动电话
     */
    @ApiModelProperty(value = "移动电话", required = false)
    @TableField("mobile_phone")
    private String mobilePhone;
    /**
     * 固话
     */
    @ApiModelProperty(value = "固话", required = false)
    @TableField("tel_phone")
    private String telPhone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别 1 男  2 女", required = false)
    private String sex;

    /**
     * (公有)用户类型(1:个人，2:机构，3:内部，其他（用户类型code）:外部)
     */
    @ApiModelProperty(value = "(公有)用户类型(1:个人，2:机构)", required = false)
    private String type;
    /**
     * (公有)用户状态(0:禁用，1:正常)
     */
    @ApiModelProperty(value = "(公有)用户状态(0:禁用，1:正常)", required = false)
    private String status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = false)
    private String description;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间(自动填充)", required = false)
    @TableField(value = "crt_time",strategy = FieldStrategy.NOT_EMPTY,fill = FieldFill.INSERT)
    @JSONField(format="yyyy-MM-dd")
    private Date crtTime;

    /**
     * 创建人ID
     */
    @ApiModelProperty(value = "创建人ID(自动填充)", required = false)
    @TableField("crt_user")
    private String crtUser;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人名称(自动填充)", required = false)
    @TableField("crt_name")
    private String crtName;

    /**
     * 创建人所在IP
     */
    @ApiModelProperty(value = "创建人IP(自动填充)", required = false)
    @TableField("crt_host")
    private String crtHost;

    /**
     * 修改人时间
     */
    @ApiModelProperty(value = "修改时间(自动填充)", required = false)
    @TableField(value = "upd_time",update = "now()",strategy = FieldStrategy.NOT_EMPTY)
    @JSONField(format="yyyy-MM-dd")
    private Date updTime;

    /**
     * 修改用户ID
     */
    @ApiModelProperty(value = "修改人ID(自动填充)", required = false)
    @TableField("upd_user")
    private String updUser;

    /**
     * 修改人姓名
     */
    @ApiModelProperty(value = "修改人姓名(自动填充)", required = false)
    @TableField("upd_name")
    private String updName;
    /**
     * 修改人IP
     */
    @ApiModelProperty(value = "修改人IP(自动填充)", required = false)
    @TableField("upd_host")
    private String updHost;

    /**
     * 应用领域
     */
    @ApiModelProperty(value = "应用领域 1 林业 2 农业 3 其他", required = false)
    private String pplappfield;

    /**
     * 证件号码
     */

    @ApiModelProperty(value = "证件号码", required = false)
    @TableField("identify_num")
    private String identifyNum;

    /**
     * 证件类别 1:身份证 2 军官证 3 护照
     */
    @ApiModelProperty(value = "证件类别 1:身份证 2 军官证 3 护照", required = false)
    @TableField("identify_Type")
    private String identifyType;
    /**
     * 省
     */
    @ApiModelProperty(value = "省", required = false)
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(value = "市", required = false)
    private String city;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位", required = false)
    private String unit;
    /**
     * 单位code
     */
    @ApiModelProperty(value = "社会信用代码", required = false)
    @TableField("unit_code")
    private String unitCode;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "单位名称", required = false)
    @TableField("idepart_name")
    private String idepartName;
    /**
     * (机构)审核人
     */
    @ApiModelProperty(value = "(机构)审核人", required = false)
    @TableField("verify_user")
    private String verifyUser;

    /**
     * (机构)审核状态(0:未通过，1:通过，2：待审核)
     */

    @ApiModelProperty(value = "(机构)审核状态(0:未通过，1:通过，2：待审核)", required = false)
    @TableField("verify_status")
    private String verifyStatus;

    /**
     * (机构)未通过的审核意见
     */
    @ApiModelProperty(value = "(机构)未通过的审核意见", required = false)
    @TableField("verify_mark")
    private String verifyMark;
    /**
     * (公有)来源（0：注册，1：后台添加）
     */
    @ApiModelProperty(value = "(公有)来源（0：注册，1：后台添加）", required = false)
    private Integer source;

    /**
     * (机构)审核时间
     */
    @ApiModelProperty(value = "(机构)审核时间", required = false)
    @TableField("verify_date")
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date verifyDate;

    /**
     * 所属组Id
     */
    @ApiModelProperty(value = "所属组Id", required = false)
    @TableField("group_id")
    private String groupId;

    /**
     * 所属组名称
     */
    @ApiModelProperty(value = "所属组名称", required = false)
    @TableField("group_name")
    private String groupName;
    /**
     * 保留属性、备用
     */
    @ApiModelProperty(value = "内部用户，外部用户区分标志 1 内部 2 外部 ", required = false)
    @TableField("system_type")
    private String systemType;

    /**
     * 密码错误次数
     */
    @ApiModelProperty(value = "密码错误次数，超过3次会后续行为", required = false)
    @TableField("miss_num")
    private int missNum;



    @TableField("client_id")
    private String clientId;

    /**
     * 资源卫星账户类型，1 是老用户 0 不是老用户，或者被修改的用户
     */
    @TableField("satellite_type")
    private String satelliteType;

    public String getSatelliteType() {
        return satelliteType;
    }

    public void setSatelliteType(String satelliteType) {
        this.satelliteType = satelliteType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean isAccountNonExpired() { //账户是否过期  true 不过期，false 过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {  //是否被锁 true 没锁， false 锁了
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //是否认证过期  true 没过期，false 过期了
        return true;
    }

    @Override
    public boolean isEnabled() {  //账户是不是被激活
        return true;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 把用户的角色或者组放到这里
     *  暂时没放
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }




}
