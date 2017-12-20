package com.kq.provider;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args)
    {
        new SpringApplicationBuilder(new Object[] { ProviderApplication.class }).web(true).run(args);
    }
}
