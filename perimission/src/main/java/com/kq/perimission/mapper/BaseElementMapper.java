package com.kq.perimission.mapper;

import com.kq.perimission.domain.BaseElement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface BaseElementMapper extends BaseMapper<BaseElement> {

    /**
     * 根据用户ID和菜单ID 获取 该页面的元素权限 已经作废
     * @param userId
     * @param menuId
     * @return
     */
    @Deprecated
    public List<BaseElement> selectAuthorityMenuElementByUserId(@Param("userId")String userId, @Param("menuId") String menuId);

    /**
     * 根据用户Id获取当前用户该用户下的所有元素 已经作废
     * @param userId
     * @return
     */
    @Deprecated
    public List<BaseElement> selectElementByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID获取API权限
     * @param userId
     * @return
     */
    public List<BaseElement> getElementByUserId(@Param("userId") String userId);

}
