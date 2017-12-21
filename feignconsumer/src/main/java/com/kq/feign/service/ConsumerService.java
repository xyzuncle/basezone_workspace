package com.kq.feign.service;

import com.kq.common.service.BaseService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "hello-service",fallback = FallBackService.class)
public interface ConsumerService extends BaseService{
}
