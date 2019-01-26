package com.kq.perimission.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Strings;
import com.kq.common.exception.BaseException;
import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.service.IBaseGroupService;
import com.kq.perimission.service.impl.BaseUserServiceImpl;
import com.kq.perimission.service.impl.TicketsServiceImpl;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
/*import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  记录组的相关信息
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@Api(value = "baseGroup",description = "用户组模块")
@Controller
@RequestMapping("/baseGroup")
/*@EnableOAuth2Client*/
public class BaseGroupController extends BaseController<BaseGroup,IBaseGroupService>{


    @Value("${spring.outside.default.roleId}")
    private String outSideRoreId;

    @Value("${spring.outside.default.orgId}")
    private String outSideOrgId;

    @Autowired
    TicketsServiceImpl ticketsService;

    @Autowired
    private BaseUserServiceImpl baseUserService;

     /**
     *
     * 带分页的查询条件
     * @return
     */
    @ApiOperation(value = "用户组多条件查询", notes = "传入search- 形式的参数进行查询,带分页")
    @RequestMapping(value = "/query.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryConditionPage(HttpServletRequest request){
        Page<BaseGroup> userListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            Sort sort = new Sort(Sort.DESC, "crt_time");
            userListPage = queryContion(searchParams,sort);
            return super.jsonObjectResult(userListPage, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

    @ApiOperation(value = "增加/修改用户信息",
            notes = "保存和修改用户组信息")
    @RequestMapping(value = "/save.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  saveOrUpdate(@RequestBody BaseGroup BaseGroup) {

        boolean result = false;
        try{
            if(Strings.isNullOrEmpty(BaseGroup.getId())){
                String squenceId = ticketsService.getAppLastId("role",BaseGroup);
                BaseGroup.setAttr1(squenceId);
            }
            result = this.defaultDAO.insertOrUpdate(BaseGroup);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存失败",500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }

    @ApiOperation(value = "根据Id删除组信息,同时组下所属的逻辑关系一并删除")
    @RequestMapping(value = "/removeById.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteBaseGroupById(@ApiParam(value = "组ID") @RequestParam("entityID") String entityID) {
        boolean result = false;
        try{

            if(StringUtils.isNotBlank(entityID)){
                EntityWrapper<BaseUser> ew = new EntityWrapper<BaseUser>();
                ew.eq("group_id", entityID);
                List<BaseUser> baseUserList =  baseUserService.selectList(ew);
                if(baseUserList!=null && baseUserList.size()>0){
                    throw new BaseException("角色已经绑定用户，无法删除角色",501);
                }

                if(entityID.equals("guest") || entityID.equals(outSideRoreId)
                        || entityID.equals("outSideOrgId")){
                    throw new BaseException("系统默认角色无法删除",502);
                }
            }

            result = this.defaultDAO.customDeleterGroup(entityID);
        }
        catch (BaseException e){
            e.printStackTrace();
            if(e.getStatus()==501){
                throw new BaseException("角色已经绑定用户，无法删除角色",500);
            } else if(e.getStatus()==502){
                throw new BaseException("系统默认角色无法删除",500);
            }
        }catch(Exception e){
            throw new BaseException("删除失败",500);
        }

        return super.jsonObjectResult(result, "删除成功");
    }

    @ApiOperation(value = "根据ID获取组的基本信息")
    @RequestMapping(value = "/queryById.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "组唯一标识id") @RequestParam(name = "id")  String id){
        try{
            BaseGroup user = this.defaultDAO.selectById(id);
            return  super.jsonObjectResult(user, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }

    @ApiOperation(value = "根据多个ID，批量删除")
    @RequestMapping(value = "/removeMore.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "组多个ids") @RequestParam(name = "entityIDS") String entityIDS){
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
    @ApiOperation(value = "获取无分页的用户组集合")
    @RequestMapping(value = "/list.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getBaseGroupList(HttpServletRequest request){
        try{
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<BaseGroup> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

    /**
     * 获取无分页的list
     * @return
     */
    @ApiOperation(value = "获取组的下拉列表",notes = "groupType 1 内部角色 2 外部角色  根据不同的类型获取外部角色列表和内部角色列表")
    @RequestMapping(value = "/selectList.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getBaseGroupList(@RequestParam("groupType") String groupType){
        try{
            //把查询条件都写好了
            EntityWrapper<BaseGroup> ew = new EntityWrapper<BaseGroup>();
            ew.setSqlSelect("id", "name").eq("group_type", groupType);
            List<BaseGroup> list = this.defaultDAO.selectList(ew);
            return super.jsonObjectResult(list, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }

    @ApiOperation(value = "判断组姓名是否唯一",notes = "判断当前组姓名是否唯一")
    @RequestMapping(value = "getExistGroupName.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getExistGroupName(@RequestParam("name") String name) {
        try{
            boolean result = this.defaultDAO.getExistGroupName(name);
            return super.jsonObjectResult(result, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }
}

