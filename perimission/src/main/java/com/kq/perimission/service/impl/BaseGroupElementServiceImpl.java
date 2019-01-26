package com.kq.perimission.service.impl;

import com.kq.perimission.domain.BaseElement;
import com.kq.perimission.domain.BaseGroupElement;
import com.kq.perimission.mapper.BaseGroupElementMapper;
import com.kq.perimission.service.IBaseGroupElementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-09-20
 */
@Service
public class BaseGroupElementServiceImpl extends ServiceImpl<BaseGroupElementMapper, BaseGroupElement> implements IBaseGroupElementService {

    @Override
    public BaseElement getByUserId(String userId) {

        return null;
    }
}
