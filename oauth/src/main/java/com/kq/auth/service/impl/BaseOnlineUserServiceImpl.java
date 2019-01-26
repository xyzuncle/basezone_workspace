package com.kq.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.kq.auth.domain.BaseOnlineUser;
import com.kq.auth.mapper.BaseOnlineUserMapper;
import com.kq.auth.service.IBaseOnlineUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
