package com.kq.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.kq.auth.domain.BaseOnlineUser;
import com.kq.auth.domain.UserTypeDTO;

import java.util.Map;


/**
 * <p>
 * 在线用户列表 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-01-12
 */
public interface IUserTypeService extends IService<UserTypeDTO> {
    public Map<Integer, String> getUserTypeMap();

    public Map<Integer, String> getIndustryType();
}
