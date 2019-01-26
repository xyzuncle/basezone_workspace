package com.kq.auth.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kq.auth.domain.BaseUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import javax.websocket.server.PathParam;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-03-21
 */
public interface BaseUserMapper extends BaseMapper<BaseUser> {

    /**
     * 根据username获取用户
     * @param username
     * @return
     */
    public BaseUser getUserInfoByUserName(@Param("username") String username);

    /**
     * 登录成功后,修改错误登录数
     */
    public void updateUserMiss(@Param("username") String username);


    public void updateUserFail(@Param("id") String id, @Param("missNum") int missNum);

    /**
     * 根据手机号获取用户
     * @param mobilePhone
     * @return
     */
    public BaseUser getUserInfoByMobile(@Param("mobilePhone") String mobilePhone);

    /**
     * 根据邮箱获取用户
     * @param email
     * @return
     */
    public BaseUser getUserInfoByEmail(@Param("email") String email);


}
