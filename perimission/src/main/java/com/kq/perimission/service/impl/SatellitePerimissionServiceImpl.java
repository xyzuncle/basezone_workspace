package com.kq.perimission.service.impl;

import com.kq.perimission.domain.SatellitePerimission;
import com.kq.perimission.mapper.SatellitePerimissionMapper;
import com.kq.perimission.service.ISatellitePerimissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <p>
 * 卫星查询权限表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-11-22
 */
@Service
public class SatellitePerimissionServiceImpl extends ServiceImpl<SatellitePerimissionMapper, SatellitePerimission> implements ISatellitePerimissionService {

    @Override
    public Map<String, Map<String, Map<String,  SatellitePerimission>>> getSatelliteMap() {

        List<SatellitePerimission> list = this.baseMapper.selectList(null);
        //开始转换map
        Map<String, List<SatellitePerimission>> statedataTypeMap= list.stream().collect(Collectors.groupingBy(SatellitePerimission::getSatelliteType));

        Map<String, Map<String, Map<String, SatellitePerimission>>> resultMap = new HashMap<String, Map<String, Map<String, SatellitePerimission>>>();
        //针对一个卫星类型 一卫星 多传感的数据格式，进行map封装
        Set set =  statedataTypeMap.keySet();
        set.stream().forEach( (key) ->{
            //第二层map 是stateId 卫星ID的map
            Map<String, Map<String,SatellitePerimission>> stateIdMap = new HashMap<String, Map<String,SatellitePerimission>>();

            List<SatellitePerimission> stateTypeList = statedataTypeMap.get(key.toString());
            //放入卫星类型的key
            resultMap.put(key.toString(),stateIdMap);
            //迭代卫星ID的数据
            Map<String, List<SatellitePerimission>> tempstateIdMap = stateTypeList.stream().collect(Collectors.groupingBy(SatellitePerimission::getSatelliteId));
            tempstateIdMap.keySet().stream().forEach(stateidkey -> {
                //第三层map 是senroeId 是传感器ID的map
                Map<String, SatellitePerimission> sensorMap = new HashMap<String,SatellitePerimission>();
                //卫星map放入 传感器map
                stateIdMap.put(stateidkey.toString(), sensorMap);

                //得到对应的卫星数据
                List<SatellitePerimission> sensorIdList = tempstateIdMap.get(stateidkey.toString());

                //通过表达式得到传感数据,有可能是同一个卫星 不同的传感，所以是集合
                Map<String, List<SatellitePerimission>> tempSensorMap = sensorIdList.stream().collect(Collectors.groupingBy(SatellitePerimission::getSensroIds));

                tempSensorMap.keySet().stream().forEach(sensorkey ->{
                    //传感器的map
                    List<SatellitePerimission> productLevleList = tempSensorMap.get(sensorkey.toString());
                    //只会有一个传感器的组合
                    sensorMap.put(sensorkey, productLevleList.get(0));
                });

            });

        });
        return resultMap;

    }

    /**
     * true  标识可以访问  false 标识不可以访问
     * @param satelLiteType
     * @param satelLiteId
     * @param sensorId
     * @return
     */
    @Override
    public boolean getStatllitePermission(String satelLiteType, String satelLiteId, String sensorId) {
        Map<String, Map<String, Map<String,  SatellitePerimission>>> map = this.getSatelliteMap();
        final boolean[] permission = {false};
        map.keySet().stream().filter(key->satelLiteType.equals(key)).forEach(key -> {
            Map<String, Map<String,  SatellitePerimission>> satesMap = map.get(key);
            satesMap.keySet().stream().filter(sateKey -> satelLiteId.equals(sateKey)).forEach(staeKey->{
                Map<String,  SatellitePerimission> sensorMap = satesMap.get(staeKey);
                sensorMap.keySet().stream().filter(sensorkey ->{
                        if(sensorkey.contains(sensorId)){
                            return true;
                        }else {
                            return false;
                        }
                    }).forEach(sensorkey ->{
                        SatellitePerimission sp =  sensorMap.get(sensorkey);
                        List<String> content = Arrays.asList(StringUtils.splitByWholeSeparator(sp.getSensorContent(), "#"));
                        content.forEach(content1 ->{
                            String[] finalresult = StringUtils.split(content1, ";");
                            if(finalresult[0].equals(sensorId)){
                                 if(finalresult[1].equals("1")){
                                     permission[0] =  true;
                                 }else{
                                     permission[0] =  false;
                                 }
                            }
                        });
                     });

                });
            });
        return permission[0];
    }




}
