package com.kq.auth.handle;

import com.kq.auth.domain.BaseUser;
import com.kq.auth.service.impl.BaseUserServiceImpl;
import com.kq.common.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class LoginOutHandler implements LogoutHandler {

    private static final String PREFIX_EXPIRES_KEYS = "spring:session:sessions:expires:";

    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessionRepository;
    @Autowired
    StringRedisTemplate template;

    @Autowired
    BaseUserServiceImpl baseUserService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //通过上下文获取用户,如果是未退出的状态
        //session退出的时候，会清楚无用的数据，在baseuser中也处理无用的数据。
        if(authentication!=null){
            String username = authentication.getName();

            //Map<String, ? extends Session> usersSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);

            BaseUser baseUser = (BaseUser) baseUserService.loadUserByUsername(username);
            //退出登录，删除该用户的查询权限
            template.delete(CommonConstants.REDIS_PERIMISSION_PRE+baseUser.getId());
            //刪除緩存的用戶信息
            template.delete(username);

        }




    }
}
