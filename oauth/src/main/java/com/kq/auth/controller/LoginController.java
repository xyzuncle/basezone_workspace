package com.kq.auth.controller;


import com.alibaba.fastjson.JSON;
import com.kq.auth.domain.BaseOnlineUser;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.impl.BaseOnlineUserServiceImpl;
import com.kq.auth.service.impl.UserTypeServiceImpl;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.constants.CommonConstants;
import com.kq.common.ip.IPinfo;
import com.kq.common.util.IPUtil;
import com.kq.common.util.jwt.JWTInfo;
import com.kq.common.util.jwt.JwtUtil;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(value = "登录入口",description = "登录入口，专门给前端写的")
@Controller
public class LoginController {
    @Autowired
    private DiscoveryClient discoveryClient;


    @Value("${jwt.pri.path}")
    private String priKey;
    @Value("${client.expire}")
    private int expire;
    @Value("${spring.prefix}")
    private String prefix;

    private static final String PREFIX_EXPIRES_KEYS = "spring:session:sessions:expires:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @Autowired
    BaseOnlineUserServiceImpl baseOnlineUserService;

    @Autowired
    UserTypeServiceImpl userTypeService;


    /**
     * 处理登录的请求
     * @param
     * @param password
     * @throws Exception
     */
    @ApiOperation(value = "登录入口",notes = "登录入口，特意给前端写的,通过swagger不能够直接调用,必须通过类似与postman，或者前端ajax调用，才能正常返回结果，必须是post方式.,调用路径是" +
            "http://所在服务器IP:8085/uaa/loginToken")
    @RequestMapping(value = "/loginToken",method = RequestMethod.POST)
    public ObjectRestResponse loginToken(@ApiParam(value = "手机号码")  @RequestParam String username, @ApiParam(value = "用户密码") @RequestParam String password) throws Exception {
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
            "http://所在服务器IP:8085/uaa/cuslogout")
    @RequestMapping(value = "/cuslogout",method = RequestMethod.GET)
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
       /* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            System.out.println("我被执行了。。。。。。。。。。。。。。。");
            new SecurityContextLogoutHandler().logout(request, response, auth);
            String result = JSON.toJSONString(new ObjectRestResponse().data("true").message("用户退出！"));
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(result);
        }*/
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

    @ApiOperation(value = "同步集群session数据",notes = "用户登录后调用" +
            "http://所在服务器IP:8085/uaa/session/asyncByUsername")
    @RequestMapping("/session/asyncByUsername")
    @ResponseBody
    public String findByUsername(@RequestParam String username,HttpServletRequest request) {
        String result = "";
        try{
            if(StringUtils.isNotBlank(username)){
                Map<String, ? extends Session> usersSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
                usersSessions.keySet().stream().forEach(e->{
                    BaseOnlineUser baseOnlineUser = null;
                    Map<Integer, String> industryMap = userTypeService.getIndustryType();
                    Map<Integer, String> userTypeMap = userTypeService.getUserTypeMap();
                    //保存到表里头
                    String json = stringRedisTemplate.opsForValue().get(username);
                    if(StringUtils.isNotBlank(json)){
                        baseOnlineUser = new BaseOnlineUser();
                        BaseUser user = (BaseUser) JSON.parseObject(json,BaseUser.class);
                        baseOnlineUser.setUserName(user.getName());
                        baseOnlineUser.setName(user.getUsername());
                        baseOnlineUser.setMobile(user.getMobilePhone());
                        //获取用户类型转码
                        if(userTypeMap.get(Integer.parseInt(user.getType()))==null){
                            baseOnlineUser.setUserType(user.getType());
                        }else{
                            baseOnlineUser.setIndustry(userTypeMap.get(Integer.parseInt(user.getType())));
                        }
                        //获取用户行业转码
                        if(industryMap.get(Integer.parseInt(user.getPplappfield()))==null){
                            baseOnlineUser.setIndustry(user.getType());
                        }else{
                            baseOnlineUser.setUserType(userTypeMap.get(Integer.parseInt(user.getPplappfield())));
                        }
                        //绑定当前在线session在线ID
                        baseOnlineUser.setSessionId(PREFIX_EXPIRES_KEYS+e);

                        //设置登录的地域信息
                        String ip =  IPUtil.getIpAddr(request);
                        Map<String,Object> areaMap = null;
                        try {
                            areaMap = IPinfo.getIPtoName(ip);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (GeoIp2Exception geoException) {
                            geoException.printStackTrace();
                        }
                        if(areaMap!=null && areaMap.size()>0){
                            if(!areaMap.get("city_code").equals("000000")){
                                if(areaMap.get("continent_name").equals("亚洲")){
                                    String cityName = areaMap.get("city_name").toString();
                                    if(StringUtils.isNotBlank(cityName)){
                                        baseOnlineUser.setArea(cityName);
                                    }else{
                                        baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
                                    }
                                }
                            }
                            else if(areaMap.get("continent_name")!=null){
                                if(!areaMap.get("continent_name").equals("亚洲")){
                                    String continentName = areaMap.get("continent_name").toString();
                                    if(StringUtils.isNotBlank(continentName)){
                                        baseOnlineUser.setArea(continentName);
                                    }else{
                                        baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
                                    }
                                }
                            }else{
                                //查找不到 就设置区域为其他
                                baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
                            }
                        }
                    }
                    if(baseOnlineUser!=null){
                        //保存数据集到在线用户表
                        baseOnlineUserService.insert(baseOnlineUser);
                    }
                });
                result = JSON.toJSON(new ObjectRestResponse().data("true").message("同步成功").status(200)).toString();
            }

        }catch (Exception e){
            e.printStackTrace();
            result = JSON.toJSON(new ObjectRestResponse().data("false").message("同步失败").status(500)).toString();
        }

        return  result;
    }


    @RequestMapping("/invalidSessionAction")
    @ResponseBody
    public void UserInvailAction(HttpServletResponse response){
        String result = JSON.toJSONString(new ObjectRestResponse().data("false").message("session失效").status(CommonConstants.INVALID_SESSION));
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   /* @RequestMapping(value = "/test/session",method = RequestMethod.GET)
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

