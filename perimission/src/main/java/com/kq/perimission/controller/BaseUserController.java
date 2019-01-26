package com.kq.perimission.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Strings;
import com.kq.common.exception.BaseException;
import com.kq.perimission.controller.rpc.feign.LoginUser;
import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.dto.SaveUserDTO;
import com.kq.perimission.dto.SocialUserInfo;
import com.kq.perimission.redis.RedisService;
import com.kq.perimission.service.IBaseUserService;

import com.kq.perimission.util.MD5;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  BaseUser 用户表以及用户的基本信息
 * </p>
 *
 * @author yerui
 * @since 2018-03-21
 */

@Api(value = "baseUser",description = "用户模块")
@Controller
@RequestMapping("/baseUser")
public class BaseUserController extends BaseController<BaseUser,IBaseUserService> {

    @Autowired
    RedisService redisService;

   /* @Autowired
    ProviderSignInUtils providerSignInUtils;*/

    /**
     * 带分页的查询条件
     *
     * @return
     */

    @ApiOperation(value = "用户多条件查询",
            notes = "传入search- 形式的参数进行查询,带分页,比如 用户是属性是 id,name,updTime" +
                    "表单的'name=search-EQ-name' EQ 标识等于name的值 " +
                    "表单的 name=search-LIKE-name LIKE 标识 表示模糊查询name的值 " +
                    "类似属性 还有  EQ, LIKE, GT(大于), " +
                    "LT(小于), GE(大于等于), LE(小于等于),NEQ(不等于)," +
                    "当LT GT 为时间格式时 ，时间格式为 YYYY-MM-DD" +
                    "GTEDT(大于等于时间),LTEDT(小于等于时间) 时间格式为YYYY-MM-DD HH24:MM:DD" +
                    "当属性是驼峰结构 比如 updTime ,search 结果 需要传递以下形式  " +
                    "name = \"search-EQ-upd_time\"  这种形式后台能够动态识别出来该属性"
    )
    @RequestMapping(value = "/query.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryConditionPage(HttpServletRequest request) {
        Page<BaseUser> userListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            Sort sort = new Sort(Sort.DESC, "crt_time");
            userListPage = queryContion(searchParams, sort);
            return super.jsonObjectResult(userListPage, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "增加/修改用户信息",
            notes = "保存和修改用户信息")
    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody BaseUser baseUser) {
        boolean result = false;

       /* BaseUser baseUser = new BaseUser();
        baseUser.setMobilePhone("13466498574");
        baseUser.setEmail("w123123p@163.com");
        baseUser.setUsername("xxxxxx");
        baseUser.setName("张三");*/

        try {
            if(!"".equals(baseUser) || baseUser!=null){

                //增加判断用户的逻辑

                //ID为空证明新增
                if("".equals(baseUser.getId()) || baseUser.getId()==null){

                    boolean result1 = this.defaultDAO.getExistUser(baseUser.getMobilePhone());
                    //true标识已经存在
                    if(result1 == true){
                        return super.jsonObjectResult(result, "用户已经存才,请更换手机号");
                    }else {
                        String password = baseUser.getPassword();
                        if(!"".equals(password) && password!=null){
                            String pd = MD5.encryptPassword(password);
                            baseUser.setPassword(pd);
                        }
                        String userName = baseUser.getUsername();
                        if(userName==null || userName.equals("")){
                            userName = Tools.generateShortUuid();
                            baseUser.setUsername(userName);
                        }
                        //增加默认值1正常，用户状态(0:禁用，1:正常)
                        baseUser.setStatus("1");


                    }

                }else if(!"".equals(baseUser.getId()) || baseUser.getId()!=null){
                    //证明修改
                    String password = baseUser.getPassword();
                    if(!"".equals(password) && password!=null){
                        if(password.length()<50 ){
                            String pd = MD5.encryptPassword(password);
                            baseUser.setPassword(pd);
                            //由于资源卫星老用户的原因,
                            // 这里需要修改老用户的状态来表明被修改过
                            baseUser.setSatelliteType("0");
                        }
                    }
                }
            }
            result = this.defaultDAO.customSaveUser(baseUser,request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("注册失败", 500);
        }

        SaveUserDTO dto = new SaveUserDTO();
        dto.setResult(result);
        dto.setTime(DateTime.now().toString("yyyy-MM-dd"));
        return super.jsonObjectResult(dto, "注册成功");

    }

    @ApiOperation(value = "根据Id删除组信息")
    @RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteBaseUserById(@ApiParam(value = "用户唯一标识") @RequestParam(name = "entityID") String entityID) {
        boolean result = false;
        try {
            if (entityID != null && !entityID.equals("")) {
                if (entityID.equals("1")) {
                    throw new BaseException("超级管理员无法删除", 500);
                }
            }
            result = this.defaultDAO.deleteById(entityID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("删除失败", 500);
        }

        return super.jsonObjectResult(result, "删除成功");
    }

    @ApiOperation(value = "根据ID获取用户的基本信息")
    @RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@RequestParam(name = "id") String id) {
        try {
            BaseUser user = this.defaultDAO.selectById(id);
            return super.jsonObjectResult(user, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "根据多个ID，批量删除")
    @RequestMapping(value = "/removeMore.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "用户多个id") @RequestParam(name = "entityIDS") String entityIDS) {
        boolean result = false;
        try {
            String[] str = Tools.str2StrArray(entityIDS);
            List<String> strArr1 = Arrays.asList(str);
            result = this.defaultDAO.deleteBatchIds(strArr1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("删除失败", 500);
        }

        return super.jsonObjectResult(result, "删除成功");

    }

    /**
     * 获取无分页的list
     *
     * @return
     */
    @ApiOperation(value = "获取无分页的用户组集合", notes = "传入search- 形式的参数进行查询,带分页,比如 用户是属性是 id,name,updTime" +
            "表单的'name=search-EQ-name' EQ 是等于的意思 " +
            "表单的 name=search-LIKE-name LIKE 标识 标识模糊查询 " +
            "类似属性 还有  EQ, LIKE, GT(大于), " +
            "LT(小于), GE(大于等于), LE(小于等于),NEQ(不等于)," +
            "当LT GT 为时间格式时 ，时间格式为 YYYY-MM-DD" +
            "   GTEDT(大于等于时间),LTEDT(小于等于时间) 时间格式为YYYY-MM-DD HH24:MM:DD" +
            "   当属性是驼峰结果 比如 updTime ,search 结果 需要传递以下形式  " +
            " name = \"search-EQ-upd_time\"  这种形式后台能够动态识别出来该属性")
    @RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getBaseUserList(HttpServletRequest request) {
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<BaseUser> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }

    }

    /**
     * 根据电话号码查询用户是否存在
     *
     * @param mobilePhone
     * @return
     */
    @ApiOperation(value = "根据手机号来判断是否用已经被注册", notes = "根据手机号来判断是否用已经被注册,true 被注册，false 不被注册")
    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    @ResponseBody
    public Object getExistUser(@RequestParam(name = "mobilePhone") String mobilePhone) {
        try {
            boolean result = this.defaultDAO.getExistUser(mobilePhone);
            return super.jsonObjectResult(result, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    /**
     * 判断原始密码是否正确
     *
     * @param
     * @return
     */
    @ApiOperation(value = "数据原始密码，判断原始密码是否正确", notes = "根据用户ID，和原始密码，判断密码是否正确")
    @RequestMapping(value = "/compile", method = RequestMethod.GET)
    @ResponseBody
    public Object CompliePass(@RequestParam(name = "userId") String userId,
                               @RequestParam(name = "oldPass") String oldPass) {
        try {
            boolean result = this.defaultDAO.compliePass(userId, oldPass);
            String message = "";
            if(result==false){
                message = "原始密码不正确！";
            }else{
                message = "";
            }
            return super.jsonObjectResult(result, message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("原始密码错误", 500);
        }
    }

    @ApiOperation(value = "全文检索", notes = "根据关键字进行全文检索")
    @RequestMapping(value = "/fulltext", method = RequestMethod.GET)
    @ResponseBody
    public Object getFullTextSearch(@RequestParam("key") String key){
         List<BaseUser> result =  this.defaultDAO.getFullTextUser(key);
         return super.jsonObjectResult(result, "查询成功");
    }


    @ApiOperation(value = "根据邮箱判断是否被注册", notes = "根据邮箱判断是否被注册,true 被注册，false 不被注册")
    @RequestMapping(value = "/existemail", method = RequestMethod.GET)
    @ResponseBody
    public Object getExistEmail(@RequestParam(name = "email") String email) {
        try {
            boolean result = this.defaultDAO.getExistEmail(email);
            return super.jsonObjectResult(result, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "判断邮箱,手机，用户名是否已经被注册", notes = "判断邮箱,手机，用户名是否已经被注册,true 被注册，false 不被注册")
    @RequestMapping(value = "/existeSomeOne", method = RequestMethod.GET)
    @ResponseBody
    public Object getExistSomeOne(@RequestParam(name = "sameone") String sameone,
                                   @RequestParam(name = "type") String type) {
        try {
            boolean result = this.defaultDAO.getExistByType(sameone,type);
            return super.jsonObjectResult(result, "查询成功");
        }catch (BaseException e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage(), 500);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }

    }

    @ApiOperation(value = "发送邮箱", notes = "发送邮箱")
    @RequestMapping(value = "/sendmail", method = RequestMethod.GET)
    @ResponseBody
    public Object sendMail(@ApiParam("mail")@RequestParam(name = "mail") String mail) {
        try {
            String code = this.defaultDAO.sendMail(mail);
            return super.jsonObjectResult("", "发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("发送失败", 500);
        }
    }

    @ApiOperation(value = "根据邮箱重置密码", notes = "根据邮箱重置密码")
    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    @ResponseBody
    public Object sendMail(@ApiParam("邮箱")@RequestParam(name = "mail") String mail,
                           @ApiParam("邮箱验证码")@RequestParam(name = "code") String code,
                           @ApiParam("新密码")@RequestParam(name = "pwd") String pwd
                           ) {
        try {

            String redisCode = redisService.getStrTemplate().opsForValue().get(mail);
            //String redisCode = null;
            if(Strings.isNullOrEmpty(redisCode)){
                throw new BaseException("验证码已经过期", 500);
            }else{
                if(!Strings.isNullOrEmpty(code)){
                    if(code.equals(redisCode)){
                        this.defaultDAO.resetPwd(pwd,mail);
                    }else{
                        Object o =  new BaseException("验证码不正确", 400);
                    }
                }
            }
            return super.jsonObjectResult("", "密码修改成功");
        }
        catch (BaseException e){
            e.printStackTrace();
            if(e.getStatus()==500){
                return super.jsonObjectResult(new BaseException("验证码已经过期", 500), "验证码已经过期");
            }else if(e.getStatus()==400){
                return super.jsonObjectResult(new BaseException("验证码不正确", 500), "验证码不正确");
            }
            return "";
        }
        catch (Exception e) {
            e.printStackTrace();
            return super.jsonObjectResult(new BaseException("修改失败", 500), "修改失败");
        }
    }


  /*  @ApiOperation(value = "根据手机号来查询用户基本信息", notes = "根据手机号来查询用户基本信息")
    @RequestMapping(value = "/getUserInfoByMobileNo", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserInfoByMobileNo(@RequestParam(name = "mobileNum") String mobileNum) {
        try {
            BaseUser baseUser = this.defaultDAO.getUserByMobileNo(mobileNum);
            return super.jsonObjectResult(baseUser, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }*/

    /**
     * 从session中获取第三方用户信息，然后返回去。
     * @param request
     * @return
     */
/*  @RequestMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection =  providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderId(connection.getKey().getProviderUserId());
        userInfo.setNickName(connection.getDisplayName());
        userInfo.setHeadImg(connection.getImageUrl());
        return userInfo;
    }*/

    /**
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public BaseUser getTest(@RequestParam("username") String username) {
        BaseUser user = this.defaultDAO.getUserByUserName(username);
        return user;
    }

    @ApiOperation(value = "修改用户密码",
            notes = "修改用户密码")
    @RequestMapping(value = "/modifyPD.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getCurrentUser(@RequestBody BaseUser baseUser) {
        boolean result = false;
        try {
            if(StringUtils.isNotBlank(baseUser.getId())){
                String password = baseUser.getPassword();
                if(!"".equals(password) && password!=null){
                    if(password.length()<50 ){
                        String pd = MD5.encryptPassword(password);
                        baseUser.setPassword(pd);
                        //由于资源卫星老用户的原因,
                        // 这里需要修改老用户的状态来表明被修改过
                        baseUser.setSatelliteType("0");
                    }
                    result= this.defaultDAO.insertOrUpdate(baseUser);
                }
            }
            return super.jsonObjectResult(result, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return super.jsonObjectResult(new BaseException("修改失败", 500), "修改失败");
        }
    }













}

