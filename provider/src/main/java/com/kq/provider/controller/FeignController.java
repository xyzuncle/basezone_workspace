package com.kq.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("feign")
public class FeignController {

    @GetMapping("/say2")
    public String say2(){
        return "i'm is say2";
    }

}
