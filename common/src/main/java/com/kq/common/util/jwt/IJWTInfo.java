package com.kq.common.util.jwt;

import com.kq.common.DTO.PerimissionInfo;

import java.util.List;


/**
 * Created by ace on 2017/9/10.
 */
public interface IJWTInfo {
    /**
     * 获取用户名
     * @return
     */
    String getUniqueName();

    /**
     * 获取用户ID
     * @return
     */
    String getId();

    /**
     * 获取名称
     * @return
     */
    String getName();

    /**
     * 获取权限对象
     * @return
     */
    List<PerimissionInfo> getPerimissionList();

    /**
     * 获取用户类型
     * @return
     */
    String getUserType();


}
