package com.kq.auth.handle;

import com.alibaba.fastjson.JSON;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.common.DTO.BaseResponse;
import com.kq.common.DTO.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * 登录失败的handel 走这里
 */

public class MyAccessDeniedHandler implements AuthenticationFailureHandler {

    private String errorPage;

    public MyAccessDeniedHandler() {
    }

    public MyAccessDeniedHandler(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }


    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

            if(e.getMessage().equals("Bad credentials")){
                String result = JSON.toJSONString(new BaseResponse(500,"用户名或密码错误"));
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(result);
            }else if(e.getMessage().equals("该账户被禁用无法登陆")){
                String result = JSON.toJSONString(new BaseResponse(500,"该用户被禁用，请联系管理员！"));
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(result);
            }else if(e.getMessage().startsWith("nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne()")){
                String result = JSON.toJSONString(new BaseResponse(500,"资源卫星老用户重复或数据异常，请联系管理员！"));
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(result);
            }else if(e.getMessage().equals("用户不存在")){
                String result = JSON.toJSONString(new BaseResponse(500,"用户不存在"));
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(result);
            }else if(e.getMessage().startsWith("Maximum sessions")){
                String result = JSON.toJSONString(new BaseResponse(500,"此用户已经在别处登录."));
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(result);
            }
            else{
                String result = JSON.toJSONString(new BaseResponse(500,"用户名或密码错误"));
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(result);
            }


    }



}
