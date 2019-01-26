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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@ApiModel(value = "元素表单")
@TableName("base_element")
public class BaseElement extends Model<BaseElement> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识", required = true)
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 资源编码
     */
    @ApiModelProperty(value = "资源编码", required = false)
    private String code;
    /**
     * 所属微服务资源的类型
     */
    @ApiModelProperty(value = "所属微服务资源的类型", required = false)
    @TableField("type")
    private String type;

    /**
     * 所属微服务的名称
     */
    @ApiModelProperty(value = "所属微服务的名称", required = false)
    @TableField("name")
    private String name;

    /**
     * api路径
     */

    @ApiModelProperty(value = "api路径", required = false)
    @TableField("uri")
    private String uri;


    /**
     * 资源请求类型
     */
    @ApiModelProperty(value = "资源请求类型", required = false)
    @TableField("method")
    private String method;

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

  /*  @ApiModelProperty(value = "客户端id", required = false)
    @TableField("client_id")
    private String clientId;
    @ApiModelProperty(value = "用户ID", required = false)
    @TableField("user_id")*/
  /*  private String userId;*/

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

   /* @ApiModelProperty(value = "被调用的上限次数", required = false)
    @TableField("calls_num")
    private Integer callsNum;

    @ApiModelProperty(value = "剩余被调用的API次数", required = false)
    @TableField("calls_num")
    private Integer callsRemainNum;

    @ApiModelProperty(value = "一分钟被调用的频率上限", required = false)
    @TableField("calls_frequency")
    private Integer callsFrequency;*/


    @ApiModelProperty(value = "API是否可以被调用 0不可调用 1可以调用", required = false)
    private String status;



  /*  public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }*/

   /* public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

   /* public Integer getCallsNum() {
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
*/
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
        return "BaseElement{" +
                ", id=" + id +
                ", code=" + code +
                ", type=" + type +
                ", name=" + name +
                ", uri=" + uri +
                ", method=" + method +
                ", description=" + description +
                ", crtTime=" + crtTime +
                ", crtUser=" + crtUser +
                ", crtName=" + crtName +
                ", crtHost=" + crtHost +
                ", attr3=" + attr3 +
                ", attr4=" + attr4 +
                ", attr5=" + attr5 +
                ", attr6=" + attr6 +
                ", attr7=" + attr7 +
                ", attr8=" + attr8 +
                "}";
    }
}
