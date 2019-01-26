package com.kq.perimission.service.impl;

import com.kq.perimission.domain.PickPerimission;
import com.kq.perimission.mapper.PickPerimissionMapper;
import com.kq.perimission.service.IPickPerimissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-05-12
 */
@Service
public class PickPerimissionServiceImpl extends ServiceImpl<PickPerimissionMapper, PickPerimission> implements IPickPerimissionService {

    @Override
    public boolean deleteByGroupId(String groupId) {
        return this.baseMapper.deleteByGroupId(groupId);
    }

    public List<PickPerimission> getPickListByGroupId(String groupId){
        return this.baseMapper.getPickListByGroupId(groupId);
    }

}
