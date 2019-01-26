package com.kq.perimission.controller.rpc.rest;

import com.ace.cache.annotation.Cache;

import com.alibaba.fastjson.JSON;
import com.kq.common.DTO.BaseResponse;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.exception.BaseException;
import com.kq.perimission.domain.BaseMenu;
import com.kq.perimission.controller.rpc.service.PerimissionService;
import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.dto.PerimissionDTO;
import com.kq.perimission.dto.PerimissionView;
import com.kq.perimission.ip.IPinfo;
import com.kq.perimission.service.impl.BaseUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Api(value = "UserRest",description = "获取用户权限")
@RestController
@RequestMapping("/api")
public class UserRest {

    protected final static Logger logger = LoggerFactory.getLogger(UserRest.class);

    @Autowired
    private PerimissionService permissionService;

    @Autowired
    private BaseUserServiceImpl baseUserService;

    @ApiOperation(value = "获取系统所有权限",notes = "获取系统所有权限")
    @Cache(key="permission")
    @RequestMapping(value = "/permissions", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  List<PerimissionInfo> getAllPermission(){
        return permissionService.getAllPermission();
    }



    @ApiOperation(value = "获取用户服务权限",notes = "根据用户登录名获取该用户能够访问的服务权限")
    @Cache(key="permission:service{1}")
    @RequestMapping(value = "/user/us/{username}/permissions", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<PerimissionInfo> getUserPermission(@ApiParam(value = "用户登录名") @PathVariable("username") String username){
        return permissionService.getUserService(username);
    }

    //{1}表示参数的占位符，就是第一个参数
    @ApiOperation(value = "根据用户登录名获取该用户的权限",notes = "根据用户登录名获取该用户的权限," +
            "权限按类型分三种 1:perimissionType=MENU 是菜单类型  2：perimissionType=ELEMENT 是元素  3 perimissionType=USER_SERVICE 代表该用户能访问的服务")
    @Cache(key="permission:u{1}")
    @RequestMapping(value = "/user/un/{username}/permissions", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  List<PerimissionInfo> getPermissionByUsername(@ApiParam(value = "用户登录名") @PathVariable("username") String username){
        return permissionService.getPermissionByUsername(username);
    }

    @ApiOperation(value = "获取菜单树",notes = "根据用户名获取用户菜单树,获取父子树格式")
    @Cache(key = "menu:tree{1}")
    @RequestMapping(value = "/front/menus/{username}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<BaseMenu> getMenusByUsername(@PathVariable("username") String username){
        return permissionService.getMenuTreeByUserName(username);
    }


  /*  @ApiOperation(value = "获取菜单树",notes = "根据用户名获取用户顶级树菜单")
    @Cache(key = "parentmenu:parent{1}")
    @RequestMapping(value = "/parent/menutop/{username}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<BaseMenu> getParentMenuByUsername(@PathVariable("username") String username){
        return permissionService.getParentMenu(username);
    }*/


    /**
     * 根据 用户名获取 该用户下的 父子菜单 有排序的IDs
     * @param username
     * @return
     */
    @ApiOperation(value = "获取菜单树",notes = "根据用户名获取用户菜单树,菜单树是MAP格式，有序")
    @Cache(key = "menu:id{1}")
    @RequestMapping(value = "/front/menuids/{username}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String,BaseMenu> getMenuIdsByUsername(@ApiParam(value = "用户登录名") @PathVariable("username") String username){
        return permissionService.getMenuIdsByUserName(username);
    }


    /**
     * 根据lambda表达式，把List转换成map
     * @param accounts
     * @return
     */
    public Map<String, List<PerimissionInfo>> getPerimissionMapByType(List<PerimissionInfo> accounts) {
        return accounts.stream().collect(Collectors.groupingBy(PerimissionInfo::getPerimissionType));
    }

    /**
     * 根据lambda表达式，把List转换成map
     * @param accounts
     * @return
     */
    public Map<String, List<PerimissionInfo>> getPerimissionMapById(List<PerimissionInfo> accounts) {
        return accounts.stream().collect(Collectors.groupingBy(PerimissionInfo::getId));
    }


    //根据用户获取该用户下，该菜单下的所有元素。
    @Cache(key="permission:element{1}")
    @RequestMapping(value = "/user/element/{username}", method = RequestMethod.GET)
    public Map<String,List<PerimissionInfo>> getElementByUsername(@PathVariable("username") List<PerimissionInfo> username){
        Map<String,List<PerimissionInfo>> testRe =   getPerimissionMapByType(username);
        Map<String,List<PerimissionInfo>> idResult = getPerimissionMapById(testRe.get("ELEMENT"));
        return  idResult;
    }

    @ApiOperation(value = "根据用户名获取用户",notes = "根据用户名获取用户")
    @Cache(key="user:user{1}")
    @RequestMapping(value = "/user/getuser/{username}", method = RequestMethod.GET)
    public BaseUser getUserByUserName(@PathVariable("username") String userName) {
        return baseUserService.getUserByUserName(userName);
    }

    @ApiOperation(value = "保存权限",notes = "保存功能权限、数据权限、保存组，保存采集权限")
    @RequestMapping(value = "/savePerimission", method = RequestMethod.POST)
    @ResponseBody
    public Object savePermission(@RequestBody PerimissionDTO pt){
        try{
            this.permissionService.savePerimission(pt);
            return JSON.toJSON(new ObjectRestResponse().data("true").message("保存成功"));
        }
        catch (BaseException be){
            be.printStackTrace();
            return JSON.toJSON(new BaseResponse(500,be.getMessage()));
        }
        catch (Exception e){
            e.printStackTrace();
            return JSON.toJSON(new BaseResponse(500,e.getMessage()));
        }

    }

    @ApiOperation(value = "查询所属组的所有权限",notes = "根据用户名和所属组ID,会获取该用户的前端权限，后端权限、采集权限和生产权限")
    @RequestMapping(value = "/queryPerimission", method = RequestMethod.GET)
    @ResponseBody
    public Object savePermission(@RequestParam("userName") String userName , @RequestParam("groupId") String groupId){
        PerimissionView perimissionView =  this.permissionService.queryPerimission(userName, groupId);
        return JSON.toJSON(new ObjectRestResponse().data(perimissionView).message("查询成功"));
    }

    @ApiOperation(value = "测试获取IP地址")
    @RequestMapping(value = "/testIP",method = RequestMethod.GET)
    @ResponseBody
    public String getIp(HttpServletRequest request) throws Exception{

        // 从Nginx中X-Real-IP获取真实ip
        String ipAddress = request.getHeader("X-Real-IP");

        if (ipAddress != null && ipAddress.length() > 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
            logger.info("从X-Real-IP中获取到ip:" + ipAddress);
            return ipAddress;
        }

        // 从Nginx中x-forwarded-for获取真实ip
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress != null && ipAddress.length() > 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            int index = ipAddress.indexOf(",");
            if (index > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
            logger.info("从x-forwarded-for中获取到ip:" + ipAddress);
            return ipAddress;
        }

        ipAddress = request.getRemoteAddr();
        if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
        // 根据网卡取本机配置的IP
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        }
        logger.info("从request.getRemoteAddr()中获取到ip:" + ipAddress);
        return ipAddress;
    }


    @ApiOperation(value = "根据真实IP获取地域,需要nginx配合",notes = "真实ip：例如 27.16.192.1,返回的结果包含经纬度等相信信息 ")
    @RequestMapping(value = "/iptoname",method = RequestMethod.GET)
    @ResponseBody
    public Object getIPtoName(String ipAddress) throws Exception{
        Map resutl = IPinfo.getIPtoName(ipAddress);
        return JSON.toJSON(new ObjectRestResponse().data(resutl).message("查询成功"));
    }



    @RequestMapping(value = "/updateRemainNum",method = RequestMethod.GET)
    @ResponseBody
    public void updateNumByUserGroupID(@RequestParam("groupId") String groupId,@RequestParam("userId") String userId){
            this.permissionService.updateNumByUserGroupID(groupId, userId,"");
    }

  /*  @ApiOperation(value = "根据组ID反向更新订单剩余表",notes = "根据组ID反向更新订单剩余表")
    @RequestMapping(value = "/updateRemainNumByGroupId",method = RequestMethod.GET)
    @ResponseBody
    public void updateNumByUserGroupID(@RequestParam("groupId") String groupId){
        this.permissionService.updateNumByGroupId(groupId);
    }*/

    /**
     * 根据用户username获取
     * @param username
     * @return
     */
    @ApiOperation(value = "根据用户ID获取用户API权限",notes = "根据用户ID获取用户api权限")
    @RequestMapping(value = "/userapi/{username}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public List<PerimissionInfo> getApiByUserId(@PathVariable("username")  String username){
       return this.permissionService.getApiByUserId(username);
   }



}
