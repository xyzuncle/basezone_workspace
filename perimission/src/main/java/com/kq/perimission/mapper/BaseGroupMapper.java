package com.kq.perimission.mapper;

import com.kq.perimission.domain.BaseGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface BaseGroupMapper extends BaseMapper<BaseGroup> {

    /**
     * 插入对象，目的是为了返回ID
     * @return
     */
    public String inserEntity(BaseGroup baseGroup);


    public int getExistGroup(@Param("groupName") String groupName);


}
