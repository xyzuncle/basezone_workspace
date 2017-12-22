package com.kq.feign.controller;


import com.kq.feign.service.ConsumerService;
import com.kq.feign.service.FaceBackService;
import com.kq.feign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
public class FeignController {

    @Autowired
    FeignService helloServceFeign;

    //注意必须有value
    @RequestMapping(value = "feign-consumer",method = RequestMethod.GET)
    public String helloConsumer(){

        return helloServceFeign.say2();
    }




}
