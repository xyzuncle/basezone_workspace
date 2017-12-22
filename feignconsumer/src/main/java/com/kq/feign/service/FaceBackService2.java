package com.kq.feign.service;


import org.springframework.stereotype.Component;

/**
 * 要实现feign实现的接口
 */
@Component
public class FaceBackService2 implements FeignService{

    @Override
    public String say2() {
        return "我是服务未知2。。";
    }
}
