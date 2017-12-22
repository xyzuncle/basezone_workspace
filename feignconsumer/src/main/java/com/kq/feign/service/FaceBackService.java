package com.kq.feign.service;


import com.kq.common.service.BaseService;
import feign.Feign;
import org.springframework.stereotype.Component;

/**
 * 要实现feign实现的接口
 */
@Component
public class FaceBackService implements FeignService{

    @Override
    public String say2() {
        return "服务未知。。";
    }
}