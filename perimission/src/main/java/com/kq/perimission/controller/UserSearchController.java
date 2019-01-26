package com.kq.perimission.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Strings;
import com.kq.common.domain.User;
import com.kq.common.exception.BaseException;
import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.UserSearch;
import com.kq.perimission.service.IBaseGroupService;
import com.kq.perimission.service.IUserSearchService;
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
 * @since 2018-07-23
 */
@Api(value = "USER-SEARCH",description = "用户搜索条件")
@Controller
@RequestMapping("/userSearch")
public class UserSearchController  extends BaseController<UserSearch,IUserSearchService>{


    /**
     *
     * 带分页的查询条件
     * @return
     */
    @ApiOperation(value = "用户关键词多条件查询", notes = "传入search- 形式的参数进行查询,带分页")
    @RequestMapping(value = "/query.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryConditionPage(HttpServletRequest request){
        Page<UserSearch> userSearchPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            String[] serarch = {"used,crt_time"};
            Sort sort = new Sort(Sort.DESC, serarch);
            userSearchPage = queryContion(searchParams, sort);
            return super.jsonObjectResult(userSearchPage, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

    @ApiOperation(value = "增加/修改用户搜索条件信息",
            notes = "保存和修改用户搜索组信息")
    @RequestMapping(value = "/save.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  saveOrUpdate(@RequestBody UserSearch userSearch) {

        boolean result = false;
        try{
            result = this.defaultDAO.insertOrUpdate(userSearch);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存失败",500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }


    @ApiOperation(value = "根据Id删除用户搜索记录")
    @RequestMapping(value = "/removeById.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteBaseGroupById(@ApiParam(value = "ID") @RequestParam("entityID") String entityID) {
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

    @ApiOperation(value = "根据ID获取用户搜索基本信息")
    @RequestMapping(value = "/queryById.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "唯一标识id") @RequestParam(name = "id")  String id){
        try{
            UserSearch user = this.defaultDAO.selectById(id);
            return  super.jsonObjectResult(user, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }


    @ApiOperation(value = "根据多个ID，批量删除")
    @RequestMapping(value = "/removeMore.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "用户搜索条件多个ids") @RequestParam(name = "entityIDS") String entityIDS){
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
    @ApiOperation(value = "获取无分页的用户搜索组集合")
    @RequestMapping(value = "/list.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getBaseGroupList(HttpServletRequest request){
        try{
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            Sort sort = new Sort(Sort.DESC, "used");
            List<UserSearch> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }


    /**
     * 根据用户id，修改历史搜索条件的点击数
     * @return
     */
    @ApiOperation(value = "根据用户id，修改历史搜索条件的点击数")
    @RequestMapping(value = "/updateUsed.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getBaseGroupList(@RequestParam("id") String id,@RequestParam("num") int num){
        try{
            //把查询条件都写好了

            this.defaultDAO.updateUserdByUserId(id);
            return super.jsonObjectResult("true", "修改成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("修改失败",500);
        }

    }

}

