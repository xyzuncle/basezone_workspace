package com.kq.api.filter;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kq.api.rpc.IDeployPermissionService;
import com.kq.api.rpc.IUserService;
import com.kq.api.utils.UserAgentGetter;
import com.kq.common.DTO.MetadataParams;
import com.kq.common.constants.CommonConstants;
import com.kq.common.domain.BaseUser;
import com.kq.common.domain.ParamsModel;
import com.kq.common.util.IPUtil;
import com.kq.common.util.jwt.JWTInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 */
@Component
public class IPFilter extends ZuulFilter {
    private final static Logger logger = LoggerFactory.getLogger(IPFilter.class);

    @Value("${font.perimission.url}")
    String fontUrl;

    @Autowired
    private IUserService iUserService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IDeployPermissionService deployPermissionService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if(ctx.getResponseStatusCode()==CommonConstants.ZUUL_ERROR
                || ctx.getResponseStatusCode()==CommonConstants.QUERY_PERIMISSION
                || ctx.getResponseStatusCode()==CommonConstants.TOKEN_EXPIRED){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        HttpServletResponse response =  ctx.getResponse();
        String requestUrl = req.getRequestURI();
        if(requestUrl.equals(fontUrl)){
            JWTInfo infoFromToken = (JWTInfo) ctx.get("jwtuser");
            if(infoFromToken==null){
                //有可能是游客的
                return null;
            }else{
                //只过滤post方法
                if(req.getMethod().equalsIgnoreCase("post")){
                    BaseUser user = iUserService.getUserByName(infoFromToken.getUsername());
                    String loginUserName = user.getUsername();
                    String userId = user.getId();
                    String satelliteType = "";
                    String satelliteId = "";
                    String sensorId = "";
                    String level = "";
                    String body = null;
                    Map<String, String> resultMap = null;
                    //检查是不是一个RequestBody方法,如果是进去
                    if (!ctx.isChunkedRequestBody()){
                        ServletInputStream inp = null;
                        try {
                            inp = ctx.getRequest().getInputStream();

                            if (inp != null) {
                                body =  IOUtils.toString(inp);
                                //" [\"GX\",{\"area\":\"110000\",\"areaname\":\"北京市\",\"scenetime\":[\"2017-01-01\",\"2017-12-31\"],\"prodlevel\":\"1\",\"imagerslx\":\">0\",\"satelliteSensor\":[\"GF1_PMS\"]}]"
                                Object listArray = JSON.parse(body);
                                if(listArray instanceof JSONArray){
                                    JSONArray  bodyArr = (JSONArray) listArray;
                                    satelliteType = (String) bodyArr.get(0).toString().toUpperCase();
                                    level = JSON.parseObject(bodyArr.get(1).toString()).get("prodlevel").toString();
                                    if(StringUtils.isNotBlank(level)){
                                            //先判断是否有查询权限，然后在判断是否是内网外
                                            JSONArray sensorList = (JSONArray) JSON.parseObject(bodyArr.get(1).toString()).get("satelliteSensor");
                                            resultMap = getRuleMap(sensorList);
                                    }

                                }else if(listArray instanceof JSONObject){
                                    JSONObject object = (JSONObject) listArray;
                                    //JSONObject object = JSON.parseObject("{\"page\":1,\"size\":20,\"geom\":[\"POLYGON((103.47617780062667 38.38817227376516,103.47617780062667 35.12231284151947,108.9192768543695 35.12231284151947,108.9192768543695 38.38817227376516,103.47617780062667 38.38817227376516))\"],\"scenetime\":[\"2019-1-2\",\"2019-1-9\"],\"satelliteSensor\":[\"GF1_WFV\",\"GF1_PMS\"],\"prodlevel\":\"1\",\"imagerslx\":\"0-400\",\"cloudpsd\":60,\"dwxtype\":\"gx\",\"desc\":[\"scenetime\"]}");
                                    satelliteType = object.get("dwxtype").toString().toUpperCase();
                                    level = object.get("prodlevel").toString();
                                    //只有零级采取判断前端的权限
                                    if(StringUtils.isNotBlank(level)){
                                            JSONArray sensorList = (JSONArray) object.get("satelliteSensor");
                                            resultMap = getRuleMap(sensorList);
                                    }
                                    //paramsModel = JSON.parseObject("{\"page\":1,\"size\":20,\"geom\":[\"POLYGON((103.47617780062667 38.38817227376516,103.47617780062667 35.12231284151947,108.9192768543695 35.12231284151947,108.9192768543695 38.38817227376516,103.47617780062667 38.38817227376516))\"],\"scenetime\":[\"2019-1-2\",\"2019-1-9\"],\"satelliteSensor\":[\"GF1_WFV\",\"GF1_PMS\"],\"prodlevel\":\"1\",\"imagerslx\":\"0-400\",\"cloudpsd\":60,\"dwxtype\":\"gx\",\"desc\":[\"scenetime\"]}",ParamsModel.class);
                                }


                            }
                            //这里调用基于redis的用户数据权限
                            if(stringRedisTemplate.hasKey(CommonConstants.REDIS_PERIMISSION_PRE+userId)==false){
                                iUserService.getRidesMapByUserId("1",userId,level);
                            }

                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    //
                    if(StringUtils.isNotBlank(level)){
                            //获取系统级别卫星查询权限
                            if(stringRedisTemplate.hasKey(CommonConstants.REDIS_PERIMISSION_PRE+userId)==true){
                                JSONObject result =   JSONObject.parseObject(stringRedisTemplate.boundHashOps(CommonConstants.REDIS_PERIMISSION_PRE + userId)
                                        .get(userId).toString());
                                AtomicBoolean queryFlag = new AtomicBoolean(true);

                                //前台参数封装比较
                                JSONObject st = result.getJSONObject(satelliteType);
                                if(st==null){
                                    queryFlag.set(false);
                                }else{
                                    //判断这里是为了防止redis存入空值，
                                    if(resultMap!=null&& resultMap.size()>0){
                                        //循环遍历map的key
                                        for(String key : resultMap.keySet()){
                                            JSONObject satelliteIdObject = st.getJSONObject(key);
                                            if(satelliteIdObject == null){
                                                queryFlag.set(false);
                                                break;
                                            }else{
                                                String sensor = resultMap.get(key);
                                                if(StringUtils.isNotBlank(sensor)){
                                                    //这个结果是前台传入封装的结果
                                                    String[] srResult = sensor.split(",");
                                                    //
                                                    String sortString =  Arrays.toString(org.springframework.util.StringUtils.
                                                            sortStringArray(srResult));
                                                    String sortsubString = StringUtils.substring(sortString, 1, -1).replaceAll(" ", "");
                                                    satelliteIdObject.keySet().stream().forEach(e->{
                                                        if(!e.contains(sortsubString)&&!e.equalsIgnoreCase(sortsubString)){
                                                            queryFlag.set(false);
                                                        }
                                                    });

                                                }

                                            }
                                        }
                                    }else{
                                        logger.info("不符合查询权限！！！");
                                        response.setContentType("text/html;charset=utf-8");
                                        ctx.setResponseBody("没有查询权限!");
                                        ctx.setResponseStatusCode(CommonConstants.QUERY_PERIMISSION);
                                        ctx.setSendZuulResponse(false);
                                        return null;
                                    }
                                }
                                if(queryFlag.get() == false){
                                    logger.info("不符合查询权限！！！");
                                    response.setContentType("text/html;charset=utf-8");
                                    ctx.setResponseBody("没有查询权限!");
                                    ctx.setResponseStatusCode(CommonConstants.QUERY_PERIMISSION);
                                    ctx.setSendZuulResponse(false);
                                    return null;
                                }
                            }else{
                                //如果redis里没有对应权限，则证明该用户没有
                                //设置权限或权限已经过期
                                logger.info("不符合查询权限!");
                                response.setContentType("text/html;charset=utf-8");
                                ctx.setResponseBody("没有查询权限!");
                                ctx.setResponseStatusCode(CommonConstants.QUERY_PERIMISSION);
                                ctx.setSendZuulResponse(false);
                                return null;

                            }
                    }

                    //如果拥有查询权限到了这步骤，开始走彭兴的内外网逻辑
                    MetadataParams metadataParams = JSON.parseObject(body, MetadataParams.class);
                    String fontPerm = deployPermissionService.getDeployPermissionStatus(metadataParams);
                    String permReuslt  =JSON.parseObject(fontPerm).getJSONArray("result").get(0).toString();

                    //T 表示有外网查询权限 F表示没有外网查询权限
                    if(permReuslt.equals("T")){

                    }else if(permReuslt.equals("F")){
                        String ipWhilte = "172.17.202.*;";                  //设置单个IP的白名单
                        // "192.168.2.*;" +                 //设置ip通配符,对一个ip段进行匹配
                        // "192.168.3.17-192.168.3.38";
                        UserAgentGetter userAgentGetter = new UserAgentGetter(req);
                        String ip = userAgentGetter.getIpAddr();
                        if(ip.equals("0:0:0:0:0:0:0:1")){
                            ip = "127.0.0.1";
                        }
                        boolean flag = IPUtil.checkLoginIP(ip,ipWhilte);
                        //如果不在内网，就抛出异常
                        if(flag == false){
                            ctx.setResponseStatusCode(CommonConstants.NETWORK_PERIMISSION);
                            response.setContentType("text/html;charset=utf-8");
                            ctx.setResponseBody("数据未发布，无法访问!");
                            ctx.setSendZuulResponse(false);
                            return null;
                        }
                        //ips.add("0.0.0.0.0.0.0.0.1");
                        //如果返回的结果是false 那么证明外网不可以访问,开始比较IP
                    }
                }
                }

        }
        return null;
    }

    private Map<String, String> getRuleMap(JSONArray tempDate) {
        String satelliteId = null;
        Map<String, String> resultMap = null;
        if(tempDate!=null && tempDate.size()>0){
            resultMap = new HashMap<>();
            for(int i=0;i<tempDate.size();i++){
                String[] tempSatelliteId = tempDate.get(i).toString().split("_");
                satelliteId = tempSatelliteId[0];
                if(resultMap.get(satelliteId)==null){
                    resultMap.put(satelliteId,tempSatelliteId[1]+",");
                }else if(resultMap.get(satelliteId)!=null){
                    String tempSensorId = resultMap.get(satelliteId);
                    String tempResult = tempSensorId + tempSatelliteId[1] + ",";
                    resultMap.put(satelliteId, tempResult);
                }
            }
        }

        Map<String, String> finalResultMap = resultMap;
        resultMap.keySet().stream().forEach(e->{
            String sensor = finalResultMap.get(e);
            String[] srResult = sensor.substring(0, sensor.length() - 1).split(",");
            //
            String sortString =  Arrays.toString(org.springframework.util.StringUtils.
                    sortStringArray(srResult));
            String sortsubString = StringUtils.substring(sortString, 1, -1).replaceAll(" ", "");
            //按照字母规则排序
            finalResultMap.put(e, sortsubString);
        });

        return finalResultMap;
    }






}
