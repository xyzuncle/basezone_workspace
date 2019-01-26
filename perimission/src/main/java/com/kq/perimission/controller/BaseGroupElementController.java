package com.kq.perimission.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.exception.BaseException;
import com.kq.perimission.domain.BaseGroupElement;
import com.kq.perimission.service.IBaseGroupElementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.kq.perimission.controller.BaseController;

import org.springframework.stereotype.Controller;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *
 * </p>
 *
 * @author yerui
 * @since 2018-09-20
 */

@Api(value = "BaseGroupElementController}",description = "角色元素匹配，用于API权限")
@Controller
@RequestMapping("/BaseGroupElement")
public class BaseGroupElementController extends BaseController<BaseGroupElement,IBaseGroupElementService>{

private final Logger logger=LoggerFactory.getLogger(BaseGroupElementController.class);

/**
*
* 带分页的查询条件
* @return
*/
@ApiOperation(value = "BaseGroupElement多条件查询", notes = "多条件查询")
@RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object queryConditionPage(HttpServletRequest request){
        Page<BaseGroupElement> BaseGroupElementListPage=null;
        try{
        //把查询条件都写好了
        Map<String, Object> searchParams=Servlets.getParametersStartingWith(request,"search-");
        Sort sort=new Sort(Sort.DESC,"crt_time");
    BaseGroupElementListPage=queryContion(searchParams,sort);
        return super.jsonObjectResult(BaseGroupElementListPage,"查询成功");
        }catch(Exception e){
        e.printStackTrace();
        throw new BaseException("查询失败",500);
        }
        }

@ApiOperation(value = "增加/修改BaseGroupElement信息",
        notes = "保存和修改BaseGroupElement信息")
@RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object saveOrUpdate(@RequestBody BaseGroupElement BaseGroupElement){
        boolean result=false;
        try{
        result=this.defaultDAO.insertOrUpdate(BaseGroupElement);
        }catch(Exception e){
        e.printStackTrace();
        throw new BaseException("保存失败",500);
        }
        return super.jsonObjectResult(result,"保存成功");

        }

@ApiOperation(value = "根据Id删除BaseGroupElement信息")
@RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object deleteBaseGroupElementById(@ApiParam(value = "BaseGroupElementID") @RequestParam(name = "entityID")  String entityID){
        boolean result=this.defaultDAO.deleteById(entityID);
        return super.jsonObjectResult(result,"删除成功");
        }


@ApiOperation(value = "根据ID获取BaseGroupElement的基本信息")
@RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object queryById(@ApiParam(value = "BaseGroupElement唯一标识") @RequestParam(name = "id") String id){
        try{
    BaseGroupElement BaseGroupElement =this.defaultDAO.selectById(id);
        return super.jsonObjectResult(BaseGroupElement,"查询成功");
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

@ApiOperation(value = "获取没有分页的多条件查询集合")
@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object getBaseGroupElementList(HttpServletRequest request){
        try{
        //把查询条件都写好了
            Map<String, Object> searchParams=Servlets.getParametersStartingWith(request,"search-");
            List<BaseGroupElement> list=super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list,"查询成功");
        }catch(Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

        }

    /**
     * 根据用户ID获取该用户的API权限
     * @param userId
     * @return
     */
 public PerimissionInfo getUserApiInfoByUser(@RequestParam("userId") String userId){

     return null;

 }



}

