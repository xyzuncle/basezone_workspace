package com.kq.perimission.service;

import com.kq.perimission.domain.OauthClientDetails;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-07-31
 */
public interface IOauthClientDetailsService extends IService<OauthClientDetails> {

    public OauthClientDetails getInfoByUserName(String userName);

}
