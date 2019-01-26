package com.kq.perimission.mapper;

import com.kq.perimission.domain.ProductPermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-05-12
 */
public interface ProductPermissionMapper extends BaseMapper<ProductPermission> {

    public boolean deleteByGroupId(@Param("groupId") String groupId);

    public List<ProductPermission> getProductListByGroupId(@Param("groupId") String groupId);
}
