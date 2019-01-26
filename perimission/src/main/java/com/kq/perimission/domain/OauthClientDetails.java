package com.kq.perimission.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 客户端ID表字段的维护
 * </p>
 *
 * @author yerui
 * @since 2018-07-31
 */
@ApiModel(value = "客户端先关实体")
@TableName("oauth_client_details")
public class OauthClientDetails extends Model<OauthClientDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * clientID主键
     */
    @ApiModelProperty(value = "唯一标识", required = true)
    @TableId("client_id")
    private String clientId;
    /**
     * 关联的资源IDS
     */
    @ApiModelProperty(value = "关联的资源IDS", required = false)
    @TableField("resource_ids")
    private String resourceIds;
    /**
     * client的秘钥
     */
    @ApiModelProperty(value = "client的秘钥", required = false)
    @TableField("client_secret")
    private String clientSecret;
    /**
     * 被授权的作用域
     */
    @ApiModelProperty(value = "被授权的作用域", required = false)
    private String scope;
    /**
     * 被授权的类型
     */
    @ApiModelProperty(value = "被授权的类型 authorization_code refresh_token password", required = false)
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;
    /**
     * 授权码模式需要跳转的确认授权页面
     */
    @ApiModelProperty(value = "授权码模式需要跳转的确认授权页面", required = false)
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;
    /**
     * 权限的字符串
     */
    @ApiModelProperty(value = "权限的字符串", required = false)
    private String authorities;
    /**
     * token的有效时间
     */
    @ApiModelProperty(value = "token的有效时间", required = false)
    @TableField("access_token_validity")
    private Integer accessTokenValidity;
    /**
     * 刷新的token时间
     */
    @ApiModelProperty(value = "刷新的token时间", required = false)
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;
    /**
     * 附加的信息
     */
    @ApiModelProperty(value = "附加的信息", required = false)
    @TableField("additional_information")
    private String additionalInformation;
    /**
     * 授权页面是否自动授权
     */
    @ApiModelProperty(value = "授权页面是否自动授权", required = false)
    private String autoapprove;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoapprove() {
        return autoapprove;
    }

    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
    }

    @Override
    protected Serializable pkVal() {
        return this.clientId;
    }

    @Override
    public String toString() {
        return "OauthClientDetails{" +
        ", clientId=" + clientId +
        ", resourceIds=" + resourceIds +
        ", clientSecret=" + clientSecret +
        ", scope=" + scope +
        ", authorizedGrantTypes=" + authorizedGrantTypes +
        ", webServerRedirectUri=" + webServerRedirectUri +
        ", authorities=" + authorities +
        ", accessTokenValidity=" + accessTokenValidity +
        ", refreshTokenValidity=" + refreshTokenValidity +
        ", additionalInformation=" + additionalInformation +
        ", autoapprove=" + autoapprove +
        "}";
    }
}
