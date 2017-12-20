package com.kq.feign;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class FeignApplication {
    public static void main(String[] args) throws Exception {
        (new SpringApplicationBuilder(new Object[]{FeignApplication.class})).web(true).run(args);
    }
}
