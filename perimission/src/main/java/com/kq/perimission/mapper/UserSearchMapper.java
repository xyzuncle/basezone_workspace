package com.kq.perimission.mapper;

import com.kq.perimission.domain.UserSearch;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-07-23
 */
public interface UserSearchMapper extends BaseMapper<UserSearch> {

    /**
     * 根据用户ID,修改该记录被点击的数量
     * @param userId
     * @param num
     */
    public void updateUserdByUserId(@Param("userId") String userId, @Param("num") int num);



}
