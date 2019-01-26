package com.kq.perimission.controller.rpc.feign;

import com.kq.perimission.config.FeignConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="oauth-server",configuration = FeignConfig.class)
public interface OnlineUserService {
    @RequestMapping(value = "/session/asyncByUsername",method = RequestMethod.GET)
    public void getAllOnlineUser();
}
