package com.kq.perimission.service.impl;

import com.kq.perimission.domain.ProductPermission;
import com.kq.perimission.mapper.ProductPermissionMapper;
import com.kq.perimission.service.IProductPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-05-12
 */
@Service
public class ProductPermissionServiceImpl extends ServiceImpl<ProductPermissionMapper, ProductPermission> implements IProductPermissionService {

    @Override
    public boolean deleteByGroupId(String groupId) {
        boolean result = this.baseMapper.deleteByGroupId(groupId);
        return result;
    }

    @Override
    public List<ProductPermission> getProductListByGroupId(String groupId) {
        return this.baseMapper.getProductListByGroupId(groupId);

    }
}
