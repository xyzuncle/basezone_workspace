package com.kq.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.kq.auth.domain.BaseOnlineUser;
import com.kq.auth.domain.InstryTypeDTO;
import com.kq.auth.domain.UserTypeDTO;
import com.kq.auth.mapper.BaseOnlineUserMapper;
import com.kq.auth.mapper.UserTypeMapper;
import com.kq.auth.service.IBaseOnlineUserService;
import com.kq.auth.service.IUserTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 在线用户列表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-01-12
 */
@Service
public class UserTypeServiceImpl extends ServiceImpl<UserTypeMapper, UserTypeDTO> implements IUserTypeService {

    @Override
    public Map<Integer,String> getUserTypeMap() {
        List<UserTypeDTO> typeDTOList =  this.baseMapper.getUserTypeMap();
        Map<Integer,String> typeMap = typeDTOList.stream().collect(Collectors.toMap(UserTypeDTO::getId, UserTypeDTO::getUserTypes));
        return typeMap;
    }

    @Override
    public Map<Integer,String> getIndustryType() {

        List<InstryTypeDTO>  industryTypeList =   this.baseMapper.getIndustryType();
        Map<Integer,String> typeMap = industryTypeList.stream().collect(Collectors.toMap(InstryTypeDTO::getCode, InstryTypeDTO::getType));
        return typeMap;
    }
}
