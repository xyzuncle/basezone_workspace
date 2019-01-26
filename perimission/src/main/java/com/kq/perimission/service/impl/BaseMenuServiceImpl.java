package com.kq.perimission.service.impl;

import com.ace.cache.annotation.Cache;
import com.kq.common.exception.BaseException;
import com.kq.perimission.constant.AdminCommonConstant;
import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.BaseMenu;
import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.mapper.BaseMenuMapper;
import com.kq.perimission.service.IBaseMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  菜单的实现类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@Service
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, BaseMenu> implements IBaseMenuService {


    @Autowired
    BaseUserServiceImpl baseUserService;

    @Autowired
    BaseGroupServiceImpl baseGroupService;

    @Override
    public List<BaseMenu> getListByPaerntId(String parentId) {
        return this.baseMapper.getListByParentId(parentId);
    }

    @Override
    public List<BaseMenu> getMenuByUserId(String userId) {
        return this.baseMapper.selectAuthorityMenuByUserId(userId);
    }
    @Override
    @Cache(key = "permission:menu")
    public List<BaseMenu> selectListAll(){
        return this.baseMapper.selectList(null);
    }

    @Override
    public List<BaseMenu> getMenusWithParentById(String userId,String parentId) {

        List<BaseMenu> menuList = this.getListByPaerntId(parentId);
        for(BaseMenu baseMenu: menuList){
            List<BaseMenu> menuResulut = this.getMenusWithParentById(userId, baseMenu.getId() + "");
            if(menuResulut!=null && menuResulut.size()>0){
                baseMenu.setHasMenu("true");
                baseMenu.setSubMenu(menuResulut);
            }

        }
        return menuList;
    }

    /**
     * 根据用户ID获取所属组下的全部菜单
     * @param userId
     * @return
     */
    @Override
    public List<BaseMenu> selectMenuByGroupId(String userId) {
        return this.baseMapper.selectMenuByGroupId(userId);
    }

    @Override
    public List<BaseMenu> selectParentMenuByGroupId(String userId, String parentId,String  menuType,String groupId,String groupType) {
        return this.baseMapper.selectParentMenuByGroupId(userId,parentId,menuType,groupId,groupType);
    }

    @Override
    public List<BaseMenu> selectParentMenuByMenuType(String userId, String parentId, String menuType) {
        return this.baseMapper.selectParentMenuByMenuType(userId,parentId,menuType);
    }

    @Override
    public List<BaseMenu> selectAllMenu() {
        return this.baseMapper.selectAllMenu("-1");
    }

    @Override
    public List<BaseMenu> selectAllMenuByType(String menuType) {
        return this.baseMapper.selectAllMenuByType("-1",menuType);
    }

    /**
     * 根据前端vue需要,吐成有顺序和结构的字符串
     * @param userId
     * @param parentId
     * @param result
     * @return
     */
    public Map<String, BaseMenu> getMenus2IdChild(String userId,String parentId,Map<String, BaseMenu> result) {

        List<BaseMenu> menuList = this.getListByPaerntId(parentId);
        for(BaseMenu baseMenu: menuList){
            String parent = baseMenu.getParentId() == null ? "" : baseMenu.getParentId()+"";
            String id = parent + "-" + baseMenu.getId();
            result.put(id,baseMenu);
            this.getMenus2IdChild(userId, baseMenu.getId().toString(),result);
        }
        return result;
    }


    /**
     * 批量保存菜入数据库
     * @param menus
     */
    public void saveMenuByList(List<BaseMenu> menus) {
        boolean retult = this.insertBatch(menus);
    }

    /**
     * 根据登录用户获取对应的前端权根据类型区分的集合
     * @param userName
     * @param type
     */
    @Override
    public List<String> getFoneMenuByUser(String userName, String type) {
        BaseUser baseUser = baseUserService.getUserByUserName(userName);

        if(baseUser.getGroupId()==null || baseUser.getGroupId().equals("")){
            throw new BaseException("用户与角色关系未绑定",500);
        }

        BaseGroup baseGroup = baseGroupService.selectById(baseUser.getGroupId());

        String groupTyp = baseGroup.getGroupType();
        String queryGroupType = "";
        if (StringUtils.isNotBlank(groupTyp)) {
            if (groupTyp.equals("2")) {
                queryGroupType = "outside";
            } else if (groupTyp.equals("1")) {
                queryGroupType = "group";
            }
        }


        List<BaseMenu> baseMenuList = this.baseMapper.selectFontMenuByUser(baseUser.getId(), "-1",
                "fontmenu", baseUser.getGroupId(), queryGroupType);

        List<String> resultList = null;
        if (StringUtils.isNotBlank(type) && !"null".equals(type)) {
            //根据用户找到所属组下的所有菜单
            Map<String, List<BaseMenu>> resultMap =
                    baseMenuList.stream().collect(Collectors.groupingBy(BaseMenu::getAttr1));
            List<BaseMenu> baseMenuList1 = resultMap.get(type);
            if(baseMenuList1!=null && baseMenuList1.size()>0){
                resultList = resultMap.get(type).stream().map(BaseMenu::getId).collect(Collectors.toList());
            }
        }else{
            resultList =  baseMenuList.stream().map(BaseMenu::getId).collect(Collectors.toList());
        }


        return resultList;
    }

    @Override
    public List<String> getFontMenuByGuest(String type) {
        String groupType = AdminCommonConstant.GUEST_GROUP_TYPE;
        String groupId = AdminCommonConstant.GUEST_GROUP_ID;
        List<BaseMenu> guestMenu =  this.baseMapper.selectFontMenuByGuest("","-1","fontmenu",groupId,groupType);
        List<String> resultList = null;

        if (StringUtils.isNotBlank(type) && !type.equals("null")) {
            //根据用户找到所属组下的所有菜单
            Map<String, List<BaseMenu>> resultMap =
                    guestMenu.stream().collect(Collectors.groupingBy(BaseMenu::getAttr1));
            List<BaseMenu> result = resultMap.get(type);
            if(result!=null && result.size()>0){
                resultList = resultMap.get(type).stream().map(BaseMenu::getId).collect(Collectors.toList());
            }

        }else{
            resultList =  guestMenu.stream().map(BaseMenu::getId).collect(Collectors.toList());
        }
        return resultList;
    }


}
