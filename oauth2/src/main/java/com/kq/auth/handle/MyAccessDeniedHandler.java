package com.kq.auth.handle;

import com.alibaba.fastjson.JSON;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.auth.validate.code.exception.ValidateCodeException;

import com.kq.common.DTO.BaseResponse;
import com.kq.common.DTO.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        if(e instanceof ValidateCodeException){
            String result = JSON.toJSONString(new BaseResponse(500,e.getMessage()));
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(result);
        }else{
            String result = JSON.toJSONString(new BaseResponse(500,"用户名或密码错误或者用户被禁用"));
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(result);
        }

    }



}
