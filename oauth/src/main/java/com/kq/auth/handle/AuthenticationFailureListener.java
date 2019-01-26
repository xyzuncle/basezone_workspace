package com.kq.auth.handle;

import com.alibaba.fastjson.JSON;
import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.common.DTO.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * 监听登录失败的行为，做一些处理
 */

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    BaseUserServiceImpl loginFailService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        String userName = authenticationFailureBadCredentialsEvent.getAuthentication().getName();
        BaseUser baseUser = (BaseUser) loginFailService.loadUserByUsername(userName);

        if(baseUser!=null && !"".equals(baseUser)){
            int missNum = baseUser.getMissNum();
            int tempMiss = missNum + 1;
            //根据用户id,修改登录失败此时
            try{
                loginFailService.updateMissNumByFail(baseUser.getId(),tempMiss);
            }catch (BadCredentialsException e){
                //后台汇报失败的凭证错误，后续可以集成别的类，但需要测试
            }

        }else{
            System.out.println("用户无法获取异常");
        }
    }
}
