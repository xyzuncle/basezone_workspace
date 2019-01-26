package com.kq.perimission.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kq.common.constants.CommonConstants;
import com.kq.perimission.domain.RemainingNumber;
import com.kq.perimission.mapper.RemainingNumberMapper;
import com.kq.perimission.service.IRemainingNumberService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-07-03
 */
@Service
public class RemainingNumberServiceImpl extends ServiceImpl<RemainingNumberMapper, RemainingNumber> implements IRemainingNumberService {

    @Override
    public void updateRemaingNumberByGroupId(String groupId, int num) {
        this.baseMapper.updateRemaingNumberByGroupId(groupId,num);
    }

    @Override
    @Transactional
    public void updateRemaingNumberBybusId(String bussinessId, int num) {
        this.baseMapper.updateRemaingNumberBybusId(bussinessId, num);
        if(num==0){
            this.baseMapper.updateSatusByBusId(bussinessId);
        }
    }

    public void batchInsertNum(List<RemainingNumber> remainingNumbers){
        this.insertBatch(remainingNumbers);
    }

    @Override
    public void deleteByUserId(String userId) {
        this.baseMapper.deleteByUserId(userId);

    }

    @Override
    public void batchSaveEntity(List<RemainingNumber> remainingNumbers) {
        this.insertOrUpdateBatch(remainingNumbers);
    }


    /**
     * 根据约定的数据权限 封装map
     * map的结构依次递推
     * stateType ===  stateId ==== SensorID ====product_levle
     * @param type  1 获取生产的数据权限 2 获取采集单的权限
     * @return
     */
    @Override
    public Map getDateRule(String type,String userId){
        //第一层map 用于封装key 是卫星类型的最上层的map
        Map<String, Map<String, Map<String, Map<String, List<RemainingNumber>>>>>
                bigMap = new HashMap<String, Map<String, Map<String, Map<String, List<RemainingNumber>>>>>();

        EntityWrapper<RemainingNumber> ew = new EntityWrapper<RemainingNumber>();
        ew.eq("status", "1"); //获取1状态 指的是数据有效的规则
        ew.eq("type", type);//获取中间表示1 是指获取生产单的数据权限，现在采集权限暂时不做
        ew.eq("user_id", userId);
        List<RemainingNumber> remainingNumberList = this.baseMapper.selectList(ew);

        Map<String, List<RemainingNumber>> statedataTypeMap= remainingNumberList.stream().collect(Collectors.groupingBy(RemainingNumber::getSatelliteType));



        Set set =  statedataTypeMap.keySet();
        set.stream().forEach( (key) ->{
            //第二层map 是stateId 卫星ID的map
            Map<String, Map<String, Map<String, List<RemainingNumber>>>> stateIdMap = new HashMap<String, Map<String, Map<String, List<RemainingNumber>>>>();

            List<RemainingNumber> stateTypeList = statedataTypeMap.get(key.toString());
            bigMap.put( key.toString(), stateIdMap);
            Map<String, List<RemainingNumber>> tempstateIdMap = stateTypeList.stream().collect(Collectors.groupingBy(RemainingNumber::getSatelliteId));
            tempstateIdMap.keySet().stream().forEach(stateidkey -> {
                //第三层map 是senroeId 是传感器ID的map
                Map<String, Map<String, List<RemainingNumber>>> sensorMap = new HashMap<String, Map<String, List<RemainingNumber>>>();
                //第四层map 是productLevle  对应产品级别所对应的数据
                final Map<String, List<RemainingNumber>> productMap = new HashMap<String, List<RemainingNumber>>();
                stateIdMap.put(stateidkey.toString(), sensorMap);
                List<RemainingNumber> sensorIdList = tempstateIdMap.get(stateidkey.toString());
                Map<String, List<RemainingNumber>> tempSensorMap = sensorIdList.stream().collect(Collectors.groupingBy(RemainingNumber::getSensorId));

                tempSensorMap.keySet().stream().forEach(sensorkey ->{
                    sensorMap.put(sensorkey, productMap);
                    //产品级别的集合
                    List<RemainingNumber> productLevleList = tempSensorMap.get(sensorkey.toString());

                    Map<String, List<RemainingNumber>> tempProdect =   productLevleList.stream().collect(Collectors.groupingBy(RemainingNumber::getProductLevle));
                    //赋值最低级别的集合
                    tempProdect.forEach((k,v) ->{
                        productMap.put(k, v);
                    });

                });

            });

        });
        return bigMap;
    }


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
    public void putReidsDateRule(String type,String userId,String level) {
        //第一层map 用于封装key 是卫星类型的最上层的map
        Map<String, Map<String, Map<String, RemainingNumber>>>
                bigMap = new HashMap<>();

        EntityWrapper<RemainingNumber> ew = new EntityWrapper<RemainingNumber>();
        ew.eq("status", "1"); //获取1状态 指的是数据有效的规则
        ew.eq("type", type);//获取中间表示1 是指获取生产单的数据权限，现在采集权限暂时不做
        ew.eq("user_id", userId);//用户id
        // ew.eq("SATELLITE_TYPE", "stateType");
        ew.like("PRODUCT_LEVLE", level);//只查询零级的权限
        List<RemainingNumber> remainingNumberList = this.baseMapper.selectList(ew);
        //如果该用户有前端查询权限
        if (remainingNumberList != null && remainingNumberList.size() > 0) {
            if(!stringRedisTemplate.hasKey(CommonConstants.REDIS_PERIMISSION_PRE + userId)){
                Map<String, List<RemainingNumber>> statedataTypeMap = remainingNumberList.stream().collect(Collectors.groupingBy(RemainingNumber::getSatelliteType));
                Set set = statedataTypeMap.keySet();
                set.stream().forEach((key) -> {
                    //第二层map 是stateType的map
                    Map<String, Map<String, RemainingNumber>> satelliteTypeMap = new HashMap<>();
                    List<RemainingNumber> satelliteTypeList = statedataTypeMap.get(key);
                    bigMap.put(key.toString(), satelliteTypeMap);

                    //获取第三层map 是stateId的map
                    Map<String, List<RemainingNumber>> satelliteIdMap = satelliteTypeList.stream().
                            collect(Collectors.groupingBy(RemainingNumber::getSatelliteId));

                    satelliteIdMap.keySet().stream().forEach(key1 -> {
                        Map<String, RemainingNumber> sensorMap = new HashMap<>();
                        satelliteTypeMap.put(key1, sensorMap);

                        RemainingNumber sensorList = satelliteIdMap.get(key1).get(0);
                        sensorMap.put(sensorList.getSensorId(), sensorList);
                    });

                });

                if(bigMap!=null && bigMap.size()>0){
                    //把封装的结果放入redis
                    stringRedisTemplate.boundHashOps(CommonConstants.REDIS_PERIMISSION_PRE + userId)
                            .put(userId, JSON.toJSONString(bigMap));
                    //很对key进行失效设置
                    stringRedisTemplate.expire(CommonConstants.REDIS_PERIMISSION_PRE + userId, 3600, TimeUnit.SECONDS);
                }
            }

        }
    }

}
