package com.kq.perimission.mapper;

import com.kq.perimission.domain.RemainingNumber;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-07-03
 */
public interface RemainingNumberMapper extends BaseMapper<RemainingNumber> {

    /**
     *
     * @param groupId  所属组ID
     * @param num  变更组后的订单剩余数量
     */
    public void updateRemaingNumberByGroupId(@Param("groupId") String groupId,@Param("num") int num);

    /**
     * 根据业务id 更新订单的剩余数量
     * @param bussinessId
     */
    public void updateRemaingNumberBybusId(@Param("bussinessId") String bussinessId,@Param("num") int num);


    public void deleteByUserId(@Param("userId") String userId);


    public void updateSatusByBusId(@Param("busId") String budId);









}
