package com.kq.perimission.mapper;

import com.kq.perimission.domain.BaseUserServiceEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-03-28
 */
public interface BaseUserServiceMapper extends BaseMapper<BaseUserServiceEntity> {
    /**
     * 根据用户ID获取该用户的下的所有能够访问的服务
     * @param userId
     * @return
     */
    List<BaseUserServiceEntity> getServiceByUserId(@Param("userId") String userId);
}
