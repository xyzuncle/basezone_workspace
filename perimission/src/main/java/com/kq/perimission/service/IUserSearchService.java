package com.kq.perimission.service;

import com.kq.perimission.domain.UserSearch;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-07-23
 */
public interface IUserSearchService extends IService<UserSearch> {

    /**
     * 根据用户ID,修改该记录被点击的数量
     * @param userId
     *
     */
    public void updateUserdByUserId(String userId);

}
