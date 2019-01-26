package com.kq.perimission.service;

import com.kq.perimission.domain.BaseMenu;
import com.baomidou.mybatisplus.service.IService;
import com.kq.perimission.dto.MenuTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface IBaseMenuService extends IService<BaseMenu> {

    public List<BaseMenu> getListByPaerntId(String parentId);

    /**
     * <p>
     *     获取所属组下的用户菜单，不包含父类
     * </p>
     * @param userId
     * @return
     */
    public List<BaseMenu> getMenuByUserId(String userId);

    public List<BaseMenu> selectListAll();

    /**
     * <p>
     *     获取所属组下的用户菜单，包含父类
     * </p>
     * @param userId
     * @param parentId
     * @return
     */
    public List<BaseMenu> getMenusWithParentById(String userId,String parentId);


    /**
     * 根据组ID获取对应用户下的菜单，简化流程，放弃了元素级别的权限
     * @param userId
     * @return
     */
    public List<BaseMenu> selectMenuByGroupId(String userId);


    /**
     * 为了响应原型设计，特意把按钮权限去掉，简化流程
     * 根据用户原型获取该用户下的所属父类ID的菜单
     * -1 是顶级菜单
     * @param userId
     * @param parentId
     * @return
     */
    public List<BaseMenu> selectParentMenuByGroupId(String userId,String parentId,String menuType,String groupId,String groupType);

    /**
     * 根据用户名id，和菜单类型获取用户的顶级菜单
     * @param userId
     * @param parentId
     * @param menuType
     * @return
     */
    public List<BaseMenu> selectParentMenuByMenuType(String userId,String parentId,String menuType);

    /**
     * 根据父类ID 获取全部的顶级菜单
     * @param
     * @return
     */
    public List<BaseMenu> selectAllMenu();


    /**
     * 根据菜单类型获取该类型下的顶级菜单
     * @param menuType
     * @return
     */
    public List<BaseMenu> selectAllMenuByType(String menuType);

    /**
     * 根据登录用户获取前端对应名称
     * @param userName
     * @param type
     * @return
     */
    public  List<String> getFoneMenuByUser(String userName, String type);


    /**
     * 获取前端游客组的前端权限,并约定了类型
     *
     * @return
     */
    public List<String> getFontMenuByGuest(String type);

}
