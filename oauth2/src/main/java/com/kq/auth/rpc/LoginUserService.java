package com.kq.auth.rpc;

import com.kq.auth.domain.BaseUser;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.common.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="oauth-server")
public interface LoginUserService {
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public BaseUser getLoginUser(@AuthenticationPrincipal UserDetails user);
}




