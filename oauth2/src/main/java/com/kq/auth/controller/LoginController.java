package com.kq.auth.controller;


import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.util.jwt.JWTInfo;
import com.kq.common.util.jwt.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
/*import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;*/
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Api(value = "登录入口",description = "登录入口，专门给前端写的")
@RestController
public class LoginController {
    @Autowired
    private DiscoveryClient discoveryClient;


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
     * @param
     * @param password
     * @throws Exception
     */
    @ApiOperation(value = "登录入口",notes = "登录入口，特意给前端写的,通过swagger不能够直接调用,必须通过类似与postman，或者前端ajax调用，才能正常返回结果，必须是post方式.,调用路径是" +
            "http://所在服务器IP:8085/uaa/loginToken")
    @RequestMapping(value = "/loginToken",method = RequestMethod.POST)
    public ObjectRestResponse loginToken(@ApiParam(value = "手机号码")  @RequestBody String username, @ApiParam(value = "用户密码") @RequestBody String password) throws Exception {
        String token = null;
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ObjectRestResponse().data(token);
    }

    /**
     * 用户登出
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "用户登出",notes = "用户退出登录" +
            "http://所在服务器IP:8085/uaa/logout")
    @RequestMapping(value = "/userLogout",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse logOut( ) throws Exception {
        return new ObjectRestResponse().data("用户退出成功");
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

 /*   @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @RequestMapping("/test/findByUsername")
    @ResponseBody
    public Map findByUsername(@RequestParam String username,HttpServletRequest request) {
        Map<String, ? extends Session> usersSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
        String sessionId = request.getSession().getId();
        String browserName = usersSessions.get(sessionId).getAttribute("browser");
        System.out.println("我获取到的browserName=="+browserName);

        return usersSessions;
    }
*/
 /*   @RequestMapping(value = "/test/session",method = RequestMethod.GET)
    @ResponseBody
    public String loginToken(@RequestParam("browser") String browser, HttpServletRequest request, HttpSession session) throws Exception {
        //取出session中的browser
        Object sessionBrowser = session.getAttribute("browser");
        if (sessionBrowser == null) {
            System.out.println("不存在session，设置browser=" + browser);

            session.setAttribute("browser", browser);
        } else {
            System.out.println("存在session，browser=" + sessionBrowser.toString());
        }

        ServiceInstance instance = this.discoveryClient.getLocalServiceInstance();
        System.out.println("执行当前任务的端口是===="+instance.getPort());
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
            }
        }
        return "";

    }*/
}

