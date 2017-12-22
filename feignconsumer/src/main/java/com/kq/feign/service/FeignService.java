package com.kq.feign.service;

import com.kq.common.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "hello-service",fallback = FaceBackService.class)
public interface FeignService {

    @RequestMapping(value = "/feign/say2",method = RequestMethod.GET)
    public String say2();




}
