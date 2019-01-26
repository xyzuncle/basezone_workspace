package com.kq.auth.service.impl;

import com.kq.auth.constants.CommonConstants;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.mapper.BaseUserMapper;
import com.kq.auth.service.IBaseUserService;
import com.kq.common.exception.UserException.UserInvaildException;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-03-21
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService,UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(@RequestParam String username) throws UsernameNotFoundException {

        BaseUser user = null;
        //因为需求有变动，需要前端配合请求，约定成俗，根据不同的方式来进行登录验证
        if(StringUtils.isNotBlank(username)){
            String[] loginResult = StringUtils.split(username,":");
            if(loginResult[0].equalsIgnoreCase(CommonConstants.LOGIN_MOBILE)){
                user = this.baseMapper.getUserInfoByMobile(loginResult[1]);
            }else if(loginResult[0].equalsIgnoreCase(CommonConstants.LOGIN_USERNAME)){
                user = this.baseMapper.getUserInfoByUserName(loginResult[1]);
            }else if(loginResult[0].equalsIgnoreCase(CommonConstants.LOGIN_EMAIL)){
                user = this.baseMapper.getUserInfoByMobile(loginResult[1]);
            }
        }

        //如果查不到用户，抛出异常
        if(user == null){
            throw new UserInvaildException("用户不存在",500);
        }
        if(user!=null){
                if(StringUtils.isNotBlank(user.getStatus())){
                    //如果该账户被禁用，也无法登陆
                    if(user.getStatus().equals("0")){
                        throw new UserInvaildException("该账户被禁用无法登陆",500);

                    }
                }
        }
        //返回的用户不应该全部信息，只能给局部的。
        return user;
    }

    @Override
    public void updateMissNumByUserName(String userName) {
        this.baseMapper.updateUserMiss(userName);
    }

    @Override
    public void updateMissNumByFail(String id, int missNum) {
        this.baseMapper.updateUserFail(id,missNum);
    }



}
