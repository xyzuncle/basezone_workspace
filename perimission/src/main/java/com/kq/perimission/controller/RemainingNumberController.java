package com.kq.perimission.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Strings;
import com.kq.common.DTO.BaseResponse;
import com.kq.common.exception.BaseException;
import com.kq.perimission.domain.RemainingNumber;
import com.kq.perimission.dto.RemaingDTO;
import com.kq.perimission.service.IRemainingNumberService;
import com.kq.perimission.service.impl.TicketsServiceImpl;
import com.kq.perimission.util.DateUtil;
import com.kq.perimission.util.Servlets;
import com.kq.perimission.util.Sort;
import com.kq.perimission.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import sun.awt.SunHints;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yerui
 * @since 2018-07-03
 */
@Api(value = "remainingNumber",description = "剩余订单数量表，用于维护订单和用户之间的关系")
@Controller
@RequestMapping("/remainingNumber")
public class RemainingNumberController extends BaseController<RemainingNumber,IRemainingNumberService>{


    @Autowired
    TicketsServiceImpl ticketsService;

    @ApiOperation(value = "增加/修改订单剩余数量表", notes = "增加/修改订单剩余数量表")
    @RequestMapping(value = "/save.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object  saveOrUpdate(@RequestBody RemainingNumber remainingNumber) {
        boolean result = false;
        try{
            result = this.defaultDAO.insertOrUpdate(remainingNumber);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存失败",500);
        }
        return super.jsonObjectResult(result, "保存成功");

    }

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
        Page<RemainingNumber> userListPage = null;
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


    @ApiOperation(value = "根据Id删除订单剩余数量表")
    @RequestMapping(value = "/removeById.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteBaseUserById(@ApiParam(value = "订单剩余数量表唯一标识") @RequestParam(name = "entityID") String entityID) {
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

    @ApiOperation(value = "根据ID获取订单剩余数量表的信息")
    @RequestMapping(value = "/queryById.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@RequestParam(name = "id") String id){
        try{
            RemainingNumber user = this.defaultDAO.selectById(id);
            return  super.jsonObjectResult(user, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
    }

    @ApiOperation(value = "根据多个ID，批量删除订单剩余数量表")
    @RequestMapping(value = "/removeMore.do",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "订单剩余数量表多个id") @RequestParam(name = "entityIDS") String entityIDS){
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

    @ApiOperation(value = "根据业务ID更改订单剩余数量")
    @RequestMapping(value = "/updateBusNum.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object updateByGroupId(@ApiParam(value = "业务ID，生产单ID或采集单ID") @RequestParam("businessId")String businessId,
                                  @ApiParam(value = "需要更新的剩余订单") @RequestParam("num") int num){
        boolean result = false;
         try{
             this.defaultDAO.updateRemaingNumberBybusId(businessId,num);
             result = true;
         }catch (Exception e){
             e.printStackTrace();
             result = false;
             return new BaseResponse(500, "更新剩余数量失败！");
         }

         return super.jsonObjectResult(result, "更新剩余数量成功！");
    }


    @ApiOperation(value = "获取5层map的效验规则")
    @RequestMapping(value = "/getBigMap.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getBigMap(@ApiParam(value = "类型ID") @RequestParam("type")String type,
     @ApiParam(value="用户id") @RequestParam("userId") String userId){
        try{
            Map map  = this.defaultDAO.getDateRule(type,userId);
            return super.jsonObjectResult(map, "查询成功");

        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse(500, "查询失败");
        }

    }

    @ApiOperation(value = "批量保存中间表")
    @RequestMapping(value = "/batchSave.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object batchSaveEntity(@RequestBody RemaingDTO remainingNumbers){
        boolean result = false;
        try{
             if(!Objects.isNull(remainingNumbers)){
                 if(remainingNumbers.getRemainingNumbers()!=null && remainingNumbers.getRemainingNumbers().size()>0){
                     List<String> ids = new ArrayList<String>();
                     String nowString = ticketsService.getDateNow();
                     Date now = DateUtil.fomatDate(nowString);
                     remainingNumbers.getRemainingNumbers().stream().forEach(remainingNumber -> {
                         if(!Strings.isNullOrEmpty(remainingNumber.getStatus())){
                             //在剩余数量充足但是有效期失效的情况下，做的逻辑判断
                             if(remainingNumber.getStatus().equals("1")){
                                 if(remainingNumber.getEffectiveBegtinDate()!=null && remainingNumber.getEffectiveEndDate()!=null){
                                     boolean resultone =   DateUtil.isinDateByJODA(now,remainingNumber.getEffectiveBegtinDate(),
                                             remainingNumber.getEffectiveEndDate(),"yyyy-MM-dd");
                                     boolean result2 = DateUtil.getFutureTimeFlag(nowString, remainingNumber.getEffectiveBegtinDate());
                                     if(resultone==false && result2==false){
                                         //ids.add(remainingNumber.getId());
                                         remainingNumber.setStatus("0");
                                     }
                                 }
                             }
                         }
                     });
                   /*  //整理出ID
                     if(ids!=null && ids.size()>0){
                         return  super.jsonObjectResult(ids, "保存失败，某些数据权限有效期已经失效！");
                     }else{*/
                         //正常保存
                         this.defaultDAO.batchSaveEntity(remainingNumbers.getRemainingNumbers());
                  /*   }*/
                 }
             }
             result = true;

        }catch (Exception e){
            e.printStackTrace();
            result = false;
            return new BaseResponse(500, "保存失败");
        }

        return super.jsonObjectResult(result, "保存成功");

    }


    @RequestMapping(value = "/view.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getRemaingNumberInfo(HttpServletRequest request) {
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<RemainingNumber> list = super.queryContionNoPage(searchParams);

            Map<String,List<RemainingNumber>> result =
                    list.stream().collect(Collectors.groupingBy(RemainingNumber::getType));

            return super.jsonObjectResult(result, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }

    }

    @RequestMapping(value = "/queryRemaingInfo.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public RemainingNumber queryRemaingNumberPermission(@RequestParam("userId") String userId
                                                                 ,@RequestParam("satelliteType") String satelliteType
                                                                 ,@RequestParam("satelliteID") String satelliteId
                                                                 ) {
        try {
            //把查询条件都写好了
            EntityWrapper<RemainingNumber> wp = new EntityWrapper<RemainingNumber>();
            wp.eq("userId", userId);
            wp.eq("satelliteType", satelliteType);
            wp.eq("satelliteId", satelliteId);
            //查询出唯一的结果
            RemainingNumber list = this.defaultDAO.selectOne(wp);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }

    }

    @RequestMapping(value = "/getRedisMap.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getRedisDateMap(@RequestParam("type") String type,
                                    @RequestParam("userId") String userId,@RequestParam("level") String level){
        boolean result = true;
        try{
           this.defaultDAO.putReidsDateRule(type,userId,level);
        }
        catch (Exception e){
            e.printStackTrace();
            result = false;
        }

        return result;
    }


}

