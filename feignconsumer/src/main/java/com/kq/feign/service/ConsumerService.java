package com.kq.feign.service;

import com.kq.common.service.BaseService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "hello-service",fallback = FaceBackService.class)
public interface ConsumerService extends FeignService {
}
