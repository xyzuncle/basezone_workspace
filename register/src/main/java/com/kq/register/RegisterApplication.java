package com.kq.register;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterApplication {
    public static void main(String[] args) {
        (new SpringApplicationBuilder(new Object[]{RegisterApplication.class})).web(true).run(args);
    }
}
