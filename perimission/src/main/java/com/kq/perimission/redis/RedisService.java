package com.kq.perimission.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

/*




    */
/**
     * 根据指定key获取String
     * @param key
     * @return
     *//*

    public String getStr(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    */
/**
     * 设置Str缓存
     * @param key
     * @param val
     *//*

    public void setStr(String key, String val){
        stringRedisTemplate.opsForValue().set(key,val);
    }

    */
/**
     * 设置Str缓存
     * @param key
     * @param val
     * @param time  设置失效时间
     *//*

    public void setStr(String key, String val,long time){
        stringRedisTemplate.opsForValue().set(key,val,time, TimeUnit.SECONDS);
    }

    */
/**
     * 删除指定key
     * @param key
     *//*

    public void del(String key){
        stringRedisTemplate.delete(key);
    }
*/

    /**
     * 根据指定o获取Object
     * @param o
     * @return
     */
    public Object getObj(Object o){
        return redisTemplate.opsForValue().get(o);
    }

    /**
     * 设置obj缓存
     * @param o1
     * @param o2
     */
    public void setObj(Object o1, Object o2){
        redisTemplate.opsForValue().set(o1, o2);
    }

    /**
     * 设置obj 缓存，同时设置失效时间
     * @param o1
     * @param o2
     * @param time
     */
    public void setObj(Object o1, Object o2,long time){
        redisTemplate.opsForValue().set(o1, o2,time);
    }

    /**
     * 删除Obj缓存
     * @param o
     */
    public void delObj(Object o){
        redisTemplate.delete(o);
    }

    public RedisTemplate<Object, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public RedisTemplate<String, String> getStrTemplate() {
        return stringRedisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
