package com.kq.provider.controller;

import com.kq.common.service.BaseService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过feign的好处就是controller不用写requestmapping
 * 真正的调用在common里的requestmapping
 */
@RestController
public class RefactorController implements BaseService {
    @Override
    public String say() {
       return "hello new world.....";
    }
}
