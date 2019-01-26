package com.kq.perimission.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.exception.BaseException;
import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.BaseGroupMember;
import com.kq.perimission.service.IBaseGroupMemberService;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * <p>
 *  用于记录用户组和成员之间的关系
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */

@Api(value = "BaseGroupMember",description = "用户成员组模块")
@Controller
@RequestMapping("/baseGroupMember")
public class BaseGroupMemberController extends BaseController<BaseGroupMember,IBaseGroupMemberService>{


    private Logger logger = LoggerFactory.getLogger(BaseGroupMemberController.class);

    /**
     *
     * 带分页的查询条件
     * @return
     */
    @ApiOperation(value = "用户成员组多条件查询", notes = "传入search- 形式的参数进行查询")
    @RequestMapping(value = "/query.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryConditionPage(HttpServletRequest request){
        Page<BaseGroupMember> userListPage = null;

        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            Sort sort = new Sort(Sort.DESC, "upd_time");
            userListPage = queryContion(searchParams,sort);
            return  super.jsonObjectResult(userListPage, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }


    @ApiOperation(value = "增加/修改用户组信息",
            notes = "保存和修改用户成员组信息")
    @RequestMapping(value = "/save.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  saveOrUpdate(@RequestBody BaseGroupMember BaseGroupMember) {
        boolean result = false;
        try{
             result = this.defaultDAO.insertOrUpdate(BaseGroupMember);
        }catch (Exception e){
                e.printStackTrace();
                throw new BaseException("保存失败",500);
        }

        //调用自定义的方法
        //System.err.println("deleteAll：" + BaseGroupMemberService.deleteAll());
        //System.err.println("插入一条数据：" + BaseGroupMemberService.insert(new BaseGroupMember(1L, "张三", AgeEnum.TWO, 1)));


        return super.jsonObjectResult(result, "保存成功");

    }

    @ApiOperation(value = "根据Id删除组信息")
    @RequestMapping(value = "/removeById.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteBaseGroupMemberById(@ApiParam(value = "用户组ID") @RequestParam(name = "entityID") String entityID) {
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

    @ApiOperation(value = "根据ID获取用户组的基本信息")
    @RequestMapping(value = "/queryById.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "用户组唯一标识id") @RequestParam(name = "id") String id){
        try{
            BaseGroupMember user = this.defaultDAO.selectById(id);
            return  super.jsonObjectResult(user, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }



    }

    @ApiOperation(value = "根据多个ID，批量删除")
    @RequestMapping(value = "/removeMore.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "用户成员组多个ids") @RequestParam(name = "entityIDS") String entityIDS){
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
    public Object getBaseGroupMemberList(HttpServletRequest request, PrintWriter printWriter){
        try{
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<BaseGroupMember> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }



    }

    /**
     * 保存用户组合用户之间的关系
     * 更新用户组合用户之间的关系
     * @param userIds
     */
    @ApiOperation(value = "保存用户组合用户之间的关系")
    @RequestMapping(value = "/saveBatchUser.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveGroupUserRelation(@ApiParam(value ="多个用户Id") @RequestParam(name = "userIds") String userIds,
                                      @ApiParam(value = "组Id") @RequestParam(name = "groupId") String groupId){
        boolean saveresult = false;
        try{
                if(StringUtils.isNotBlank(groupId)){
                    //全部清除用户和这个组的关系
                    this.defaultDAO.deleteByColumn(groupId);
                    if(StringUtils.isNotBlank(userIds)){
                        String[] str = Tools.str2StrArray(userIds);
                        //批量组装
                        List<BaseGroupMember> memberList = new ArrayList<BaseGroupMember>();
                        for(int i =0;i<str.length;i++){
                            BaseGroupMember tempMember = new BaseGroupMember();
                            tempMember.setGroupId(groupId);
                            tempMember.setUserId(str[i]);
                            memberList.add(tempMember);
                        }
                        saveresult = this.defaultDAO.insertBatch(memberList);

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new BaseException("保存失败",500);
            }

        return super.jsonObjectResult(saveresult, "保存成功");

    }

    @ApiOperation(value = "根据用户组ID，获取该组下的所有用户")
    @RequestMapping(value = "/queryUserIds.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getUserIdsByGroup(@ApiParam(value = "用户组ID") @RequestParam(name = "groupId") String groupId){
        try{
            List<String> userList =  this.defaultDAO.getUserIdsByGroupId(groupId);
            return super.jsonObjectResult(userList, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }

    }



}

