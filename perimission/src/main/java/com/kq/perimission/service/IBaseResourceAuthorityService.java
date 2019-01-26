package com.kq.perimission.service;

import com.kq.perimission.domain.BaseResourceAuthority;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface IBaseResourceAuthorityService extends IService<BaseResourceAuthority> {

    public boolean deleteMenuByGroupId(String groupId);

}
