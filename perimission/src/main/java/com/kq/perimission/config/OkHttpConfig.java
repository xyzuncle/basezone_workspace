package com.kq.perimission.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * okHttp 配置类
 *
 */
@Configuration
public class OkHttpConfig {

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }
}
