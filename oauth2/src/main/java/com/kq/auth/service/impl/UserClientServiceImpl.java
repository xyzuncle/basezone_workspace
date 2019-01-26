package com.kq.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kq.auth.domain.UserClient;
import com.kq.auth.mapper.UserClientMapper;
import com.kq.auth.service.IUserClientService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-07-28
 */
@Service
public class UserClientServiceImpl extends ServiceImpl<UserClientMapper, UserClient> implements IUserClientService {

    @Override
    public UserClient getClientInfoByUserId(String userId) {
        return this.baseMapper.getClientInfoByUserId(userId);
    }
}
