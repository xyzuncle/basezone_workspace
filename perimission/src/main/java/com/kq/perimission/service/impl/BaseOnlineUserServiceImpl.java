package com.kq.perimission.service.impl;

import com.kq.perimission.domain.BaseOnlineUser;
import com.kq.perimission.mapper.BaseOnlineUserMapper;
import com.kq.perimission.service.IBaseOnlineUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 在线用户列表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-01-12
 */
@Service
public class BaseOnlineUserServiceImpl extends ServiceImpl<BaseOnlineUserMapper, BaseOnlineUser> implements IBaseOnlineUserService {

}
