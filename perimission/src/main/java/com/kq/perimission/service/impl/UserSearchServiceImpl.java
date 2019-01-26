package com.kq.perimission.service.impl;

import com.google.common.base.Strings;
import com.kq.perimission.domain.UserSearch;
import com.kq.perimission.mapper.UserSearchMapper;
import com.kq.perimission.service.IUserSearchService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-07-23
 */
@Service
public class UserSearchServiceImpl extends ServiceImpl<UserSearchMapper, UserSearch> implements IUserSearchService {

    @Override
    @Transactional
    public void updateUserdByUserId(String id) {

        UserSearch result =  this.baseMapper.selectById(id);
        if(result!=null && result.getUsed()!=null){
            Integer tempint = result.getUsed();
            tempint += 1;
            this.baseMapper.updateUserdByUserId(id, tempint);
        }


    }
}
