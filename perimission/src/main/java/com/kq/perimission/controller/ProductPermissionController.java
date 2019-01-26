package com.kq.perimission.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.kq.common.exception.BaseException;
import com.kq.perimission.domain.ProductPermission;
import com.kq.perimission.service.IProductPermissionService;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
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
 * @since 2018-05-12
 */
@Api(value = "product",description = "生产单模块")
@Controller
@RequestMapping("/productPermission")
public class ProductPermissionController extends BaseController<ProductPermission,IProductPermissionService>{

    /**
     *
     * 带分页的查询条件
     * @return
     */

    @ApiOperation(value = "用户多条件查询",
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
    @RequestMapping(value = "/query.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  queryConditionPage(HttpServletRequest request){
        Page<ProductPermission> userListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            Sort sort = new Sort(Sort.DESC, "upd_time");
            userListPage = queryContion(searchParams,sort);
            return super.jsonObjectResult(userListPage, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

    @ApiOperation(value = "增加/修改生产单", notes = "增加/修改生产单," +
            "其中产品分辨率请存：1：对应小于1m 2 对应1m-3m 3 3m 4 3m-4m 5 8m-16m 6 大于10m." +
            "")
    @RequestMapping(value = "/save.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  saveOrUpdate(@RequestBody ProductPermission productPermission) {
        boolean result = false;
        try{
            result = this.defaultDAO.insertOrUpdate(productPermission);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存失败",500);
        }
        return super.jsonObjectResult(result, "保存成功");

    }

    @ApiOperation(value = "根据Id删除生产单信息")
    @RequestMapping(value = "/removeById.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteBaseUserById(@ApiParam(value = "用户唯一标识") @RequestParam(name = "entityID") String entityID) {
        boolean result = false;
        try{
            result = this.defaultDAO.deleteById(entityID);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new BaseException("删除失败",500);
        }

        return super.jsonObjectResult(result, "删除成功");
    }

    @ApiOperation(value = "根据ID获取生产单的基本信息")
    @RequestMapping(value = "/queryById.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@RequestParam(name = "id") String id){
        try{
            ProductPermission user = this.defaultDAO.selectById(id);
            return  super.jsonObjectResult(user, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }

    @ApiOperation(value = "根据多个ID，批量删除生产单")
    @RequestMapping(value = "/removeMore.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "生产单多个id") @RequestParam(name = "entityIDS") String entityIDS){
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
    @ApiOperation(value = "获取无分页的生产单集合", notes = "传入search- 形式的参数进行查询,带分页,比如 用户是属性是 id,name,updTime" +
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
    public Object getBaseUserList(HttpServletRequest request){
        try{
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<ProductPermission> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }


}

