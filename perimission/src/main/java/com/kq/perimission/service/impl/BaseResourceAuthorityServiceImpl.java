package com.kq.perimission.service.impl;

import com.kq.perimission.domain.BaseResourceAuthority;
import com.kq.perimission.mapper.BaseResourceAuthorityMapper;
import com.kq.perimission.service.IBaseResourceAuthorityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@Service
public class BaseResourceAuthorityServiceImpl extends ServiceImpl<BaseResourceAuthorityMapper, BaseResourceAuthority> implements IBaseResourceAuthorityService {

    @Override
    public boolean deleteMenuByGroupId(String groupId) {
       return this.baseMapper.deleteMenuByGroupId(groupId);
    }
}
