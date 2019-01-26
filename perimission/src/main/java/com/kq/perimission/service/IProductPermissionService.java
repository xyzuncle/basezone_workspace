package com.kq.perimission.service;

import com.kq.perimission.domain.ProductPermission;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-05-12
 */
public interface IProductPermissionService extends IService<ProductPermission> {

    public boolean deleteByGroupId(@Param("groupId") String groupId);

    public List<ProductPermission> getProductListByGroupId(String groupId);
}
