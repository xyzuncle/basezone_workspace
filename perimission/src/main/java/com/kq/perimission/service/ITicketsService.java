package com.kq.perimission.service;

import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.Tickets;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-07-09
 */
public interface ITicketsService extends IService<Tickets> {

    /**
     * 根据应用id 获取流水ID
     * @param appId
     * @return
     */
    public String getAppLastId(String appId,BaseGroup baseGroup);

}
