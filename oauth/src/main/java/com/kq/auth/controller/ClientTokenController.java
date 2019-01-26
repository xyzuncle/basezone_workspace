package com.kq.auth.controller;

import com.alibaba.fastjson.JSON;
import com.kq.auth.DTO.ClientInfo;

import com.kq.auth.DTO.LoginResult;
import com.kq.auth.jwt.ClientTokenUtil;



import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.constants.CommonConstants;
import com.kq.common.util.jwt.JWTInfo;
import com.kq.common.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

@RestController
@RequestMapping("token")
public class ClientTokenController {

    @Value("${jwt.pri.path}")
    private String priKey;
    @Value("${client.expire}")
    private int expire;

    //通过rpc的调用，重新生成一个token
    @RequestMapping(value = "/genJwtToken",method = RequestMethod.GET)
    public void genJwtToken(@RequestParam("userName") String userName,
                            @RequestHeader HttpServletResponse response){
        JWTInfo info = new JWTInfo(userName);
        String token = null;
        try {
            token = JwtUtil.genToken(info,priKey,expire);
            LoginResult loginResult = new LoginResult();
            loginResult.setToken(token);
            String result = JSON.toJSONString(new ObjectRestResponse().data(loginResult).message("颁发token成功").status(CommonConstants.TOKEN_EXPIRED));
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
            String result = JSON.toJSONString(new ObjectRestResponse().data("false").message("颁发token失败").status(500));
            response.setContentType("application/json;charset=utf-8");
            try {
                response.getWriter().write(result);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
