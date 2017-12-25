package com.kq.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("feign")
@RestController
public class FeignController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/say2")
    public String say2(){
        ServiceInstance instance = this.discoveryClient.getLocalServiceInstance();
        return "i'm is say2"+"host:"+instance.getHost()+" port:"+instance.getPort();
    }

}
