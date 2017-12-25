package com.kq.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import java.rmi.activation.ActivationGroup;

@SpringBootApplication
@EnableZuulProxy
public class APIApplication {
    public static void main(String[] args) {
        SpringApplication.run(APIApplication.class, args);
    }
}
