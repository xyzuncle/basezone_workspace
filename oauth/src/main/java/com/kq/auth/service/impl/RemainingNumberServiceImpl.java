package com.kq.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kq.auth.constants.CommonConstants;
import com.kq.auth.domain.RemainingNumber;
import com.kq.auth.mapper.RemainingNumberMapper;
import com.kq.auth.service.IRemainingNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  为了减少服务依赖，所以这里重复了
 *  查询权限的逻辑，
 *  //todo
 * </p>
 *
 * @author yerui
 * @since 2018-07-03
 */
@Service
public class RemainingNumberServiceImpl extends ServiceImpl<RemainingNumberMapper, RemainingNumber> implements IRemainingNumberService {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据约定的数据权限 封装map
     * map的结构依次递推
     * stateId ==== SensorID
     * @param type  1 获取生产的数据权限 2 获取采集单的权限
     * @return
     */
    @Override
    public void putReidsDateRule(String type,String userId){
        //第一层map 用于封装key 是卫星类型的最上层的map
        Map<String,Map<String, Map<String, RemainingNumber>>>
                bigMap = new HashMap<>();

        EntityWrapper<RemainingNumber> ew = new EntityWrapper<RemainingNumber>();
        ew.eq("status", "1"); //获取1状态 指的是数据有效的规则
        ew.eq("type", type);//获取中间表示1 是指获取生产单的数据权限，现在采集权限暂时不做
        ew.eq("user_id", userId);//用户id
       // ew.eq("SATELLITE_TYPE", "stateType");
        ew.like("PRODUCT_LEVLE","0");//只查询零级的权限
        List<RemainingNumber> remainingNumberList = this.baseMapper.selectList(ew);
        //如果该用户有前端查询权限
        if(remainingNumberList!=null && remainingNumberList.size()>0){
            Map<String, List<RemainingNumber>> statedataTypeMap= remainingNumberList.stream().collect(Collectors.groupingBy(RemainingNumber::getSatelliteType));
            Set set =  statedataTypeMap.keySet();
            set.stream().forEach( (key) ->{
                //第二层map 是stateType的map
                Map<String, Map<String,RemainingNumber>> satelliteTypeMap = new HashMap<>();
                List<RemainingNumber> satelliteTypeList = statedataTypeMap.get(key);
                bigMap.put(key.toString(),satelliteTypeMap);

                //获取第三层map 是stateId的map
                Map<String, List<RemainingNumber>>  satelliteIdMap = satelliteTypeList.stream().
                        collect(Collectors.groupingBy(RemainingNumber::getSatelliteId));

                satelliteIdMap.keySet().stream().forEach(key1 ->{
                    Map<String,RemainingNumber> sensorMap = new HashMap<>();
                    satelliteTypeMap.put(key1, sensorMap);

                    RemainingNumber sensorList  = satelliteIdMap.get(key1).get(0);
                    sensorMap.put(sensorList.getSensorId(), sensorList);
                });

            });
            //判断是否重复
            if(!stringRedisTemplate.hasKey(CommonConstants.REDIS_PERIMISSION_PRE+userId)){
                stringRedisTemplate.boundHashOps(CommonConstants.REDIS_PERIMISSION_PRE+userId)
                        .put(userId,JSON.toJSONString(bigMap));
                //很对key进行失效设置
                stringRedisTemplate.expire(CommonConstants.REDIS_PERIMISSION_PRE + userId, 3600, TimeUnit.SECONDS);
            }
        }







    }
}
