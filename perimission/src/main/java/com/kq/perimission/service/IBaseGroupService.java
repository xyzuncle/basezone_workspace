package com.kq.perimission.service;

import com.kq.perimission.domain.BaseGroup;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface IBaseGroupService extends IService<BaseGroup> {

    /**
     * 插入实体 返回插入后的ID
     * @param baseGroup
     * @return
     */
    public String inserEntity(BaseGroup baseGroup);

    /**
     * 根据原型删除角色，删除角色时同时删除角色所属的逻辑关系
     * @param groupId
     * @return
     */
    public boolean customDeleterGroup(String groupId);


    /**
     * 判断角色姓名是否存在 true 存在 false 不存在
     * @param groupName
     * @return
     */
    public boolean getExistGroupName(String groupName);

}
