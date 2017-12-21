package com.kq.provider.controller;

import com.kq.common.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient;
import org.springframework.web.bind.annotation.RestController;

/**idea properties 提示问题
 * 通过feign的好处就是controller不用写requestmapping
 * 真正的调用在common里的requestmapping
 */
@RestController
public class RefactorController implements BaseService {


    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public String say() {
        ServiceInstance instance = this.discoveryClient.getLocalServiceInstance();

       return "hello new world....."+"host:"+instance.getHost()+" port:"+instance.getPort();
    }
}
