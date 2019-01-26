package com.kq.perimission.service;

import com.kq.perimission.domain.SatellitePerimission;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 卫星查询权限表 服务类
 * </p>
 *
 * @author yerui
 * @since 2018-11-22
 */
public interface ISatellitePerimissionService extends IService<SatellitePerimission> {
    /**
     * 获取卫星查询权限表封装出的规则类
     * @return
     */
    Map<String,Map<String, Map<String,SatellitePerimission>>> getSatelliteMap();


    boolean getStatllitePermission(String satelLiteType,String satelLiteId,String sensorId);

}
