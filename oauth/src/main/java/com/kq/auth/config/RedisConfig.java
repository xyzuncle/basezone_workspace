/*
package com.kq.auth.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.net.UnknownHostException;

@Configuration
public class RedisConfig {

    */
/**
     * 修改redisTemplae的序列化方式，目的在于更好的观察
     * redis 在client下的返回结果
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     *//*


    @Bean
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);

        */
/*Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);*//*


        template.setValueSerializer(new JdkSerializationRedisSerializer()); //1
        template.setKeySerializer(new StringRedisSerializer()); //2

        template.afterPropertiesSet();
        return template;
    }

}
*/
