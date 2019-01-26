package com.kq.auth.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kq.auth.domain.BaseOnlineUser;
import com.kq.auth.domain.InstryTypeDTO;
import com.kq.auth.domain.UserTypeDTO;

import java.util.List;


/**
 * <p>
 * 在线用户列表 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-01-12
 */
public interface UserTypeMapper extends BaseMapper<UserTypeDTO> {

    public List<UserTypeDTO> getUserTypeMap();

    public List<InstryTypeDTO> getIndustryType();

}
