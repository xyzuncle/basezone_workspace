package com.kq.perimission.controller;


import com.ace.cache.annotation.Cache;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.exception.BaseException;
import com.kq.common.util.TreeUtil;
import com.kq.perimission.constant.AdminCommonConstant;
import com.kq.perimission.domain.BaseMenu;
import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.dto.MenuTree;
import com.kq.perimission.service.IBaseMenuService;
import com.kq.perimission.service.impl.BaseUserServiceImpl;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;
import com.sun.xml.internal.rngom.parse.host.Base;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@Api(value = "BaseMenuController",description = "菜单模块")
@Controller
@RequestMapping("/baseMenu")
public class BaseMenuController extends BaseController<BaseMenu,IBaseMenuService>{

    @Autowired
    private BaseUserServiceImpl baseUserService;


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
    public Object queryConditionPage(HttpServletRequest request){
        //把查询条件都写好了
        Page<BaseMenu> userListPage = null;
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


    @ApiOperation(value = "增加/修改菜单信息",
            notes = "保存和修改菜单信息")
    @RequestMapping(value = "/save.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody BaseMenu BaseMenu) {
        boolean result = false;
        try{
            result = this.defaultDAO.insertOrUpdate(BaseMenu);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存失败",500);
        }
        return super.jsonObjectResult(result, "保存成功");

    }

    @ApiOperation(value = "根据Id删除菜单信息")
    @RequestMapping(value = "/removeById.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteBaseMenuById(@ApiParam(value = "菜单唯一标识") @RequestParam(name = "entityID") String entityID) {
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


    @ApiOperation(value = "根据ID获取用户的基本信息")
    @RequestMapping(value = "/queryById.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "菜单唯一标识id") @RequestParam(name = "id") String id){
        try{
            BaseMenu user = this.defaultDAO.selectById(id);
            return  super.jsonObjectResult(user, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }

    @ApiOperation(value = "根据多个ID，批量删除")
    @RequestMapping(value = "/removeMore.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "菜单多个id") @RequestParam(name = "entityIDS") String entityIDS){
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
    @ApiOperation(value = "获取无分页的菜单集合", notes = "传入search- 形式的参数进行查询,带分页,比如 用户是属性是 id,name,updTime" +
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
    public Object getBaseMenuList(HttpServletRequest request){
        try{
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<BaseMenu> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

    /**
     * 父类菜单的JSON串
     * @return
     */
    @ApiOperation(value = "根据父类菜单ID获取所属父类相关信息")
    @RequestMapping(value = "/paretList.do" ,method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getParentListByParentId(@RequestParam(name = "parentId") String parentId){
        try{
            List<BaseMenu> baseMenusList =    this.defaultDAO.getListByPaerntId(parentId);
            return super.jsonObjectResult(baseMenusList, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }



    /**
     * 获取所有菜单
     * @return
     */
    @ApiOperation(value = "获取所有菜单")
    @RequestMapping(value = "/allMenu", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  getBaseMenuAll() {
        try{
            List<BaseMenu> baseMenusList =  this.defaultDAO.selectListAll();
            return super.jsonObjectResult(baseMenusList, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }

    @ApiOperation(value = "根据用户名获取用户顶级树菜单",notes = "根据用户名获取用户顶级树菜单，菜单类型 menu:针对后台带单 fontmenu:针对前台菜单")
    @RequestMapping(value = "/parent/menutop/{username}/{menuType}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getTopMenu(@ApiParam("用户名")@PathVariable("username") String username,@ApiParam("菜单类型")@PathVariable("menuType") String menuType) {
        try{
            BaseUser user = baseUserService.getUserByUserName(username);
            List<String> idList = null;
            if(user!=null && !user.equals("")){
                List<BaseMenu> baseMenusList =  this.defaultDAO.selectParentMenuByMenuType(user.getId(),-1+"",menuType);
                idList = baseMenusList.stream().map(BaseMenu::getId).collect(Collectors.toList());
            }

            return super.jsonObjectResult(idList, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }


    /**
     * 根据顶级菜单
     * @param
     * @return
     */
    @ApiOperation(value = "根据菜单类型获取全部顶级菜单",notes = "可选参数 menuType，值为两种 back,font,如果不填，" +
            "查询全部菜单，不分类别，如果传back 返回后台菜单，如果传font 返回前台菜单")
    @RequestMapping(value = "/parentMenu", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  getAllMenuByParent(@ApiParam("菜单类型") @RequestParam(value = "menuType",required = false) String menuType) {
        List<BaseMenu> baseMenusList = null;
        try{
            if(menuType==null || menuType.equals("")){
                baseMenusList =  this.defaultDAO.selectAllMenu();
            }else{
                baseMenusList =  this.defaultDAO.selectAllMenuByType(menuType);
            }
            return super.jsonObjectResult(baseMenusList, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }




    /**
     * 根据登录的用户获取前端菜单
     * @param userName
     * @param type
     * @return
     */
    @ApiOperation(value = "根据登录用户获取前端菜单",notes = "type 1是 新闻浏览 数据定制 统计分析  " +
             "2是 用户列表 、用户订单,3 数据订购，4 全分辨率采集 -1 是产品查询 、长期采集单 " +
            "如果type不传值，则默认全部" )
    @RequestMapping(value = "/fontMenuByUser", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getFontMenuByUser(@RequestParam("userName") String userName,@RequestParam(value = "type",required = false) String type){
        try{
            List<String> result =  this.defaultDAO.getFoneMenuByUser(userName, type);

            return super.jsonObjectResult(result, "查询成功");
        }catch (BaseException b){
            b.printStackTrace();
            return super.jsonObjectResult("false", "用户与角色未绑定");
        }
        catch (Exception e){
                 e.printStackTrace();
                throw new BaseException("查询失败",500);
        }


    }


    @ApiOperation(value = "根据游客登录获取对应前端菜单",notes = "type 1是 新闻浏览 数据定制 统计分析 高级检索  " +
            "2是 用户列表 、用户订单,3 数据订购，4 全分辨率采集  -1 是产品查询 、长期采集单 " +
            "如果type不传值，则默认全部" )
    @RequestMapping(value = "/fontMenuByGuest", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getFontMenuByGuest(@RequestParam(value = "type",required = false) String type){
        try{
            List<String> result =  this.defaultDAO.getFontMenuByGuest(type);
            return super.jsonObjectResult(result, "查询成功");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }


    }




}

