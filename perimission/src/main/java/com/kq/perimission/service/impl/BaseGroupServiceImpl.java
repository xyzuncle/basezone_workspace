package com.kq.perimission.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kq.perimission.controller.rpc.service.PerimissionService;
import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.mapper.BaseGroupMapper;
import com.kq.perimission.service.IBaseGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  用户组实现类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@Service
public class BaseGroupServiceImpl extends ServiceImpl<BaseGroupMapper, BaseGroup> implements IBaseGroupService {


    @Autowired
    private ProductPermissionServiceImpl productPermissionService;

    @Autowired
    private PickPerimissionServiceImpl pickPerimissionService;

    @Autowired
    private BaseResourceAuthorityServiceImpl baseResourceAuthorityService;

    @Autowired
    private BaseGroupMemberServiceImpl baseGroupMemberService;

    @Autowired
    private PerimissionService perimissionService;

    @Override
    public String inserEntity(BaseGroup baseGroup) {
        return this.baseMapper.inserEntity(baseGroup);
    }

    @Override
    @Transactional
    public boolean customDeleterGroup(String groupId) {




        boolean result =  this.deleteById(groupId);
        baseResourceAuthorityService.deleteMenuByGroupId(groupId);
        productPermissionService.deleteByGroupId(groupId);
        pickPerimissionService.deleteByGroupId(groupId);
        baseGroupMemberService.deleteByColumn(groupId);
        //删除角色后，改角色对应的用户的权限，将变成默认个人用户
        //周旋需求，用户角色绑定用户了，不允许删除
        //2018年9月5日
       /* if(StringUtils.isNotBlank(groupId)) {

            if(baseUserList!=null && baseUserList.size()>0){
                List<BaseUser> existUser = new LinkedList<BaseUser>();
                baseUserList.stream().forEach(baseUser -> {
                    baseUser.setGroupId("3d50ad2c38bc43caa147f0591202264f");
                    baseUser.setGroupName("外部个人默认角色");
                    existUser.add(baseUser);
                });
                //批量更新外部个人默认角色
                baseUserService.insertOrUpdateBatch(existUser);
                //同时循环修改中间表的关系
                existUser.stream().forEach(ebaseUser -> {
                    perimissionService.updateNumByUserGroupID(ebaseUser.getId(),ebaseUser.getGroupId());
                });

            }
        }*/




        return result;
    }

    @Override
    public boolean getExistGroupName(String groupName) {
        int reuslt = this.baseMapper.getExistGroup(groupName);
        if(reuslt>0){
            return true;
        }else{
            return false;
        }
    }
}
