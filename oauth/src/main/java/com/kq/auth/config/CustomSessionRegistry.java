package com.kq.auth.config;

import com.alibaba.fastjson.JSON;

import com.kq.auth.domain.BaseOnlineUser;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.impl.BaseOnlineUserServiceImpl;
import com.kq.auth.service.impl.UserTypeServiceImpl;
import com.kq.common.constants.CommonConstants;
import com.kq.common.ip.IPinfo;
import com.kq.common.util.IPUtil;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;

import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description: spingsession 不支持所有的在线用户统计
 *               所以继承了他的实现类，自己重写方法
 * @Author: yerui
 * @CreateDate : 2019/1/2 14:50
 * @Version: 1.0
 *
 */

public class CustomSessionRegistry extends SpringSessionBackedSessionRegistry {

    private static final String SESSIONIDS = "sessionIds";

    private static final String PREFIX_KEYS = "spring:session:sessions:";

    private static final String PREFIX_EXPIRES_KEYS = "spring:session:sessions:expires:";

    private static final String PREFIX_ONLIEN_USER_KEYS = "spring:online:";

    private Set<String> userNameSet = new HashSet<>();

    private final FindByIndexNameSessionRepository<ExpiringSession> sessionRepository;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    HttpServletRequest request;

    @Autowired
    BaseOnlineUserServiceImpl baseOnlineUserService;
    @Autowired
    UserTypeServiceImpl userTypeService;

    @Autowired
    RedisOperationsSessionRepository redisOperationsSessionRepository;

    public CustomSessionRegistry(FindByIndexNameSessionRepository<ExpiringSession> sessionRepository) {
        super(sessionRepository);
        this.sessionRepository = sessionRepository;
    }


    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
       // redisOperationsSessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,"admin");
        List<SessionInformation> sessionInformationList = super.getAllSessions(principal, includeExpiredSessions);
        //保存用户与sessionId之间的关系
        sessionInformationList.stream().forEach(e->{
           //stringRedisTemplate.opsForValue().set(PREFIX_ONLIEN_USER_KEYS+e.getPrincipal(),PREFIX_EXPIRES_KEYS+e.getSessionId());

        });
        return sessionInformationList;
    }

/*

    public List<BaseOnlineUser> getOnlineUser(@RequestParam("userType") String userType,
                                              @RequestParam("userName") String userName,
                                              @RequestParam("industry") String industry){

        Set<String> keys = stringRedisTemplate.keys(PREFIX_ONLIEN_USER_KEYS + "*");
        Map<Integer, String> industryMap = userTypeService.getIndustryType();
        Map<Integer, String> userTypeMap = userTypeService.getUserTypeMap();
        List<BaseOnlineUser> baseOnlineUsersList = new ArrayList<>();
        keys.stream().forEach(e->{
            String sessionIds = stringRedisTemplate.opsForValue().get(e);
            boolean result = stringRedisTemplate.hasKey(sessionIds);
            if(result==true){
                //拼装在线的数据
                //根据userName获取用户数据
                String json = stringRedisTemplate.opsForValue().get(e);
                BaseOnlineUser baseOnlineUser =null;
                BaseUser user = (BaseUser) JSON.parseObject(json,BaseUser.class);
                if(StringUtils.isNotBlank(json)){

                    if(userType.equals(user.getType())){
                        baseOnlineUser =  buildBaseOnlineUser(baseOnlineUser,user,userName,industry);
                    }


                    baseOnlineUser = new BaseOnlineUser();

                    baseOnlineUser.setUserName(user.getName());
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
                //如果
                if(baseOnlineUser!=null){
                    baseOnlineUsersList.add(baseOnlineUser);
                }
            }

        });
        return baseOnlineUsersList;
    }


    public BaseOnlineUser buildBaseOnlineUser(BaseOnlineUser baseOnlineUser,BaseUser user,
                                              String userName,String industry){
        Map<Integer, String> industryMap = userTypeService.getIndustryType();
        Map<Integer, String> userTypeMap = userTypeService.getUserTypeMap();

        baseOnlineUser = new BaseOnlineUser();

        baseOnlineUser.setUserName(user.getName());
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
        if(areaMap!=null && areaMap.size()>0) {
            if (!areaMap.get("city_code").equals("000000")) {
                if (areaMap.get("continent_name").equals("亚洲")) {
                    String cityName = areaMap.get("city_name").toString();
                    if (StringUtils.isNotBlank(cityName)) {
                        baseOnlineUser.setArea(cityName);
                    } else {
                        baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
                    }
                }

            } else if (areaMap.get("continent_name") != null) {
                if (!areaMap.get("continent_name").equals("亚洲")) {
                    String continentName = areaMap.get("continent_name").toString();
                    if (StringUtils.isNotBlank(continentName)) {
                        baseOnlineUser.setArea(continentName);
                    } else {
                        baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
                    }
                }
            } else {
                //查找不到 就设置区域为其他
                baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
            }
        }
        return baseOnlineUser;
    }*/



//    /**
//     * 获取在线所有session的信息
//     * @return
//     */
//    public Set<String> getPrincipalsKeySet() {
//        Set<String> keys = stringRedisTemplate.keys(PREFIX_KEYS + "*");
//        //获取过期的sessionID,用于后续比较
//        Map<String,String> expireList = new HashMap<>();
//        Set<String> NoExPkeys = new HashSet<>();
//        keys.stream().forEach(e->{
//            String sessionid = null;
//            if(e.contains("expires")){
//                //获取过期的sessionID
//                 sessionid = StringUtils.substringAfterLast(e, PREFIX_EXPIRES_KEYS);
//                 expireList.put(PREFIX_KEYS + sessionid, PREFIX_KEYS + sessionid);
//            }else{
//                NoExPkeys.add(e);
//            }
//
//        });
//
//        Set<String> finalResult = new HashSet<>();
//        NoExPkeys.stream().forEach(e -> {
//            if(expireList.get(e)!=null){
//                String sessionid = StringUtils.substringAfterLast(e, PREFIX_KEYS);
//                finalResult.add(sessionid);
//            }
//        });
//
//        return finalResult;
//    }
//
//
//    public List<Object> loadOnlieSessionUser(){
//        Map<Integer, String> industryMap = userTypeService.getIndustryType();
//        Map<Integer, String> userTypeMap = userTypeService.getUserTypeMap();
//        List<Object> keys = new ArrayList<>(this.getPrincipalsKeySet());
//        //这个sessioninfomation包含prical和sessionId
//        List<BaseOnlineUser> baseOnlineUsersList = new ArrayList<>();
//        keys.stream().forEach(key->{
//            SessionInformation sessionInformation =   this.getSessionInformation(key.toString());
//            //如果等于空 证明过期了
//            if(sessionInformation!=null){
//                //根据sessionId 获取session用户信息,并封装在集合中，供前端调用
//                BaseOnlineUser baseOnlineUser =null ;
//                String userName = sessionInformation.getPrincipal().toString();
//
//                //按理说 按理说不应该有空
//                String json = stringRedisTemplate.opsForValue().get(userName);
//                if(StringUtils.isNotBlank(json)){
//                    baseOnlineUser = new BaseOnlineUser();
//                    BaseUser user = (BaseUser) JSON.parseObject(json,BaseUser.class);
//                    baseOnlineUser.setUserName(user.getName());
//                    baseOnlineUser.setMobile(user.getMobilePhone());
//                    //获取用户类型转码
//                    if(userTypeMap.get(Integer.parseInt(user.getType()))==null){
//                        baseOnlineUser.setUserType(user.getType());
//                    }else{
//                        baseOnlineUser.setIndustry(userTypeMap.get(Integer.parseInt(user.getType())));
//                    }
//
//                    //获取用户行业转码
//                    if(industryMap.get(Integer.parseInt(user.getPplappfield()))==null){
//                        baseOnlineUser.setIndustry(user.getType());
//                    }else{
//                        baseOnlineUser.setUserType(userTypeMap.get(Integer.parseInt(user.getPplappfield())));
//                    }
//
//
//                    //设置登录的地域信息
//                    String ip =  IPUtil.getIpAddr(request);
//                    Map<String,Object> areaMap = null;
//                    try {
//                        areaMap = IPinfo.getIPtoName(ip);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (GeoIp2Exception e) {
//                        e.printStackTrace();
//                    }
//                    if(areaMap!=null && areaMap.size()>0){
//                        if(!areaMap.get("city_code").equals("000000")){
//                            if(areaMap.get("continent_name").equals("亚洲")){
//                                String cityName = areaMap.get("city_name").toString();
//                                if(StringUtils.isNotBlank(cityName)){
//                                    baseOnlineUser.setArea(cityName);
//                                }else{
//                                    baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
//                                }
//                            }
//
//                        }
//                        else if(areaMap.get("continent_name")!=null){
//                            if(!areaMap.get("continent_name").equals("亚洲")){
//                                String continentName = areaMap.get("continent_name").toString();
//                                if(StringUtils.isNotBlank(continentName)){
//                                    baseOnlineUser.setArea(continentName);
//                                }else{
//                                    baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
//                                }
//                            }
//                        }else{
//                            //查找不到 就设置区域为其他
//                            baseOnlineUser.setArea(CommonConstants.AREA_OTHER);
//                        }
//                    }
//                }
//                if(baseOnlineUser!=null){
//                    baseOnlineUsersList.add(baseOnlineUser);
//                }
//
//
//
//            }
//        });
//        //保存到表中，每次登陆同步一下表
//        //后期有时间，看看redis如何做条件查询和分页
//        if(baseOnlineUsersList.size()>0){
//            //先清除所有，然后同步数据到库中
//            baseOnlineUserService.delete(null);
//            baseOnlineUserService.insertBatch(baseOnlineUsersList);
//        }else{
//            //如果没有就证明session中都过期了，删除
//            baseOnlineUserService.delete(null);
//        }
//        return keys;
//    }

}
