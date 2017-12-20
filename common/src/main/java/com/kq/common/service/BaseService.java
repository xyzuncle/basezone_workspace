package com.kq.common.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/baseservice")
public interface BaseService {
    @RequestMapping(value = "/say",method = RequestMethod.GET)
    String say();
}
