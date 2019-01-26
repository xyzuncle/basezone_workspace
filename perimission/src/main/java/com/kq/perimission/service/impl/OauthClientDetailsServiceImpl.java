package com.kq.perimission.service.impl;

import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.domain.OauthClientDetails;
import com.kq.perimission.mapper.OauthClientDetailsMapper;
import com.kq.perimission.service.IOauthClientDetailsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-07-31
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {


    @Autowired
    BaseUserServiceImpl baseUserService;

    @Override
    @Transactional
    public OauthClientDetails getInfoByUserName(String userName) {
        BaseUser user = baseUserService.getUserByUserName(userName);
        OauthClientDetails oauthClientDetails =  this.baseMapper.getInfoByUserId(user.getId());
        return oauthClientDetails;
    }
}
