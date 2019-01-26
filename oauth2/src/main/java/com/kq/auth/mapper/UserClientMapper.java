package com.kq.auth.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kq.auth.domain.UserClient;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-07-28
 */
public interface UserClientMapper extends BaseMapper<UserClient> {

    public UserClient getClientInfoByUserId(@Param("userId") String userId);

}
