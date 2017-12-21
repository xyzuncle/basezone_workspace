package com.kq.feign.service;

import com.kq.common.service.BaseService;
import org.springframework.stereotype.Component;

/**
 * 这是服务降级的实现方法，在消费端，而不是在服务端
 */
@Component
public class FallBackService implements BaseService{
    @Override
    public String say() {
        return "未知的服务";
    }
}
