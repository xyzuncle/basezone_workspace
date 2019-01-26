package com.kq.perimission.mapper;

import com.kq.perimission.domain.BaseResourceAuthority;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface BaseResourceAuthorityMapper extends BaseMapper<BaseResourceAuthority> {


    /**
     * 根据groupID 删除前端和后端所属的菜单
     */
    public boolean deleteMenuByGroupId(@Param("groupId") String groupId);

}
