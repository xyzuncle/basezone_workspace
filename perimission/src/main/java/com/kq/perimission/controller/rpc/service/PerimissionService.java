package com.kq.perimission.controller.rpc.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.base.Strings;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.exception.BaseException;
import com.kq.perimission.constant.AdminCommonConstant;
import com.kq.perimission.domain.*;
import com.kq.perimission.dto.PerimissionDTO;
import com.kq.perimission.dto.PerimissionView;
import com.kq.perimission.service.impl.*;
import com.kq.perimission.util.BeanCopierUtils;
import com.kq.perimission.util.DateUtil;
import com.sun.xml.internal.rngom.parse.host.Base;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PerimissionService {

    @Autowired
    private BaseMenuServiceImpl iBaseMenuService;

    @Autowired
    private BaseElementServiceImpl baseElementService;

    @Autowired
    private BaseUserServiceImpl baseUserService;

    @Autowired
    private BaseGroupServiceImpl baseGroupService;

    @Autowired
    private BaseUserServiceEntityServiceImpl baseUserServiceEntityService;

    @Autowired
    private ProductPermissionServiceImpl productPermissionService;

    @Autowired
    private PickPerimissionServiceImpl pickPerimissionService;

    @Autowired
    private BaseResourceAuthorityServiceImpl baseResourceAuthorityService;

    @Autowired
    private RemainingNumberServiceImpl remainingNumberService;

    @Autowired
    TicketsServiceImpl ticketsService;

    //获取所有权限
    public List<PerimissionInfo> getAllPermission() {
        //获取所有menu的菜单
        List<BaseMenu> menus = iBaseMenuService.selectListAll();
        List<PerimissionInfo> result = new ArrayList<PerimissionInfo>();
        PerimissionInfo info = null;
        //只是把meun放在了permission的集合中而已
        menu2permission(menus, result);
        //把所有的元素也放到了permission对象中
        List<BaseElement> elements = baseElementService.getAllElement();
        //这里把所有的页面元素放在list集合里
        element2permission(result, elements);
        return result;
    }

    //获取对应服务
    public List<PerimissionInfo> getUserService(String useName) {
        BaseUser user = baseUserService.getUserByUserName(useName);
        List<BaseUserServiceEntity> userServiceEntity = baseUserServiceEntityService.getServiceByUserId(user.getId());
        List<PerimissionInfo> result = new ArrayList<PerimissionInfo>();
        userService2permission(userServiceEntity, result);
        return result;
    }

    /**
     * 根据用于名获取顶级权限
     *
     * @param userName
     * @return
     */
    public List<BaseMenu> getParentMenu(String userName) {
        BaseUser user = baseUserService.getUserByUserName(userName);
        return this.iBaseMenuService.selectParentMenuByMenuType(user.getId(), "back", -1 + "");

    }

    /**
     * 获取全部顶级权限
     *
     * @param
     * @return
     */
    public List<BaseMenu> getParentMenu() {
        EntityWrapper<BaseMenu> ew = new EntityWrapper<BaseMenu>();
        ew.andNew("parent_id", -1);
        List<BaseMenu> baseMenusList = iBaseMenuService.selectList(ew);
        return baseMenusList;
    }


    public List<PerimissionInfo> getPermissionByUsername(String username) {
        BaseUser user = baseUserService.getUserByUserName(username);
        List<BaseMenu> menus = iBaseMenuService.getMenuByUserId(user.getId());
        List<PerimissionInfo> result = new ArrayList<PerimissionInfo>();
        PerimissionInfo info = null;
        menu2permission(menus, result);
        List<BaseElement> elements = baseElementService.getElementByUser(user.getId());
        element2permission(result, elements);
        List<BaseUserServiceEntity> userServiceEntity = baseUserServiceEntityService.getServiceByUserId(user.getId());
        userService2permission(userServiceEntity, result);
        return result;
    }


    /**
     * 把menu菜单的属性，全部放到permissionInfo 这个集合
     *
     * @param menus
     * @param result
     */
    private void menu2permission(List<BaseMenu> menus, List<PerimissionInfo> result) {
        PerimissionInfo info;
        for (BaseMenu menu : menus) {
            if (StringUtils.isBlank(menu.getHref())) {
                menu.setHref("/" + menu.getCode());
            }
            info = new PerimissionInfo();
            info.setCode(menu.getCode());
            info.setType(AdminCommonConstant.RESOURCE_TYPE_MENU);
            info.setName(AdminCommonConstant.RESOURCE_ACTION_VISIT);
            info.setPerimissionType(AdminCommonConstant.RESOURCE_TYPE_MENU);
            String uri = menu.getHref();
            if (!uri.startsWith("/")) {
                uri = "/" + uri;
            }
            info.setUri(uri);
            info.setMethod(AdminCommonConstant.RESOURCE_REQUEST_METHOD_GET);
            result.add(info
            );
            info.setMenu(menu.getTitle());
        }
    }

    private void element2permission(List<PerimissionInfo> result, List<BaseElement> elements) {
        PerimissionInfo info;
        for (BaseElement element : elements) {
            info = new PerimissionInfo();
            info.setCode(element.getCode());
            info.setType(element.getType());
            info.setUri(element.getUri());
            info.setMethod(element.getMethod());
            info.setName(element.getName());
            info.setPerimissionType(AdminCommonConstant.RESOURCE_TYPE_ELEMENT);
            result.add(info);
        }
    }

    //这里把得到的用户服务也放到permission对象中，减少依赖
    private void userService2permission(List<BaseUserServiceEntity> serviceEntities, List<PerimissionInfo> result) {
        PerimissionInfo info;
        for (BaseUserServiceEntity element : serviceEntities) {
            info = new PerimissionInfo();
            info.setCode(null);
            info.setType(AdminCommonConstant.RESOURCE_TYPE_SERVICE);
            info.setUri(element.getServerUri());
            info.setMethod(null);
            info.setName(null);
            info.setMenu(null);
            info.setPerimissionType(AdminCommonConstant.RESOURCE_TYPE_SERVICE);
            result.add(info);
        }
    }

    /**
     * 根据用户名获取该用户组下的所有树形菜单
     *
     * @param userName
     * @return 获取父类的递归树形菜单
     */
    public List<BaseMenu> getMenuTreeByUserName(String userName) {

        BaseUser user = baseUserService.getUserByUserName(userName);
        List<BaseMenu> baseMenuList = iBaseMenuService.getMenusWithParentById(user.getId(), AdminCommonConstant.ROOT + "");

        return baseMenuList;
    }

    /**
     * 根据用户名获取该菜单下的递归的ID
     *
     * @param userName
     * @return
     */
    public Map<String, BaseMenu> getMenuIdsByUserName(String userName) {
        BaseUser user = baseUserService.getUserByUserName(userName);
        Map<String, BaseMenu> result = new LinkedHashMap<String, BaseMenu>();
        return iBaseMenuService.getMenus2IdChild(user.getId(), AdminCommonConstant.ROOT + "", result);
    }


    /**
     * 保存所有权限包括数据权限，功能权限和采集权限 和组的权限
     */
    @Transactional
    public void savePerimission(PerimissionDTO perimissionDTO) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        String groupId = perimissionDTO.getBaseGroup().getId();

        //设置流水号
        if(Strings.isNullOrEmpty(groupId)){
            String squenceId = ticketsService.getAppLastId("role",perimissionDTO.getBaseGroup());
            perimissionDTO.getBaseGroup().setAttr1(squenceId);

            //在保存前需要判断，用户名是否唯一
            if(perimissionDTO.getBaseGroup().getName()!=null){
                boolean nameresult = this.baseGroupService.getExistGroupName(perimissionDTO.getBaseGroup().getName());
                if(nameresult==true){
                    throw new BaseException("角色用户名重复",500);
                }
            }

        }
        if(!"".equals(groupId) && groupId!=null){
            uuid = groupId;
        }else{
            perimissionDTO.getBaseGroup().setId(uuid);
        }

        boolean result = this.baseGroupService.insertOrUpdate(perimissionDTO.getBaseGroup());

        if (result == true) {
            //先清除所有groupId下的所有菜单
            //根据需求变化,可能会有空值的变化
            if(perimissionDTO.getBaseResource()!=null&& perimissionDTO.getBaseResource().size()>0){
                this.baseResourceAuthorityService.deleteMenuByGroupId(uuid);
                String finalUuid = uuid;
                perimissionDTO.getBaseResource().stream().forEach(BaseResourceAuthority ->{
                    BaseResourceAuthority.setAuthorityId(finalUuid);
                });
                this.baseResourceAuthorityService.insertBatch(perimissionDTO.getBaseResource());
            }

            //优先处理中间表的业务逻辑，是为了多余的数据在中间表进行删除
            //this.updateNumByGroupId(uuid,perimissionDTO.getProductPermission(),perimissionDTO.getPickPerimission());

            //先清除所有，然后保存groupId 菜单
            if( perimissionDTO.getProductPermission()!=null &&  perimissionDTO.getProductPermission().size()>0){
                this.productPermissionService.deleteByGroupId(uuid);
                String finalUuid1 = uuid;
                perimissionDTO.getProductPermission().stream().forEach(productPermission ->{
                    if(StringUtils.isBlank(productPermission.getEffectiveDay())){
                        productPermission.setEffectiveDay("0");
                    }
                    if(StringUtils.isBlank(productPermission.getEffectiveMouth())){
                        productPermission.setEffectiveMouth("0");
                    }
                    if(StringUtils.isBlank(productPermission.getEffectiveYear())){
                        productPermission.setEffectiveYear("0");
                    }
                    productPermission.setGroupId(finalUuid1);
                });
                this.productPermissionService.insertBatch(perimissionDTO.getProductPermission());
            }else{
                //如果前台传值为空，则吧对应表删除
               boolean deleteResult =  this.productPermissionService.deleteByGroupId(uuid);
            }

            //先清除所有，
            if(perimissionDTO.getPickPerimission()!=null && perimissionDTO.getPickPerimission().size()>0){
                this.pickPerimissionService.deleteByGroupId(uuid);
                String finalUuid2 = uuid;
                perimissionDTO.getPickPerimission().stream().forEach(pickPerimission->{
                    pickPerimission.setGroupId(finalUuid2);
                });
                this.pickPerimissionService.insertBatch(perimissionDTO.getPickPerimission());
            }else{
                boolean deleteReuslt =  this.pickPerimissionService.deleteByGroupId(uuid);
            }

            //中间表进行全删全插
            //this.updateBusinessId(uuid);

        }

    }


    public PerimissionView queryPerimission(String username, String groupid) {
        PerimissionView viewDto = new PerimissionView();
        BaseUser user = baseUserService.getUserByUserName(username);
        List<String> idList = null;
        List<String> fontMenuListList = null;
        if (user != null && !user.equals("")) {

            BaseGroup baseGroup = baseGroupService.selectById(groupid);
            viewDto.setBaseGroup(baseGroup);

            String resourceType = "";
            if(baseGroup.getGroupType()!=null){
                if(baseGroup.getGroupType().equals("1")){
                    resourceType = "group";
                }else if(baseGroup.getGroupType().equals("2")){
                    resourceType = "outside";
                }
            }

            List<BaseMenu> baseMenusList = iBaseMenuService.selectParentMenuByGroupId(user.getId(), -1 + "", "menu",groupid,resourceType);
            idList = baseMenusList.stream().map(BaseMenu::getId).collect(Collectors.toList());
            viewDto.setBackMenu(idList);

            List<BaseMenu> fontMenuList = iBaseMenuService.selectParentMenuByGroupId(user.getId(), -1 + "", "fontmenu",groupid,resourceType);
            fontMenuListList = fontMenuList.stream().map(BaseMenu::getId).collect(Collectors.toList());
            viewDto.setFontMenu(fontMenuListList);


            List<PickPerimission> pickList = pickPerimissionService.getPickListByGroupId(groupid);
            viewDto.setPickPerimission(pickList);


            List<ProductPermission> productList = productPermissionService.getProductListByGroupId(groupid);
            viewDto.setProductPermission(productList);
        }
        return viewDto;
    }

    /**
     * 适用于用户为主体，变更用户和组之间的关系后发生的订单剩余表的关系维护
     * @param groupId
     * @param userId
     */
    @Transactional
    public void updateNumByUserGroupID(String groupId,String userId,String oldGroupId){
        //获取改组下的采集单数据
        EntityWrapper<PickPerimission> pickWrapper = new EntityWrapper<PickPerimission>();
        pickWrapper.eq("group_id", groupId);
        List<PickPerimission> pickPerimissions = this.pickPerimissionService.selectList(pickWrapper);

        //获取改组下的生产单数据
        EntityWrapper<ProductPermission> productWrapper = new EntityWrapper<ProductPermission>();
        productWrapper.eq("group_id", groupId);
        List<ProductPermission> productPermissionList = this.productPermissionService.selectList(productWrapper);
        List<RemainingNumber> remainingNumberList = new LinkedList<RemainingNumber>();


        //用于判断是否是第一次更新关系，用于新增角色和新用户绑定老角色。
        EntityWrapper<RemainingNumber> ew = new EntityWrapper<RemainingNumber>();

        ew.eq("user_id",userId);

        List<RemainingNumber>  existList = remainingNumberService.selectList(ew);
        //需要反查一次用户的创建时间，作为初始化时间
        BaseUser baseUser = baseUserService.selectById(userId);

        //便利数据进行更新
        //第一种情况，新建角色，新建用户，管理关联关系
        List<RemainingNumber> updataList = new ArrayList<RemainingNumber>();
        //数据库本地时间
        String nowString = ticketsService.getDateNow();
        //如果existList没有数据，则新建的角色，但是还没有关联用户关系
        if(existList!=null && existList.isEmpty()){

            productPermissionList.stream().forEach(productPermission -> {
                RemainingNumber remainingNumber = new RemainingNumber();
                BeanCopierUtils.copyProperties(productPermission,remainingNumber);
                remainingNumber.setUserId(userId);

                remainingNumber.setBusinessId(productPermission.getId());
                remainingNumber.setUserId(userId);
                remainingNumber.setGroupId(groupId);
                remainingNumber.setRemainingNumber(productPermission.getTotalOrderNumber());
                remainingNumber.setTotalNumber(productPermission.getTotalOrderNumber());
                remainingNumber.setType("1");
                //状态默认重置1，如果有变动后续的逻辑会处理
                remainingNumber.setStatus("1");

                //防止主键重复
                remainingNumber.setId("");

                Date orgTime =  DateUtil.fomatDate(nowString);

                remainingNumber = this.countTime(orgTime, remainingNumber.getEffectiveYear()
                        ,remainingNumber.getEffectiveMouth(),
                        remainingNumber.getEffectiveDay(),remainingNumber);


                Date now = DateUtil.fomatDate(nowString);
                //比较时间,是防止用户注册时间是几年以前的数据，绑定到角色上，导致数据异常
                if(remainingNumber.getEffectiveBegtinDate()!=null && remainingNumber.getEffectiveEndDate()!=null){
                    boolean resultone =   DateUtil.isinDateByJODA(now,remainingNumber.getEffectiveBegtinDate(), remainingNumber.getEffectiveEndDate(),"yyyy-MM-dd");
                    boolean result2 = DateUtil.getFutureTimeFlag(nowString, remainingNumber.getEffectiveBegtinDate());
                    if(resultone==false && result2==false){
                        //ids.add(remainingNumber.getId());
                        remainingNumber.setStatus("0");
                    }
                }

                //同时判断剩余数量是否小于0同时不等于-1
                if(remainingNumber.getRemainingNumber()<0 && remainingNumber.getRemainingNumber()!=-1){
                    remainingNumber.setStatus("0");
                }

                updataList.add(remainingNumber);
            });

            this.remainingNumberService.insertOrUpdateBatch(updataList);
        }else{

            //如果查到.证明已经存在关系，需更换。

            if(pickPerimissions!=null && pickPerimissions.size()>0){
                //当切组的时候，才进行中间表的更新
                if(!oldGroupId.equals(baseUser.getGroupId())){
                    String dbTime = ticketsService.getDateNow();
                    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
                    pickPerimissions.stream().forEach(pickPerimission -> {
                        RemainingNumber temp = new RemainingNumber();
                        BeanCopierUtils.copyProperties(pickPerimission,temp);
                        //防止主键重复
                        temp.setId("");
                        temp.setUserId(userId);
                        temp.setGroupId(groupId);
                        temp.setType("2"); //采集单
                        temp.setBusinessId(pickPerimission.getId());
                        temp.setRemainingNumber(pickPerimission.getPickNumber());
                        /* temp.setStatus("1");//0 数据权限无效  1 数据权限有效*/
                        remainingNumberList.add(temp);
                    });
                }

            }

            if(productPermissionList!=null && productPermissionList.size()>0){
                String dbTime = ticketsService.getDateNow();
                DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

                productPermissionList.stream().forEach(productPermission -> {

                   /* if(StringUtils.isNotBlank(productPermission.getTimeInfiniteStatus())){
                        if(productPermission.getTimeInfiniteStatus().equals("1")){
                            //从原始数据中获取开始时间，进行新的有效时间计算
                            Date orgTime = null;
                            DateTime dateTime = null;
                            if(existList !=null && existList.size()>0){
                                RemainingNumber temp =   existList.get(0);
                                if(temp.getOriginTime()!=null){
                                    orgTime =  temp.getOriginTime();
                                    dateTime = new DateTime(orgTime);
                                }else{
                                    //防止混乱的数据发生，造成底部的空指针异常
                                    //如果中间表中，没有原始时间，就用生产单权限的创建时间作为原始时间
                                    dateTime = new DateTime(productPermission.getCrtTime());
                                    orgTime = dateTime.toDate();
                                }

                            }else{
                                //如果中间表中，没有原始时间，就用生产单权限的创建时间作为原始时间
                                dateTime = new DateTime(productPermission.getCrtTime());
                                orgTime = dateTime.toDate();
                            }
                            temp2.setOriginTime(orgTime);
                            temp2.setEffectiveBegtinDate(orgTime);

                            if(StringUtils.isNotBlank(productPermission.getEffectiveYear())){
                                int year = Integer.valueOf(productPermission.getEffectiveYear());
                                dateTime = dateTime.plusYears(year);
                            }else{
                                dateTime =  dateTime.plusYears(0);
                            }
                            if (StringUtils.isNotBlank(productPermission.getEffectiveMouth())) {
                                dateTime = dateTime.plusMonths(Integer.valueOf(productPermission.getEffectiveMouth()));
                            } else {
                                dateTime = dateTime.plusMonths(0);
                            }
                            if (StringUtils.isNotBlank(productPermission.getEffectiveDay())) {
                                dateTime = dateTime.plusDays(Integer.valueOf(productPermission.getEffectiveDay()));
                            } else {
                                dateTime = dateTime.plusDays(0);
                            }
                            Date d2 =  dateTime.toDate();
                            temp2.setEffectiveEndDate(d2);
                        }else{
                            temp2.setOriginTime(null);
                            temp2.setEffectiveBegtinDate(null);
                            temp2.setEffectiveEndDate(null);
                        }
                    }*/
                    //只有组ID发生变化的时候，才可以进行修改
                    if(!oldGroupId.equals(baseUser.getGroupId())){
                        RemainingNumber temp2 = new RemainingNumber();
                        BeanCopierUtils.copyProperties(productPermission,temp2);

                        temp2.setBusinessId(productPermission.getId());
                        temp2.setUserId(userId);
                        temp2.setGroupId(groupId);
                        temp2.setRemainingNumber(productPermission.getTotalOrderNumber());
                        temp2.setTotalNumber(productPermission.getTotalOrderNumber());
                        temp2.setType("1");
                        temp2.setStatus("1");

                        //防止主键重复
                        temp2.setId("");

                        //2018年9月10日 用户切换的时候，用修改时间作为原始时间
                        // ，进行重新计算开始时间，结束时间
                        Date now = DateUtil.fomatDate(nowString);
                        temp2.setOriginTime(now);
                        //计算开始时间结束时间
                        temp2 = this.countTime(now, productPermission.getEffectiveYear(),
                                productPermission.getEffectiveMouth(), productPermission.getEffectiveDay()
                                , temp2);

                        //比较时间看看数据是否失效
                        if(temp2.getEffectiveBegtinDate()!=null && temp2.getEffectiveEndDate()!=null){
                            boolean resultone =   DateUtil.isinDateByJODA(now,temp2.getEffectiveBegtinDate(), temp2.getEffectiveEndDate(),"yyyy-MM-dd");
                            boolean result2 = DateUtil.getFutureTimeFlag(nowString, temp2.getEffectiveBegtinDate());
                            if(resultone==false && result2==false){
                                //ids.add(remainingNumber.getId());
                                temp2.setStatus("0");
                            }
                        }
                        //同时判断剩余数量是否小于0同时不等于-1
                        if(temp2.getRemainingNumber()<0 && temp2.getRemainingNumber()!=-1){
                            temp2.setStatus("0");
                        }

                        remainingNumberList.add(temp2);
                    }



                });
            }

            if(remainingNumberList!=null && remainingNumberList.size()>0){
                //删除该用户下的所有用户和订单的关系
                remainingNumberService.deleteByUserId(userId);
                //批量插入用户和 订单剩余关系表
                remainingNumberService.batchInsertNum(remainingNumberList);
            }
        }





    }

    /**
     * 当前用户角色的数据权限发生了变化维护用户订单剩余表的关系
     * @param groupId
     */
    @Transactional
    public void updateNumByGroupId(String groupId,List<ProductPermission> fontProducts,
                                   List<PickPerimission> fontPickPerimiss) {
        //获取该组下的采集单数据
        EntityWrapper<PickPerimission> pickWrapper = new EntityWrapper<PickPerimission>();
        pickWrapper.eq("group_id", groupId);
        List<PickPerimission> backPickPerimissions = this.pickPerimissionService.selectList(pickWrapper);

        //获取该组下的生产单数据
        EntityWrapper<ProductPermission> productWrapper = new EntityWrapper<ProductPermission>();
        productWrapper.eq("group_id", groupId);
        List<ProductPermission> backProductPermissionList = this.productPermissionService.selectList(productWrapper);
        List<String> resutl = null;
        if(backPickPerimissions!=null && backPickPerimissions.size()>0){
             resutl =  backPickPerimissions.stream().map(PickPerimission::getId).collect(Collectors.toList());
        }

        List<String> result2 = null;
        if(backProductPermissionList!=null && backProductPermissionList.size()>0){
            result2 = backProductPermissionList.stream().map(ProductPermission::getId).collect(Collectors.toList());
        }

        //把采集单和生产单的IDS合并并进行查询
        EntityWrapper<RemainingNumber> bWrapper = new EntityWrapper<RemainingNumber>();
        List<RemainingNumber> remainingNumbers = null;
        if(resutl!=null && result2!=null){
            resutl.addAll(result2);
            bWrapper.in("business_id", resutl);
            remainingNumbers = remainingNumberService.selectList(bWrapper);
        }else if(resutl!=null && result2 == null){
            bWrapper.in("business_id", resutl);
            remainingNumbers = remainingNumberService.selectList(bWrapper);
        }else if(result2!=null && resutl == null){
            bWrapper.in("business_id", result2);
            remainingNumbers = remainingNumberService.selectList(bWrapper);
        }

        //把userId
         String userId = null;
        if(remainingNumbers!=null && remainingNumbers.size()>0){
             userId = remainingNumbers.get(0).getUserId();
        }

        //这段方法只负责排查
        if(Strings.isNullOrEmpty(userId)){
            //如果查不到，证明是第一次处理角色权限，后续逻辑进行保存
        }else{
            //数据库查询出来的采集单
            if(backPickPerimissions!=null && backPickPerimissions.size()>0){
                if(fontPickPerimiss==null ||fontPickPerimiss.size()==0){
                    List<String> oldPickList = backPickPerimissions.stream().map(pick -> pick.getId()).collect(Collectors.toList());
                    //在中间表删除多余的ID
                    EntityWrapper<RemainingNumber> entityWrapper = new EntityWrapper<>();
                    entityWrapper.in("business_id", oldPickList);
                    //this.remainingNumberService.delete(entityWrapper);
                }
                //如果从前端传入的集合小于已经存在的集合大小，证明中间表有ID需要被清楚掉
                //判断等于的目的是为了，配置的过程中，数量不变但是ID变化的情况。
                else if(fontPickPerimiss.size()<=backPickPerimissions.size()){
                    List<String>  newPickList = fontPickPerimiss.stream().map(pickPerimission ->
                            pickPerimission.getId()).collect(Collectors.toList());

                    List<String> oldPickList = backPickPerimissions.stream().map(pick -> pick.getId()).collect(Collectors.toList());
                    //求差值，把不同的ID比对出来，同时在中间表去删除改ID
                    List<String>  result =  oldPickList.stream().filter(PickPerimission ->
                            !newPickList.contains(PickPerimission)).collect(Collectors.toList());

                    //在中间表删除多余的ID
                    if(result!=null && result.size()>0){
                        EntityWrapper<RemainingNumber> entityWrapper = new EntityWrapper<>();
                        entityWrapper.in("business_id", result);
                        //this.remainingNumberService.delete(entityWrapper);
                    }

                }
            }

            if(backProductPermissionList!=null && backProductPermissionList.size()>0){
                //如果集合为空，则直接删除中间表bussinessID 相关数据
                if(fontProducts==null || fontProducts.size()==0){
                    List<String> oldPickList = backProductPermissionList.stream().map(product -> product.getId()).collect(Collectors.toList());
                    //在中间表删除多余的ID
                    EntityWrapper<RemainingNumber> entityWrapper = new EntityWrapper<>();
                    entityWrapper.in("business_id", oldPickList);
                    //this.remainingNumberService.delete(entityWrapper);
                }
                //判断等于的目的是为了，配置的过程中，数量不变但是ID变化的情况。
                else if(fontProducts.size()<=backProductPermissionList.size()){
                    List<String>  newPickList = fontProducts.stream().map(fontProductPermission ->
                            fontProductPermission.getId()).collect(Collectors.toList());

                    List<String> oldPickList = backProductPermissionList.stream().map(product -> product.getId()).collect(Collectors.toList());
                    //求差值，把不同的ID比对出来，同时在中间表去删除改ID
                    List<String>  result =  oldPickList.stream().filter(PickPerimission ->
                            !newPickList.contains(PickPerimission)).collect(Collectors.toList());

                    //在中间表删除多余的ID
                    if(result!=null && result.size()>0){
                        EntityWrapper<RemainingNumber> entityWrapper = new EntityWrapper<>();
                        entityWrapper.in("business_id", result);
                       // this.remainingNumberService.delete(entityWrapper);
                    }

                }
            }
        }

    }


    /**
     * 这个方法用于最新的采集单ID和生产单ID 批量的插入中间表
     * @param groupId
     */
    public void updateBusinessId(String groupId){
        //获取改组下的采集单数据
        EntityWrapper<PickPerimission> pickWrapper = new EntityWrapper<PickPerimission>();
        pickWrapper.eq("group_id", groupId);
        List<PickPerimission> backPickPerimissions = this.pickPerimissionService.selectList(pickWrapper);

        //获取改组下的生产单数据
        EntityWrapper<ProductPermission> productWrapper = new EntityWrapper<ProductPermission>();
        productWrapper.eq("group_id", groupId);
        List<ProductPermission> backProductPermissionList = this.productPermissionService.selectList(productWrapper);

        List<String> resutl = null;
        if(backPickPerimissions!=null && backPickPerimissions.size()>0){
            resutl =  backPickPerimissions.stream().map(PickPerimission::getId).collect(Collectors.toList());
        }


        List<String> result2 = null;
        if(backProductPermissionList!=null && backProductPermissionList.size()>0){
            result2 = backProductPermissionList.stream().map(ProductPermission::getId).collect(Collectors.toList());
        }

        EntityWrapper<RemainingNumber> bWrapper = new EntityWrapper<RemainingNumber>();
        List<RemainingNumber> remainingNumbers = null;
        //把采集单和生产单的IDS合并并进行查询
        if(resutl!=null && result2!=null){
            resutl.addAll(result2);
            bWrapper.in("business_id", resutl);
            remainingNumbers = remainingNumberService.selectList(bWrapper);
        }else if(resutl!=null && result2 == null){
            bWrapper.in("business_id", resutl);
            remainingNumbers = remainingNumberService.selectList(bWrapper);
        }else if(result2!=null && resutl == null){
            bWrapper.in("business_id", result2);
            remainingNumbers = remainingNumberService.selectList(bWrapper);
        }

        //2018年8月22日 为了通过角色反向更新所有数据的模板变化这里需要记录所有用户，进行便利
        List<RemainingNumber> userIdList = null;
        //userid 只是为了检测用户是否用关联关系
        String userId = null;
        if(remainingNumbers!=null && remainingNumbers.size()>0){
            userId = remainingNumbers.get(0).getUserId();

            //排重userId
          /*  List<String> tempIdList = new ArrayList<String>();
            userIdList =  remainingNumbers.stream().filter(v ->{
                boolean flag = !tempIdList.contains(v.getUserId());
                tempIdList.add(v.getUserId());
                return flag;
            }).collect(Collectors.toList());
            //删除所有相关数据
            this.remainingNumberService.delete(bWrapper);*/
        }

        List<RemainingNumber> remainingNumberList = new LinkedList<RemainingNumber>();
        //第一种情况 中间表没有和用户进行关联
        //根据生产表中的的年限要求，重新计算数据权限的有效期   2018年8月3日
        if(Strings.isNullOrEmpty(userId)){
            if(backProductPermissionList!=null && backProductPermissionList.size()>0){
                String dbTime = ticketsService.getDateNow();
                DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

                backProductPermissionList.stream().forEach(productPermission -> {
                    RemainingNumber temp2 = new RemainingNumber();
                    BeanCopierUtils.copyProperties(productPermission,temp2);
                    //2018年9月10日更改需求，所有的时间以用户的时间为准
                  /*  if(StringUtils.isNotBlank(productPermission.getTimeInfiniteStatus())){
                     *//*   if(productPermission.getTimeInfiniteStatus().equals("1")){
                           *//**//* if(temp2.getOriginTime()!=null){
                                //如果新增就按数据库新增时间
                                temp2.setEffectiveBegtinDate(temp2.getOriginTime());
                                DateTime dateTime = new DateTime(temp2.getOriginTime());
                                if(StringUtils.isNotBlank(productPermission.getEffectiveYear())){
                                    dateTime = dateTime.plusYears(Integer.valueOf(productPermission.getEffectiveYear()));
                                }else{
                                    dateTime =  dateTime.plusYears(Integer.valueOf(0));
                                }
                                if (StringUtils.isNotBlank(productPermission.getEffectiveMouth())) {
                                    dateTime = dateTime.plusMonths(Integer.valueOf(productPermission.getEffectiveMouth()));
                                } else {
                                    dateTime = dateTime.plusMonths(Integer.valueOf(0));
                                }
                                if (StringUtils.isNotBlank(productPermission.getEffectiveDay())) {
                                    dateTime = dateTime.plusDays(Integer.valueOf(productPermission.getEffectiveDay()));
                                } else {
                                    dateTime = dateTime.plusDays(Integer.valueOf(0));
                                }
                                Date d2 =  dateTime.toDate();
                                temp2.setEffectiveEndDate(d2);
                            }else{
                                temp2.setEffectiveBegtinDate(null);
                                temp2.setEffectiveEndDate(null);
                                temp2.setOriginTime(null);
                            }*//**//*
                        }else{
                            temp2.setEffectiveBegtinDate(null);
                            temp2.setEffectiveEndDate(null);
                            temp2.setOriginTime(null);

                        }*//*
                    }*/
                    //
                    temp2.setBusinessId(productPermission.getId());
                    temp2.setGroupId(groupId);
                    temp2.setRemainingNumber(productPermission.getRemainingNumber());
                    temp2.setTotalNumber(productPermission.getTotalOrderNumber());
                    temp2.setType("1");
                    temp2.setStatus("1");
                    temp2.setId("");
                    remainingNumberList.add(temp2);
                });
            }
            if(backPickPerimissions!=null && backPickPerimissions.size()>0){
                backPickPerimissions.stream().forEach(pickPerimission -> {
                    RemainingNumber temp = new RemainingNumber();
                    BeanCopierUtils.copyProperties(pickPerimission,temp);
                    temp.setBusinessId(pickPerimission.getId());
                    temp.setGroupId(groupId);
                    temp.setRemainingNumber(pickPerimission.getPickNumber());
                    temp.setTotalNumber(pickPerimission.getPickNumber());
                    temp.setType("2");
                    temp.setStatus("1");
                    temp.setId("");
                    remainingNumberList.add(temp);
                });
            }
            //如果中间表没有和用户关联，证明是新增，全部插入即可
            //2018年9月10日 以用户的数据权限主题,这里的逻辑进行修改
            // 先全部删除改组下的所有数据,然后插入。
            //
            if(StringUtils.isNotBlank(groupId)){
                EntityWrapper<RemainingNumber> ew = new EntityWrapper<RemainingNumber>();
                ew.eq("group_id", groupId);
                this.remainingNumberService.delete(ew);
            }
           // this.remainingNumberService.insertBatch(remainingNumberList);

        }
        else {
          /*  //便利中间表所有的用户
           // userIdList.stream().forEach(exitUser ->{
                //修改，基于原始时间进行修改,证明该用户已经有数据
                if (backProductPermissionList != null && backProductPermissionList.size() > 0) {
                    String dbTime = ticketsService.getDateNow();
                    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
                    //做个id和原始时间的map,用于获取所有ID对应原始时间
                    EntityWrapper<RemainingNumber> ew = new EntityWrapper<RemainingNumber>();
                    ew.eq("status", "1");

                    List<RemainingNumber>  remainingNumbers1 = remainingNumberService.selectList(ew);
                    Map<String, RemainingNumber> tmpmap = null;
                    if(remainingNumbers1!=null && remainingNumbers1.size()>0){
                        // tmpmap = remainingNumbers1.stream().collect(Collectors.toMap(RemainingNumber::getBusinessId,remainingNumber -> remainingNumber));
                    }

                    Map<String, RemainingNumber> finalTmpmap = tmpmap;

                    backProductPermissionList.stream().forEach(productPermission -> {
                        RemainingNumber temp2 = new RemainingNumber();
                        BeanCopierUtils.copyProperties(productPermission,temp2);

                       *//* if(StringUtils.isNotBlank(productPermission.getTimeInfiniteStatus())){*//*
                           *//* if(productPermission.getTimeInfiniteStatus().equals("1")){
                                //从原始数据中获取开始时间，进行新的有效时间计算
                                Date orgTime = null;

                                //如果中间表有原始时间，就记录原始时间
                             *//**//*   if(exitUser!=null){
                                    if(exitUser.getOriginTime()!=null){
                                        orgTime =  exitUser.getOriginTime();
                                        dateTime = new DateTime(orgTime);
                                    }
                                    else{
                                        //这里处理是为了防止空指针发生
                                        //如果中间表中，没有原始时间，就用当前数据库时间，作为原始时间
                                        dateTime = new DateTime(productPermission.getCrtTime());
                                        orgTime = dateTime.toDate();
                                    }
                                }else{*//**//*
                                    //如果中间表中，没有原始时间，就用当前数据库时间，作为原始时间
                                   // dateTime = new DateTime(productPermission.getCrtTime());
                                   // orgTime = dateTime.toDate();
                                *//**//*}*//**//*

                               *//**//* temp2.setOriginTime(orgTime);*//**//*
                                //temp2.setEffectiveBegtinDate(orgTime);
                                //2018年9月10日 业务发生了变化，原始时间只能是用户那，而不是角色呢
                                //这里又改了需求
                                if(temp2.getOriginTime()!=null){
                                    temp2.setEffectiveBegtinDate(temp2.getOriginTime());
                                    DateTime dateTime = new DateTime(temp2.getOriginTime());
                                    if(StringUtils.isNotBlank(productPermission.getEffectiveYear())){
                                        dateTime =  dateTime.plusYears(Integer.valueOf(productPermission.getEffectiveYear()));
                                    }else{
                                        dateTime = dateTime.plusYears(Integer.valueOf(0));
                                    }
                                    if (StringUtils.isNotBlank(productPermission.getEffectiveMouth())) {
                                        dateTime =dateTime.plusMonths(Integer.valueOf(productPermission.getEffectiveMouth()));
                                    } else {
                                        dateTime =dateTime.plusMonths(Integer.valueOf(0));
                                    }
                                    if (StringUtils.isNotBlank(productPermission.getEffectiveDay())) {
                                        dateTime =dateTime.plusDays(Integer.valueOf(productPermission.getEffectiveDay()));
                                    } else {
                                        dateTime =dateTime.plusDays(Integer.valueOf(0));
                                    }

                                    Date d2 =  dateTime.toDate();
                                    temp2.setEffectiveEndDate(d2);
                                }else {
                                    temp2.setOriginTime(null);
                                    temp2.setEffectiveBegtinDate(null);
                                    temp2.setEffectiveEndDate(null);
                                }
                            }else{
                                temp2.setOriginTime(null);
                                temp2.setEffectiveBegtinDate(null);
                                temp2.setEffectiveEndDate(null);

                            }
                        }*//*

                        temp2.setBusinessId(productPermission.getId());
                       *//* temp2.setUserId(exitUser.getUserId());*//*
                        temp2.setGroupId(groupId);
                        temp2.setRemainingNumber(productPermission.getRemainingNumber());
                        temp2.setTotalNumber(productPermission.getTotalOrderNumber());
                        temp2.setType("1");
                        temp2.setStatus("1");
                        temp2.setId("");
                        remainingNumberList.add(temp2);
                    });
                }
                if(backPickPerimissions != null && backPickPerimissions.size() > 0) {
                    *//*  String finalUserId1 = userId;*//*
                    backPickPerimissions.stream().forEach(pickPerimission -> {
                        RemainingNumber temp = new RemainingNumber();
                        BeanCopierUtils.copyProperties(pickPerimission,temp);
                        temp.setBusinessId(pickPerimission.getId());
                     *//*   temp.setUserId(exitUser.getUserId());*//*
                        temp.setGroupId(groupId);
                        temp.setRemainingNumber(pickPerimission.getPickNumber());
                        temp.setTotalNumber(pickPerimission.getPickNumber());
                        temp.setType("2");
                        temp.setStatus("1");
                        temp.setId("");
                        remainingNumberList.add(temp);
                    });
                }

                //批量更新订单剩余表
                this.remainingNumberService.insertBatch(remainingNumberList);
                remainingNumberList.clear();*/
           // });

        }




    }

    /**
     * 根据传入的原始时间，计算新的开始时间和结束时间
     * type 1 是 新增 2 是修改
     * @return
     */
    public RemainingNumber countTime(Date orgTime,String year,String mouth,String day,RemainingNumber remainingNumber){
        DateTime dateTime = null;
        if(StringUtils.isNotBlank(remainingNumber.getType())){

            //1是生产单
            if(remainingNumber.getType().equals("1")){
                if(StringUtils.isNotBlank(remainingNumber.getTimeInfiniteStatus())){
                    if(remainingNumber.getTimeInfiniteStatus().equals("1")){
                        if(orgTime!=null){
                            remainingNumber.setOriginTime(orgTime);
                            remainingNumber.setEffectiveBegtinDate(orgTime);
                             dateTime = new DateTime(orgTime);
                            if(StringUtils.isNotBlank(year)){
                                dateTime =  dateTime.plusYears(Integer.valueOf(year));
                            }else{
                                dateTime = dateTime.plusYears(Integer.valueOf(0));
                            }
                            if (StringUtils.isNotBlank(mouth)) {
                                dateTime =dateTime.plusMonths(Integer.valueOf(mouth));
                            } else {
                                dateTime =dateTime.plusMonths(Integer.valueOf(0));
                            }
                            if (StringUtils.isNotBlank(day)) {
                                dateTime =dateTime.plusDays(Integer.valueOf(day));
                            } else {
                                dateTime =dateTime.plusDays(Integer.valueOf(0));
                            }

                            Date d2 =  dateTime.toDate();
                            remainingNumber.setEffectiveEndDate(d2);
                        }else {
                            remainingNumber.setOriginTime(null);
                            remainingNumber.setEffectiveBegtinDate(null);
                            remainingNumber.setEffectiveEndDate(null);
                        }
                    }else{
                        remainingNumber.setOriginTime(null);
                        remainingNumber.setEffectiveBegtinDate(null);
                        remainingNumber.setEffectiveEndDate(null);

                    }
                }
            }
            //2 是采集单
            else if(remainingNumber.getType().equals("2")){

            }
        }
        return remainingNumber;
    }

    /**
     *
     * @param username
     * @return
     */
    public List<PerimissionInfo> getApiByUserId(String username){
        BaseUser user = baseUserService.getUserByUserName(username);
        List<BaseElement>  baseElements =  baseElementService.getElementByUserId(user.getId());
        List<PerimissionInfo> perimissionInfos = new ArrayList<PerimissionInfo>();
        element2permission(perimissionInfos, baseElements);
        return perimissionInfos;
    }


}
