package com.kq.perimission.mapper;

import com.kq.perimission.domain.BaseGroupMember;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface BaseGroupMemberMapper extends BaseMapper<BaseGroupMember> {

    public void deleteByColumn(@Param("column") String column);

    public List<String> queryUserIdsByGroupId(@Param("groupId")  String groupId);
}
