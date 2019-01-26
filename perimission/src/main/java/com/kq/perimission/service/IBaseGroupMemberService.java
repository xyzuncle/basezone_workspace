package com.kq.perimission.service;

import com.kq.perimission.domain.BaseGroupMember;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface IBaseGroupMemberService extends IService<BaseGroupMember> {

    /**
     * 根据某个字段来删除
     */
    public void deleteByColumn(String columnId);

    /**
     * 根据groupId 获取所属的userId
     * @param groupId
     * @return
     */
    public List<String> getUserIdsByGroupId(String groupId);
}
