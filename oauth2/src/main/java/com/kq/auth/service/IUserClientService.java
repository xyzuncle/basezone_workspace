package com.kq.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.kq.auth.domain.UserClient;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-07-28
 */
public interface IUserClientService extends IService<UserClient> {
    public UserClient getClientInfoByUserId(@Param("userId") String userId);
}
