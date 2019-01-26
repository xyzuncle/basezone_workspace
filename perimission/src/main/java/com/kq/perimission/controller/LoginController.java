package com.kq.perimission.controller;


import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.util.jwt.JWTInfo;
import com.kq.common.util.jwt.JwtUtil;
import com.kq.perimission.controller.rpc.service.PerimissionService;
import com.kq.perimission.service.impl.BaseUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {


    @Autowired
    BaseUserServiceImpl baseUserService;

    @Autowired
    PerimissionService perimissionService;

    @Value("${jwt.pri.path}")
    private String priKey;
    @Value("${client.expire}")
    private int expire;
    @Value("${spring.prefix}")
    private String prefix;


/*    @RequestMapping(value = "/login")
    public String login(){
      *//*  ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("prefix",prefix);
        modelAndView.setViewName("home");*//*
        return "4234324";
    }*/

    /**
     * 处理登录的请求
     * @param username
     * @param password
     * @throws Exception
     */

    @RequestMapping(value = "/loginToken",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse loginToken(@ApiParam(value = "登录名") String username, @ApiParam(value = "用户密码") String password) throws Exception {
        String token = null;
        try {
            List<PerimissionInfo> infoList = perimissionService.getPermissionByUsername(username);
            JWTInfo info = perimission2jwt(infoList, username);
            token = JwtUtil.genToken(info, priKey, expire);
            //response.addHeader("Authorization", "Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ObjectRestResponse().data(token);
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


    @RequestMapping(value = "/test/session",method = RequestMethod.GET)
    @ResponseBody
    public String loginToken(@RequestParam("browser") String browser,HttpServletRequest request, HttpSession session) throws Exception {
        //取出session中的browser
        Object sessionBrowser = session.getAttribute("browser");
        if (sessionBrowser == null) {
            System.out.println("不存在session，设置browser=" + browser);
            session.setAttribute("browser", browser);
        } else {
            System.out.println("存在session，browser=" + sessionBrowser.toString());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
            }
        }
        return "";

    }

  /*  @Autowired
    FindByIndexNameSessionRepository<? extends ExpiringSession> sessionRepository;

    @RequestMapping("/test/findByUsername")
    @ResponseBody
    public Map findByUsername(@RequestParam String username) {
        Map<String, ? extends ExpiringSession> usersSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
        return usersSessions;
    }*/
}

