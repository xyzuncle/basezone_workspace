package com.kq.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.kq.auth.domain.BaseUser;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-03-21
 */
public interface IBaseUserService extends IService<BaseUser> {


    public void updateMissNumByUserName(String userName);

    /**
     * 根据登录失败行为，记录错误次数
     * @param id
     * @param missNum
     */
    public void updateMissNumByFail(String id, int missNum);

}
