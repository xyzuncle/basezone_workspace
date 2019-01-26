package com.kq.perimission.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.constants.CommonConstants;
import com.kq.common.exception.BaseException;
import com.kq.common.ip.IPinfo;
import com.kq.common.util.IPUtil;
import com.kq.perimission.controller.rpc.feign.OnlineUserService;
import com.kq.perimission.domain.BaseOnlineUser;
import com.kq.perimission.dto.StatisticalDTO;

import com.kq.perimission.service.IBaseOnlineUserService;
import com.kq.perimission.service.impl.BaseOnlineUserServiceImpl;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 *
 * </p>
 *
 * @author yerui
 * @since 2019-01-12
 */

@Api(value = "BaseOnlineUserController",description = "在线用户列表模块")
@Controller
@RequestMapping("/BaseOnlineUser")
public class BaseOnlineUserController extends BaseController<BaseOnlineUser,IBaseOnlineUserService>{

private final Logger logger=LoggerFactory.getLogger(BaseOnlineUserController.class);

@Autowired
BaseOnlineUserServiceImpl baseOnlineUserService;

@Autowired
StringRedisTemplate stringRedisTemplate;
/**
*
* 带分页的查询条件
* @return
*/
@ApiOperation(value = "BaseOnlineUser多条件查询", notes = "多条件查询")
@RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object queryConditionPage(HttpServletRequest request){
        Page<BaseOnlineUser> BaseOnlineUserListPage=null;
        try{
            //把查询条件都写好了
            Map<String, Object> searchParams=Servlets.getParametersStartingWith(request,"search-");
            Sort sort=new Sort(Sort.DESC,"crt_time");
            BaseOnlineUserListPage=queryContion(searchParams,sort);

            //同时排序所有失效的数据
            List<BaseOnlineUser> invalidUserLiset =  BaseOnlineUserListPage.getRecords().stream().filter(item ->{
                String sessionId = item.getSessionId();
                if(stringRedisTemplate.hasKey(sessionId)==false){
                    return true;
                }else{
                    return false;
                }
            }).collect(Collectors.toList());

            //批量删除无效的数据
            List<String> ids = invalidUserLiset.stream().map(e -> e.getId()).collect(Collectors.toList());
            baseOnlineUserService.deleteBatchIds(ids);


            //重新查询一次，保证分页数据正常。
            BaseOnlineUserListPage=queryContion(searchParams,sort);

            StatisticalDTO statisticalDTO = new StatisticalDTO(BaseOnlineUserListPage.getRecords(),
                    BaseOnlineUserListPage.getCurrent(),BaseOnlineUserListPage.getTotal(),BaseOnlineUserListPage.getSize());
            statisticalDTO.setMessage("查询成功");
            statisticalDTO.setStatus("1");

            return JSON.toJSON(statisticalDTO);
            }catch(Exception e){
            e.printStackTrace();
            StatisticalDTO statisticalDTO = new StatisticalDTO();
            statisticalDTO.setMessage("查询失败");
            statisticalDTO.setStatus("0");
            return JSON.toJSON(statisticalDTO);
            }
        }

@ApiOperation(value = "增加/修改BaseOnlineUser信息",
        notes = "保存和修改BaseOnlineUser信息")
@RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object saveOrUpdate(@RequestBody BaseOnlineUser BaseOnlineUser){
        boolean result=false;
        try{
        result=this.defaultDAO.insertOrUpdate(BaseOnlineUser);
        }catch(Exception e){
        e.printStackTrace();
        throw new BaseException("保存失败",500);
        }
        return super.jsonObjectResult(result,"保存成功");

        }

@ApiOperation(value = "根据Id删除BaseOnlineUser信息")
@RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object deleteBaseOnlineUserById(@ApiParam(value = "BaseOnlineUserID") @RequestParam(name = "entityID")  String entityID){
        boolean result=this.defaultDAO.deleteById(entityID);
        return super.jsonObjectResult(result,"删除成功");
        }


@ApiOperation(value = "根据ID获取BaseOnlineUser的基本信息")
@RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object queryById(@ApiParam(value = "BaseOnlineUser唯一标识") @RequestParam(name = "id") String id){
        try{
    BaseOnlineUser BaseOnlineUser =this.defaultDAO.selectById(id);
        return super.jsonObjectResult(BaseOnlineUser,"查询成功");
        }catch(Exception e){
        e.printStackTrace();
        throw new BaseException("查询失败",500);
        }
        }

@ApiOperation(value = "根据多个ID，批量删除")
@RequestMapping(value = "/removeMore.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object deleteByIds(@ApiParam(value = "多个元素ID") @RequestParam(name = "entityIDS") String entityIDS){
        boolean result=false;
        try{
        String[]str=Tools.str2StrArray(entityIDS);
        List<String> strArr1=Arrays.asList(str);
        result=this.defaultDAO.deleteBatchIds(strArr1);
        }catch(Exception e){
        e.printStackTrace();
        throw new BaseException("删除失败",500);
        }
        return super.jsonObjectResult(result,"删除成功");

        }


@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object getBaseOnlineUserList(HttpServletRequest request){
        try{
        //把查询条件都写好了
            Map<String, Object> searchParams=Servlets.getParametersStartingWith(request,"search-");
            List<BaseOnlineUser> list=super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list,"查询成功");
        }catch(Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

}


}

