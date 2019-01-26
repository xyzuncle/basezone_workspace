package com.kq.auth.handle;

import com.alibaba.fastjson.JSON;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.common.DTO.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 成功登出后需要执行的行为
 */
public class LoginOutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    public BaseUserServiceImpl userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String result = JSON.toJSONString(new ObjectRestResponse().data("true").message("用户退出！"));
        SecurityContext context = SecurityContextHolder.getContext();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result);
    }
}
