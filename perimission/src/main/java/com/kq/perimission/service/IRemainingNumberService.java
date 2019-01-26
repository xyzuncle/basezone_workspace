package com.kq.perimission.service;

import com.kq.perimission.domain.RemainingNumber;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-07-03
 */
public interface IRemainingNumberService extends IService<RemainingNumber> {


    /**
     *
     * @param groupId  所属组ID
     * @param num  变更组后的订单剩余数量
     */
    public void updateRemaingNumberByGroupId( String groupId,int num);

    /**
     * 根据业务id 更新订单的剩余数量
     * @param bussinessId
     */
    public void updateRemaingNumberBybusId(String bussinessId,int num);

    public Map getDateRule(String type,String userId);

    public void deleteByUserId(String userId);

    public void batchSaveEntity(List<RemainingNumber> remainingNumbers);

    /**
     * 放入redis数据接口
     * @param type
     * @param userId
     * @param level
     */
    public void putReidsDateRule(String type, String userId, String level);
}
