package com.kq.perimission.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.kq.common.exception.BaseException;
import com.kq.perimission.domain.OauthClientDetails;
import com.kq.perimission.redis.RedisService;
import com.kq.perimission.service.IOauthClientDetailsService;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yerui
 * @since 2018-07-31
 */
@Api(value = "oauth客户端管理",description = "客户端管理模块")
@Controller
@RequestMapping("/oauthClientDetails")
public class OauthClientDetailsController extends BaseController<OauthClientDetails,IOauthClientDetailsService>{

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    private static final String CACHE_CLIENT_KEY = "oauth_client_details";

    @Autowired
    RedisService redisService;

    /**
     *
     * 带分页的查询条件
     * @return
     */

    @ApiOperation(value = "菜单多条件查询",
            notes = "传入search- 形式的参数进行查询,带分页,比如 用户是属性是 id,name,updTime" +
                    "表单的'name=search-EQ-name' EQ 标识等于name的值 " +
                    "表单的 name=search-LIKE-name LIKE 标识 表示模糊查询name的值 " +
                    "类似属性 还有  EQ, LIKE, GT(大于), " +
                    "LT(小于), GE(大于等于), LE(小于等于),NEQ(不等于)," +
                    "当LT GT 为时间格式时 ，时间格式为 YYYY-MM-DD"+
                    "GTEDT(大于等于时间),LTEDT(小于等于时间) 时间格式为YYYY-MM-DD HH24:MM:DD" +
                    "当属性是驼峰结构 比如 updTime ,search 结果 需要传递以下形式  " +
                    "name = \"search-EQ-upd_time\"  这种形式后台能够动态识别出来该属性"
    )
    @RequestMapping(value = "/query.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  queryConditionPage(HttpServletRequest request){
        //把查询条件都写好了
        Page<OauthClientDetails> userListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            userListPage = queryContion(searchParams);
            return super.jsonObjectResult(userListPage, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

    @ApiOperation(value = "增加/修改客户端信息",
            notes = "保存和修改客户端信息")
    @RequestMapping(value = "/save.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody OauthClientDetails oauthClientDetails) {
        boolean result = false;
        try{

            result = this.defaultDAO.insertOrUpdate(oauthClientDetails);
            //更新redis的信息
            redisService.getRedisTemplate().
                    boundHashOps(CACHE_CLIENT_KEY).put(oauthClientDetails.getClientId(),JSONObject.toJSONString(oauthClientDetails));
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存失败",500);
        }
        return super.jsonObjectResult(result, "保存成功");

    }

    @ApiOperation(value = "根据clientid删除客户端服务信息")
    @RequestMapping(value = "/removeById.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteBaseUserServiceEntityById(@ApiParam(value = "客户端唯一ID") @RequestParam(name = "entityID") String entityID) {

        boolean result = this.defaultDAO.deleteById(entityID);
        if(result==true){
            //同时清除redis缓存
            redisService.getRedisTemplate().
                    boundHashOps(CACHE_CLIENT_KEY).delete(entityID);
        }
        return super.jsonObjectResult(result, "删除成功");
    }

    @ApiOperation(value = "根据客户端celintID获取客户端基本信息")
    @RequestMapping(value = "/queryById.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "clientId") @RequestParam(name = "clientId") String clientId){
        try{
            String value = (String) redisService.getRedisTemplate().boundHashOps(CACHE_CLIENT_KEY).get(clientId);
            OauthClientDetails user = null;
            if (StringUtils.isBlank(value)) {
                user = this.defaultDAO.selectById(clientId);
                redisService.getRedisTemplate().boundHashOps(CACHE_CLIENT_KEY).put(clientId,JSONObject.toJSONString(user));
            } else {
                user = JSONObject.parseObject(value, OauthClientDetails.class);
            }

            return  super.jsonObjectResult(user, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }

    @ApiOperation(value = "根据多个ID，批量删除")
    @RequestMapping(value = "/removeMore.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "用户服务多个id") @RequestParam(name = "entityIDS") String entityIDS){
        boolean result = false;
        try{
            String[] str = Tools.str2StrArray(entityIDS);
            List<String> strArr1 = Arrays.asList(str);
            result = this.defaultDAO.deleteBatchIds(strArr1);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("删除失败",500);
        }
        return super.jsonObjectResult(result, "删除成功");

    }

    /**
     * 获取无分页的list
     * @return
     */
    @ApiOperation(value = "获取无分页的用户组集合", notes = "传入search- 形式的参数进行查询,带分页,比如 用户是属性是 id,name,updTime" +
            "表单的'name=search-EQ-name' EQ 是等于的意思 " +
            "表单的 name=search-LIKE-name LIKE 标识 标识模糊查询 " +
            "类似属性 还有  EQ, LIKE, GT(大于), " +
            "LT(小于), GE(大于等于), LE(小于等于),NEQ(不等于)," +
            "当LT GT 为时间格式时 ，时间格式为 YYYY-MM-DD"+
            "   GTEDT(大于等于时间),LTEDT(小于等于时间) 时间格式为YYYY-MM-DD HH24:MM:DD" +
            "   当属性是驼峰结果 比如 updTime ,search 结果 需要传递以下形式  " +
            " name = \"search-EQ-upd_time\"  这种形式后台能够动态识别出来该属性")
    @RequestMapping(value = "/list.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getBaseUserServiceEntityList(HttpServletRequest request){
        try{
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<OauthClientDetails> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

    @ApiOperation(value = "根据用户名称获取客户端信息")
    @RequestMapping(value = "/getInfoByName.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getInfoByUserName(@RequestParam("userName") String userName){
        try{
            //把查询条件都写好了
            OauthClientDetails result = this.defaultDAO.getInfoByUserName(userName);
            return super.jsonObjectResult(result, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

}

