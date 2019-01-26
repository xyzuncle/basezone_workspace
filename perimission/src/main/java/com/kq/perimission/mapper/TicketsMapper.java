package com.kq.perimission.mapper;

import com.kq.perimission.domain.Tickets;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  利用数据库的自增特性获取业务流水号
 * </p>
 *
 * @author yerui
 * @since 2018-07-09
 */
public interface TicketsMapper extends BaseMapper<Tickets> {

    /**
     * 根据应用获取应用流水ID
     * @param appId
     */
    public void selectSquence(@Param("appId") String appId);

    /**
     * 获取最后的插入ID
     * @return
     */
    public Integer selectLastId();

    /**
     * 获取数据库时间
     * @return
     */
    public String getDateNow();


}
