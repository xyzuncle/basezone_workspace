package com.kq.auth.controller;

import com.alibaba.fastjson.JSON;
import com.kq.auth.config.CustomSessionRegistry;
import com.kq.common.DTO.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/baseOnline")
public class BaseOnlineUserController {

    @Autowired
    CustomSessionRegistry customSessionRegistry;

   /* @Autowired
    SessionRegistryImpl sessionRegistry;
*/

    //所有在线用户进行初始化
    @RequestMapping("/allOnlineUser")
    @ResponseBody
    public Object getAllOnlineUser(Authentication authentication){
        List<SessionInformation> allPrincipals = customSessionRegistry.getAllSessions(authentication.getPrincipal(),true);
        allPrincipals.stream().forEach(e->{
            System.out.println(e.getPrincipal());
            System.out.println(e.isExpired());
            System.out.println(e.getSessionId());
        });
        return JSON.toJSON(new ObjectRestResponse().data(allPrincipals).message("测试在线session").rel(true));
    }


}
