package com.kq.auth.handle;

import com.alibaba.fastjson.JSON;

import com.kq.auth.DTO.LoginResult;
import com.kq.auth.constants.CommonConstants;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.domain.UserClient;
import com.kq.auth.service.IBaseUserService;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.auth.service.impl.UserClientServiceImpl;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.exception.BaseException;
import com.kq.common.util.jwt.JWTInfo;
import com.kq.common.util.jwt.JwtUtil;
import com.kq.common.util.jwt.RsaKeyHelper;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

    private String forwardUrl;

    @Value("${jwt.pri.path}")
    private String priKey;
    @Value("${client.expire}")
    private int expire;

    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    public SuccessHandler(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Autowired
    public BaseUserServiceImpl userService;

    @Autowired
    public UserClientServiceImpl userClientService;

    @Autowired
    public ClientDetailsService clientDetailsService;

    @Autowired
    public AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

    @Autowired
    StringRedisTemplate StringRedisTemplate;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        //这里处理基于oauth协议的处理登录流程，并返回token
        //1 通过用户拿clientId 和 秘钥
        BaseUser user = (BaseUser) authentication.getPrincipal();


        String clientId = user.getClientId();


        //第一步 获取clientDetail对象
        ClientDetails  clientDetails  = clientDetailsService.loadClientByClientId(clientId);
        if(clientDetails == null){
            throw new BaseException("clientId配置信息不存在："+clientId,500);
        }
        //第二步 构建TokenRequest 对象
        //原来的逻辑是根据不同的参数，获取不同的认证，基于不同的认证，构建不同的authentication
        //这里内部认证，不需要构建额外的认证。
        //因为是内部认证，第四个参数是自定义
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId,
                clientDetails.getScope(),"custom");

        //第三步 通过TokenRequest 创建 Oauth2Request 对象
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        //第四步  通过OAuth2Request 对象 和 authentication 拼装OAuth2authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);

        //第五步 创建令牌
        OAuth2AccessToken token = defaultAuthorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        //可以根据用户ID进行关联
        StringRedisTemplate.opsForValue().set(CommonConstants.TOKEN+user.getId(),token.getValue(),token.getExpiresIn(), TimeUnit.SECONDS);
        StringRedisTemplate.opsForValue().set(CommonConstants.TOKEN_REFRESH+user.getId(),token.getRefreshToken().getValue());
        //往响应头里添加头
        response.addHeader("Authorization",token.getValue());

        request.getSession().setAttribute("test","1111");

        logger.info("登录成功！！");
    }

    /**
     * 将权限对象放入jwt,生成token
     * @param perimissionInfos
     * @param name
     * @return
     */
    public JWTInfo perimission2jwt(List<PerimissionInfo> perimissionInfos,String name){
        JWTInfo info = new JWTInfo();
        info.setName(name);
        info.setPerimission(perimissionInfos);
        return info;
    }
}
