package com.kq.perimission.service;

import com.kq.perimission.domain.PickPerimission;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-05-12
 */
public interface IPickPerimissionService extends IService<PickPerimission> {
    public boolean deleteByGroupId(@Param("groupId") String groupId);
}
