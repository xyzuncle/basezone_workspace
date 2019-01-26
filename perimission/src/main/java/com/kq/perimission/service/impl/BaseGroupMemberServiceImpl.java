package com.kq.perimission.service.impl;

import com.kq.perimission.domain.BaseGroupMember;
import com.kq.perimission.mapper.BaseGroupMemberMapper;
import com.kq.perimission.service.IBaseGroupMemberService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *   用户组成员实现类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@Service
public class BaseGroupMemberServiceImpl extends ServiceImpl<BaseGroupMemberMapper, BaseGroupMember> implements IBaseGroupMemberService {

    @Override
    public void deleteByColumn(String columnId) {
        this.baseMapper.deleteByColumn(columnId);
    }

    @Override
    public List<String> getUserIdsByGroupId(String groupId) {
        return this.baseMapper.queryUserIdsByGroupId(groupId);
    }
}
