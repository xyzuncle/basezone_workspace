package com.kq.perimission.service.impl;

import com.kq.perimission.domain.BaseUserServiceEntity;
import com.kq.perimission.mapper.BaseUserServiceMapper;
import com.kq.perimission.service.IBaseUserServiceEntityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  用户服务关系
 * </p>
 *
 * @author yerui
 * @since 2018-03-28
 */
@Service
public class BaseUserServiceEntityServiceImpl extends ServiceImpl<BaseUserServiceMapper, BaseUserServiceEntity> implements IBaseUserServiceEntityService {

    /**
     * 根据用户id获取该用户下的服务
     * @param userId
     * @return
     */
    @Override
    public List<BaseUserServiceEntity> getServiceByUserId(String userId) {
        return this.baseMapper.getServiceByUserId(userId);
    }
}
