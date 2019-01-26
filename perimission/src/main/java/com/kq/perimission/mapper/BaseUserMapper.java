package com.kq.perimission.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kq.perimission.domain.BaseUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-03-21
 */
public interface BaseUserMapper extends BaseMapper<BaseUser> {

    public BaseUser getUserInfoByUserName(@Param("userName") String userName);

    public int getExistUserInfo(@Param("mobileNum") String mobileNum);

    /**
     * 根据全文检索的关键字
     * @param
     * @return
     */
    public List<BaseUser> getFullTextUser(@Param("ew") Wrapper<BaseUser> wrapper);


    public int getExistEmail(@Param("email") String email);




    public void resetPwdByEmail(@Param("email") String email, @Param("pwd") String pwd);


}
