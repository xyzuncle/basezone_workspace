package com.kq.perimission.service;

import com.kq.perimission.domain.BaseUserServiceEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-03-28
 */
public interface IBaseUserServiceEntityService extends IService<BaseUserServiceEntity> {

    public List<BaseUserServiceEntity> getServiceByUserId(String userId);
}
