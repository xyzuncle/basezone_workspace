package com.kq.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/demo")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getDemo(){
        return "good";
    }

    @GetMapping("/demo2")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getDemo2(){
        return "good2";
    }

    @GetMapping("/demo3")
    public String getDemo3(){
        return "good3";
    }
}
