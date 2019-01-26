package com.kq.perimission.service.impl;

import com.ace.cache.annotation.Cache;
import com.kq.perimission.domain.BaseElement;
import com.kq.perimission.mapper.BaseElementMapper;
import com.kq.perimission.service.IBaseElementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
@Service("baseElementService")
public class BaseElementServiceImpl extends ServiceImpl<BaseElementMapper, BaseElement> implements IBaseElementService {

    @Override
    @Cache(key = "permission:ele")
    public List<BaseElement> getAllElement() {
        return this.baseMapper.selectList(null);
    }

    @Override
    @Deprecated
    public List<BaseElement> getElementByUser(String userId) {
        return this.baseMapper.selectElementByUserId(userId);
    }

    @Override
    public boolean getAcess(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();
        //拿到client id 下的所有数据

        return true;
    }

    @Override
    public List<BaseElement> getElementByUserId(String userId) {
        return this.baseMapper.getElementByUserId(userId);

    }


}
