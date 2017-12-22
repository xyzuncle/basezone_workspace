package com.kq.feign.service;

import com.kq.common.service.BaseService;
import config.FaceBackService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "hello-service",fallback = FaceBackService.class)
public interface ConsumerService extends BaseService{
}
