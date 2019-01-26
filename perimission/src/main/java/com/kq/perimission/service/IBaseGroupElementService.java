package com.kq.perimission.service;

import com.kq.perimission.domain.BaseElement;
import com.kq.perimission.domain.BaseGroupElement;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-09-20
 */
public interface IBaseGroupElementService extends IService<BaseGroupElement> {

    /**
     * 根据用户ID获取用户API权限信息
     * @param userId
     * @return
     */
    public BaseElement getByUserId(String userId);

}
