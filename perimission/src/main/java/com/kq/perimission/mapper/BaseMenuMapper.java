package com.kq.perimission.mapper;

import com.kq.perimission.domain.BaseMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface BaseMenuMapper extends BaseMapper<BaseMenu> {

    public List<BaseMenu> getListByParentId(@Param("parentId") String parentId);

    /**
     * 根据用户ID 获取所属组下的 菜单名称,不包含父类
     * 这个方法都是基于元素表为核心表进行关联，关联的resoureid指的是
     * 元素的ID 元素的ID又关联菜单
     * @param userId
     * @return
     */
    public List<BaseMenu> selectAuthorityMenuByUserId(String userId);


    /**
     * 根据用户获取该用户下的所有菜单包括父类菜单，用于组成菜单父子树
     * 这个方法都是基于元素表为核心表进行关联，关联的resoureid指的是
     * 元素的ID 元素的ID又关联菜单
     * @param userId
     * @return
     */
    public List<BaseMenu> queryMenusByUserId(@Param("userId")String userId,@Param("parentId")String parentId);


    /**
     * 为了响应原型设计，特意把按钮权限去掉，简化流程
     * 根据用户原型获取该用户下的所有菜单
     * @param userId
     * @return
     */
    public List<BaseMenu> selectMenuByGroupId(@Param("userId") String userId);


    /**
     * 为了响应原型设计，特意把按钮权限去掉，简化流程
     * 根据用户ID获取该用户下的所属父类ID的菜单
     * 这里的关联关系变成了组，而不是原有的元素表
     * -1 是顶级菜单
     * @param userId
     * @param parentId
     * @return
     */
    public List<BaseMenu> selectParentMenuByGroupId(@Param("userId") String userId,@Param("parentId") String parentId,
                                                    @Param("menuType") String menuType,@Param("groupId") String groupId,
                                                    @Param("groupType") String groupType);


    /**
     * 根据用户ID获取顶级菜单，同时根据menuType来获取是前台菜单还是后台菜单
     * @param userId
     * @param parentId
     * @param menuType
     * @return
     */
    public List<BaseMenu> selectParentMenuByMenuType(@Param("userId") String userId,@Param("parentId") String parentId,
                                                    @Param("menuType") String menuType);

    /**
     *
     * 获取所有的顶级菜单根据菜单类型
     * @param parentId
     * @return
     */
    public List<BaseMenu> selectAllMenu(@Param("parentId") String parentId);


    /**
     * 根据菜单类型获取菜单
     * @param parentId
     * @param menuType
     * @return
     */
    public List<BaseMenu> selectAllMenuByType(@Param("parentId") String parentId,@Param("menuType")String menuType);


    public List<BaseMenu> selectFontMenuByUser(@Param("userId") String userId, @Param("parentId") String parentId,
                                                   @Param("menuType") String menuType, @Param("groupId") String groupId,
                                                   @Param("groupType") String groupType);

    public List<BaseMenu> selectFontMenuByGuest(@Param("userId") String userId,@Param("parentId") String parentId,
                                                @Param("menuType") String menuType, @Param("groupId") String groupId,
                                                @Param("groupType") String groupType);

}
