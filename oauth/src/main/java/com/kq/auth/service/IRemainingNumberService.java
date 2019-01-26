package com.kq.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.kq.auth.domain.RemainingNumber;


import java.util.List;
import java.util.Map;

/**
 * <p>
 *  根据用户的数据权限
 *  来限制前端登录用户的查询行为
 * </p>
 *
 * @author yerui
 * @since 2018-07-03
 */
public interface IRemainingNumberService extends IService<RemainingNumber> {
    public void putReidsDateRule(String type, String userId);

}
