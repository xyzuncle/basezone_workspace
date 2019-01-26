package com.kq.api.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kq.api.jwt.JWTHelp;

import com.kq.api.rpc.IUserService;
import com.kq.common.DTO.BaseResponse;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.constants.CommonConstants;
import com.kq.common.context.BaseContextHandler;
import com.kq.common.exception.ClientInvalidException;
import com.kq.common.util.jwt.IJWTInfo;


import com.kq.common.util.jwt.JWTInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 *
 */
@Component
public class LoginFilter extends ZuulFilter {

    private final static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private static final String PREFIX_EXPIRES_KEYS = "spring:session:sessions:expires:";

    @Value("${jwt.pub-key}")
    String pubKey;

    @Value("${font.perimission.url}")
    String fontUrl;

    @Value("${jwt.ignore-url}")
    private String jwtIgnoreUrl;

    @Value("${zuul.prefix}")
    private String zuulPrefix;

    @Value("${jwt.token-header}")
    private String accessToken;

    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    @Value("${client.token-header}")
    private String clientTokenHeader;

    @Value("${api.validation}")
    private String apiFlag;

    @Autowired
    private JWTHelp jwtHelp;

    @Autowired
    RedisOperationsSessionRepository sessionRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IUserService iUserService;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否产生过滤请求 true
     * true     激活该过滤器
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * @return 过滤的逻辑需要的地方
     * 过滤器只是返回true或false来保证过滤器是否执行
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        //是为了在权限中指定方法
        final String method = request.getMethod();
        //每次效验都放空，通过认证后在防值
        BaseContextHandler.setToken(null);
        //以后的过滤的路径可能有很多
        String ignoreUrl = request.getRequestURI();
        //初始化上下文的状态
        ctx.setResponseStatusCode(0);
        if (isIgnorePath(ignoreUrl)) {
            this.printLog(request,null,"200");
            //过滤请求，需要对后续的过滤做处理
            ctx.setResponseStatusCode(500);
            return null;
        }
       /* //得到access-token，目的是验证用户合法性
        String token = request.getHeader(accessToken);

        if (StringUtils.isBlank(token)) {
           //如果为空，则从cookie中获取
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(accessToken)) {
                    token = cookie.getValue();
                }
            }
        }*/

        try {
            String token = request.getHeader(accessToken);
            if(StringUtils.isBlank(token)){
                 //不向下传递过滤器
                 ctx.setResponseStatusCode(500);
                 //針對遊客下的records 進行特殊處理
                 if(request.getRequestURI().equals(fontUrl)){
                     guestPerm(request, ctx);
                 }else{
                     response.setContentType("text/html;charset=utf-8");
                     ctx.setResponseBody(JSON.toJSONString(new BaseResponse(40101, "token解析异常，请重新登录")));
                     ctx.setSendZuulResponse(false);
                 }
                 return null;
            }
            //需要对状态做处理，来依据这个状态来控制后续的过滤器是否执行
           // ctx.setResponseStatusCode(0);
            //否则就认为是登录用户，
            // 验证用户合法性
            JWTInfo infoFromToken = (JWTInfo) getJWTUser(request,ctx);
            //记录日志
            this.printLog(request,infoFromToken,"200");

            if(apiFlag.equals("true")){
                //通过用户获取该用户的API权限
                this.checkUserApiPerimission(ctx,infoFromToken.getUsername());
            }
            ctx.set("jwtuser", infoFromToken);
            //存到临时变量，目的是防止颁发新的token导致出错
            ctx.set("currentLogingUserName",infoFromToken.getUsername());
            //通过日志记录当前用户 ip 等相关信息
            ctx.setResponseStatusCode(0);
            //暂时不考虑限流
            //1通过用户权限 得到资源权限 和 和元素权限 和 用户的服务权限
            //1.1 menu的菜单如何对应
            //2通过解析用户名，获取是否有对应的服务权限
      /* boolean serviceResult =  checkUserServicePerimission(request.getRequestURI(), ctx,infoFromToken.getUniqueName());
       if(serviceResult==false){
           return false;
       }else{
           //再去调用权限
           List<PerimissionInfo> permissionIfs =  iUserService.getAllPerimissionInfo();
           // 判断资源是否启用权限约束
           Stream<PerimissionInfo> stream = getPermissionIfs(request.getRequestURI(), method, permissionIfs);
           // 把合法的集合转换成权限DTO
           List<PerimissionInfo> result = stream.collect(Collectors.toList());
           //效验路径是否合法
           PerimissionInfo[] permissions = result.toArray(new PerimissionInfo[]{});
           if (permissions.length > 0) {

               checkUserPermission(permissions, ctx,  infoFromToken);

               Map<String,PerimissionInfo> pimap =   getPerimissionMapByType(permissionIfs);

           }else{
               setFailedRequest(JSON.toJSONString(new BaseResponse(500,"URL Forbidden!")), 200);
           }*/



            //先注释掉client token
        /*BaseResponse resp = clientAuthRpc.getAccessToken(clientId, clientSecret);
        if (resp.getStatus() == 200) {
            ObjectRestResponse<String> clientToken = (ObjectRestResponse<String>) resp;
            //把解析后的token内容放到头里
            //postman里没有看到，不知道IE里能不能看到
            ctx.addZuulRequestHeader(clientTokenHeader, clientToken.getData());
            ctx.getZuulRequestHeaders().get("client-token");
        } else {
            throw new ClientInvalidException("Gate client secret is Error");
        }*/
            /* }*/

        } catch (ClientInvalidException ex) {
            ex.printStackTrace();
            response.setContentType("text/html;charset=utf-8");
            ctx.setResponseBody(JSON.toJSONString(new BaseResponse(40101, "token解析异常，请重新登录")));
            ctx.setSendZuulResponse(false);
            this.printLog(request,null,"500");
        } catch (Exception e) {
            if(e.getMessage().startsWith("JWT expired")){
                this.printLog(request,null,"500");
                //ctx.setSendZuulResponse(false);
                //设置这个状态是为了后续的拦截器不生效，
                //否则会执行一遍,麻烦
                String userName = request.getHeader("prical");
                Map<String, ? extends Session> usersSessions =sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
                        userName);

                if(usersSessions.size()==0){
                    response.setContentType("text/html;charset=utf-8");
                    ctx.setResponseStatusCode(CommonConstants.TOKEN_EXPIRED);
                    //当token过期后，要重新生成token,原先是直接让重新登录。
                    ctx.setResponseBody("Token过期，请重新登录");
                    ctx.setSendZuulResponse(false);
                    return null;
                }
                usersSessions.keySet().stream().forEach(sessionId->{
                    //证明当前用户的session 未过期
                    if(stringRedisTemplate.hasKey(PREFIX_EXPIRES_KEYS + sessionId)==true){
                        //Feign去调用认证中心颁发token的方法
                        iUserService.genJwtToken(userName);
                    }
                });


            }else{
                this.printLog(request,null,"500");

                //设置这个状态是为了后续的拦截器不生效，
                //否则会执行一遍，麻烦
                response.setContentType("text/html;charset=utf-8");
                ctx.setResponseStatusCode(500);
                ctx.setResponseBody(JSON.toJSONString(new BaseResponse(CommonConstants.EX_TOKEN_ERROR_CODE, "Token error or Token is Expired！")));
                ctx.setSendZuulResponse(false);
                // e.printStackTrace();
                // return false;
            }

        }

        return null;

    }

    private boolean isIgnorePath(String path) {
      /*  //对查询方法进行特殊处理
        if(path.startsWith(fontUrl)){
            return false;
        }*/
        for (String url : jwtIgnoreUrl.split(",")) {

            if (path.substring(zuulPrefix.length()).startsWith(url)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 返回session中的用户信息
     *
     * @param request
     * @param ctx
     * @return
     */
    private IJWTInfo getJWTUser(HttpServletRequest request, RequestContext ctx) throws Exception {
        //先从当前作用域网关的作用
        String exitToken = ctx.getZuulRequestHeaders().get("Authorization");
        //得到access-token，目的是验证用户合法性
        String token = request.getHeader(accessToken);
        if (StringUtils.isBlank(token)) {
            //如果为空，则从cookie中获取
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(accessToken)) {
                    token = cookie.getValue();
                }
            }
        }

        //又放到了Zull中
        ctx.addZuulRequestHeader("Authorization", token);
        //放到了作用域中
        //BaseContextHandler.setToken(token);

        return jwtHelp.getInfoFromToken(token);
    }

    /**
     * 网关抛异常
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }


    /**
     * 判断方法是否以非法链接或者反斜抗开始或者方法必须符合对应的方法才合法，
     * 会把合法的集合留下来
     *
     * @param requestUri
     * @param method
     * @param serviceInfo
     * @return  返回合法的流
     */
    private Stream<PerimissionInfo> getPermissionIfs(final String requestUri, final String method, List<PerimissionInfo> serviceInfo) {
        return serviceInfo.parallelStream().filter(new Predicate<PerimissionInfo>() {
            @Override
            public boolean test(PerimissionInfo perimissionInfo) {
                String url = perimissionInfo.getUri();
                String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                String regEx = "^" + uri + "$";
                //符合正则或者已什么方式打头的 同时请求方式是一致的，那就过
                return (Pattern.compile(regEx).matcher(requestUri).find() || requestUri.startsWith(url))
                        && method.equals(perimissionInfo.getMethod());
            }
        });
    }




    /**
     * 效验用户的合法与安全性，如果是合法的请求就记录日志
     * @param permissions
     * @param ctx
     * @param user
     */
    private void checkUserPermission(PerimissionInfo[] permissions, RequestContext ctx, IJWTInfo user) {
        //根据解析的用户名获取相应的权限
        List<PerimissionInfo> perimissionInfos = iUserService.getPermissionByUsername(user.getUniqueName());
        PerimissionInfo current = null;
        for (PerimissionInfo info : permissions) {
            boolean anyMatch = perimissionInfos.parallelStream().anyMatch(new Predicate<PerimissionInfo>() {
                @Override
                public boolean test(PerimissionInfo permissionInfo) {
                    return permissionInfo.getCode().equals(info.getCode());
                }
            });
            if (anyMatch) {
                current = info;
                break;
            }
        }
        if (current == null) {
            setFailedRequest(JSON.toJSONString(new BaseResponse(500,"Token Forbidden!")), 200);
        } else {
            //GET 请求不认为是登录行为，
            if (!RequestMethod.GET.toString().equals(current.getMethod())) {
                //setCurrentUserInfoAndLog(ctx, user, current);
                System.out.println("登录成功！！");
                //这里拿到该用户的所有菜单

            }

        }
    }

    /**
     * 效验当前用户API权限
     * @param ctx
     * @param userName
     */
    private void checkUserApiPerimission(RequestContext ctx, String userName) {
        //根据解析的用户名获取相应的权限
        List<PerimissionInfo> perimissionInfos = iUserService.getUserApiByUserId(userName);
        boolean anyMatch = perimissionInfos.parallelStream().anyMatch(new Predicate<PerimissionInfo>() {
            @Override
            public boolean test(PerimissionInfo permissionInfo) {
                String codeUrl = permissionInfo.getCode();
                String url = permissionInfo.getCode()+"/"+permissionInfo.getUri();
                String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                String regEx = "^" + uri + "$";
                String requestUrl = ctx.getRequest().getRequestURI();
                //符合正则或者已什么方式打头的 同时请求方式是一致的，那就过
                return (Pattern.compile(regEx).matcher(requestUrl).find() || requestUrl.startsWith(codeUrl))
                        && ctx.getRequest().getMethod().equalsIgnoreCase(permissionInfo.getMethod()) && uri.equals(requestUrl);

            }
        });
        if (!anyMatch) {
            setFailedRequest(JSON.toJSONString(new BaseResponse(500,"perimission Forbidden!")), 200);

        }else{
            System.out.println("权限征程");
        }

    }

    /**
     * 效验用户服务权限
     * @param url
     * @param ctx
     * @param username
     */
    private boolean checkUserServicePerimission(String url, RequestContext ctx, String username){

        int index =  url.indexOf("/", 2);
        String servicePath = url.substring(0, index);
        //根据类型过滤下权限的内容
        List<PerimissionInfo> perimissionService = iUserService.getServicePermissionByUserName(username);
        Stream<PerimissionInfo> stream = getPermissionService(servicePath, perimissionService);
        //得到符合条件的权限集合
        List<PerimissionInfo> perimissionInfos = stream.collect(Collectors.toList());
        PerimissionInfo current = null;
        for (PerimissionInfo info : perimissionInfos) {
            boolean anyMatch = perimissionInfos.parallelStream().anyMatch(new Predicate<PerimissionInfo>() {
                @Override
                public boolean test(PerimissionInfo permissionInfo) {
                    return permissionInfo.getUri().equals(servicePath);
                }
            });
            if (anyMatch) {
                current = info;
                break;
            }
        }
        if (current == null) {
            setFailedRequest(JSON.toJSONString(new BaseResponse(500,"Service Forbidden!")), 200);
            return false;
        }else {
            System.out.println("合法");
            return true;
        }
    }

    /**
     * 返回合法的服务路径
     * @param requestUri
     * @param serviceInfo
     * @return
     */
    private Stream<PerimissionInfo> getPermissionService(final String requestUri,List<PerimissionInfo> serviceInfo) {
        return serviceInfo.parallelStream().filter(new Predicate<PerimissionInfo>() {
            @Override
            public boolean test(PerimissionInfo perimissionInfo) {
                String url = perimissionInfo.getUri();
                return (requestUri.startsWith("/"))
                        && perimissionInfo.getType().equals("USER_SERVICE");
            }
        });
    }

    /**
     * 根据lambda表达式，把List转换成map
     * @param accounts
     * @return
     */
    public Map<String, PerimissionInfo> getPerimissionMapByType(List<PerimissionInfo> accounts) {
        return accounts.stream().collect(Collectors.toMap(PerimissionInfo::getPerimissionType, account -> account));
    }

    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public  String getIpAddr(HttpServletRequest request){

        String ip = request.getHeader("cip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 抽象记录日志的方法，用户记录日志
     * @param request
     * @param jwtInfo
     * @param statusCode
     */
    public void printLog(HttpServletRequest request,JWTInfo jwtInfo,String statusCode) {
        String ipAddr=this.getIpAddr(request);
        String username = null;
        String userTyp = null;
        if(jwtInfo!=null){
             username = jwtInfo.getUniqueName();
             userTyp = jwtInfo.getUserType();
        }
        if(StringUtils.isBlank(userTyp)){
            //默认0是游客
            userTyp = "0";
        }
        if(StringUtils.isBlank(username)){
            username = "guset"+ipAddr;
        }
        String methodName = request.getMethod();
        //获取协议头
        String stream = request.getProtocol();
        //用户所使用的浏览器
        String userAgent = request.getHeader("user-agent");
        //用户请求的字节数
        int dataLength = request.getContentLength();
        //获取用户的请求路径
        String requestUrl = request.getRequestURI();

        DateTime dt = new DateTime(new Date());

        String time = dt.toString("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

        logger.debug(ipAddr+" - "+username+" ["+time+"] "+"\""+methodName+" "+requestUrl+" "+stream+"\"" +" "+statusCode
                +" "+dataLength
                +" \"-\" "+"\""+userAgent+"\""+" "+userTyp);
    }

    /**
     * 判断游客的权限
     * @param request
     * @return
     */
    public boolean guestPerm(HttpServletRequest request,RequestContext ctx){
        HttpServletResponse response = ctx.getResponse();
        String requestUrl = request.getRequestURI();
        if(requestUrl.equals(fontUrl)) {
            Map<String, String> resultMap = null;
            String level = "";
            String body = null;
            String satelliteType = "";
            if (!ctx.isChunkedRequestBody()) {
                ServletInputStream inp = null;
                try {
                    inp = ctx.getRequest().getInputStream();
                    if (inp != null) {
                        body = IOUtils.toString(inp);
                        //" [\"GX\",{\"area\":\"110000\",\"areaname\":\"北京市\",\"scenetime\":[\"2017-01-01\",\"2017-12-31\"],\"prodlevel\":\"1\",\"imagerslx\":\">0\",\"satelliteSensor\":[\"GF1_PMS\"]}]"
                        Object listArray = JSON.parse(body);
                        if (listArray instanceof JSONArray) {
                            JSONArray bodyArr = (JSONArray) listArray;
                            //satelliteType = (String) bodyArr.get(0).toString().toUpperCase();
                            level = JSON.parseObject(bodyArr.get(1).toString()).get("prodlevel").toString();
                            //先判断是否有查询权限，然后在判断是否是内网外
                            JSONArray sensorList = (JSONArray) JSON.parseObject(bodyArr.get(1).toString()).get("satelliteSensor");
                            resultMap = getRuleMap(sensorList);
                        } else if (listArray instanceof JSONObject) {
                            JSONObject object = (JSONObject) listArray;
                            //JSONObject object = JSON.parseObject("{\"page\":1,\"size\":20,\"geom\":[\"POLYGON((103.47617780062667 38.38817227376516,103.47617780062667 35.12231284151947,108.9192768543695 35.12231284151947,108.9192768543695 38.38817227376516,103.47617780062667 38.38817227376516))\"],\"scenetime\":[\"2019-1-2\",\"2019-1-9\"],\"satelliteSensor\":[\"GF1_WFV\",\"GF1_PMS\"],\"prodlevel\":\"1\",\"imagerslx\":\"0-400\",\"cloudpsd\":60,\"dwxtype\":\"gx\",\"desc\":[\"scenetime\"]}");
                            //satelliteType = object.get("dwxtype").toString().toUpperCase();
                            level = object.get("prodlevel").toString();
                            JSONArray sensorList = (JSONArray) object.get("satelliteSensor");
                            resultMap = getRuleMap(sensorList);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //这里需要进行比较，游客的权限先写死
            //GF1_WFV
            String sensorList = "PMS,WFV";
            String guestType = "GF1";
            if(resultMap!=null && resultMap.size()>0){
                String result = resultMap.get(guestType);
                if (result == null) {
                    response.setContentType("text/html;charset=utf-8");
                    ctx.setResponseBody("没有查询权限!");
                    ctx.setResponseStatusCode(CommonConstants.QUERY_PERIMISSION);
                    ctx.setSendZuulResponse(false);
                    return false;
                } else  {
                    String[] finalResutl = result.split(",");
                    String sortString =  Arrays.toString(org.springframework.util.StringUtils.
                            sortStringArray(finalResutl));
                    String sortsubString = StringUtils.substring(sortString, 1, -1).replaceAll(" ", "");
                    if(!sensorList.contains(sortsubString) &&!sensorList.equalsIgnoreCase(sortsubString)){
                        response.setContentType("text/html;charset=utf-8");
                        ctx.setResponseBody("没有查询权限!");
                        ctx.setResponseStatusCode(CommonConstants.QUERY_PERIMISSION);
                        ctx.setSendZuulResponse(false);
                        return false;
                    }
                }
            }else{
                response.setContentType("text/html;charset=utf-8");
                ctx.setResponseBody("没有查询权限!");
                ctx.setResponseStatusCode(CommonConstants.QUERY_PERIMISSION);
                ctx.setSendZuulResponse(false);
                return false;
            }


        }
        return true;
    }

    private Map<String, String> getRuleMap(JSONArray tempDate) {
        String satelliteId;
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
