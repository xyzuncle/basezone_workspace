package com.kq.feign.service;

import com.kq.common.domain.User;

public class FeignService {

    public void toSay(){
        User user = new User();
        user.setName("23");
        System.out.println(user.getName());


    }
}
