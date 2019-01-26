package com.kq.auth;


import com.kq.auth.validate.code.servlet.CodeServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class Outh2Application {

    /**
     * 初始化验证码的servlet和处理路径
     * todo 后去需要处理根据属性可以配置话的
     * @return
     */
    @Bean
    public ServletRegistrationBean MyServlet1(){
        return new ServletRegistrationBean(new CodeServlet(),"/images/captcha");
    }

    public static void main(String[] args) {
        SpringApplication.run(Outh2Application.class, args);
    }
}
