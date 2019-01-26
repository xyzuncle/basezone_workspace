package com.kq.perimission.controller.rpc.feign;


import com.kq.perimission.config.FeignConfig;
import com.kq.perimission.domain.BaseUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="oauth-server",configuration = FeignConfig.class)
public interface LoginUser {
    @RequestMapping(value = "/me",method = RequestMethod.GET)
    public String getCurrentUser();
}
