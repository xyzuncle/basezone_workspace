package com.kq.perimission.mapper;

import com.kq.perimission.domain.OauthClientDetails;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-07-31
 */
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

    public OauthClientDetails getInfoByUserId(@Param("userId") String userId);

}
