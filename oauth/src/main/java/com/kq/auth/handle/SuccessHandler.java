package com.kq.auth.handle;

import com.alibaba.fastjson.JSON;
import com.kq.auth.DTO.LoginResult;
import com.kq.auth.config.CustomSessionRegistry;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.IBaseUserService;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.auth.service.impl.RemainingNumberServiceImpl;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.constants.CommonConstants;
import com.kq.common.exception.BaseException;
import com.kq.common.util.IPUtil;
import com.kq.common.util.jwt.JWTInfo;
import com.kq.common.util.jwt.JwtUtil;
import com.kq.common.util.jwt.RsaKeyHelper;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SuccessHandler implements
        AuthenticationSuccessHandler {

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

    @Autowired
    CustomSessionRegistry sessionRegistry;


    @Autowired
    public BaseUserServiceImpl userService;

    @Autowired
    public RemainingNumberServiceImpl remainingNumberService;

    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logger.info("登录成功！！");
        //认证成功后，可以把用户相关信息放到session
        String token =null;
        BaseUser baseUser = (BaseUser) authentication.getPrincipal();
        String userName = authentication.getName().toString();

        String ip = IPUtil.getIpAddr(request);
        baseUser.setIpAddress(ip);

        //登录成功后，把用户信息放入redis，方便获取
        redisTemplate.opsForValue().set(userName, JSON.toJSON(baseUser).toString(), CommonConstants.EXPIRES_TIME, TimeUnit.SECONDS);
        //登录成功后，把改用户的查询权限放入redis
        //putRedisMap(baseUser.getId());

        //当用户登录成功的时候 初始化一次所有的在线用户
        //sessionRegistry.getAllPrincipals();

        JWTInfo info = new JWTInfo(userName);
        info.setUserType(baseUser.getSystemType());
        try {
            token = JwtUtil.genToken(info,priKey,CommonConstants.EXPIRES_TIME);
           // response.addHeader("Authorization", "Bearer " + token);
            //序列化结果，传递到ajax

            LoginResult loginResult = new LoginResult();
            loginResult.setUsername(userName);
            loginResult.setName(baseUser.getName());
            loginResult.setSystemType(baseUser.getSystemType());
            loginResult.setTime(DateTime.now().toString("yyyy-MM-dd"));

             String hiddenPhone = "";
            if(baseUser.getMobilePhone().length()<=7){
                throw new BaseException("手机号码异常!",500);
            }else if(baseUser.getMobilePhone().length()>7){
                hiddenPhone = baseUser.getMobilePhone().substring(0, 3) + "****" + baseUser.getMobilePhone().substring(7);
            }
            loginResult.setMobileNum(hiddenPhone);
            loginResult.setToken(token);
            //根据用户名修改该用户的错误登录次数
            userService.updateMissNumByUserName(userName);
            String result = JSON.toJSONString(new ObjectRestResponse().data(loginResult).message("登录成功！"));
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(result);
            //登录成功后需要跳转的界面
           // request.getRequestDispatcher(forwardUrl).forward(request, response);
            //跳转到一个自己的页面
             //redirectStrategy.sendRedirect(request,response,this.forwardUrl);
            //跳转之前的页面
           // super.onAuthenticationSuccess(request, response, authentication);

            //getRedirectStrategy().sendRedirect(request,response,this.forwardUrl);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("生成token异常");
            String result = JSON.toJSONString(new ObjectRestResponse().data("").message("用户名或密码错误或token异常").status(500));
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(result);
        }


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

    /**
     * 根据用户ID 保存在redis
     * @param userId
     */
    public void putRedisMap(String userId) {
        if(StringUtils.isNotBlank(userId)){
            remainingNumberService.putReidsDateRule("1",userId);
        }

    }
}
