package com.kq.perimission.scheduled;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.base.Strings;
import com.kq.perimission.domain.RemainingNumber;
import com.kq.perimission.service.impl.RemainingNumberServiceImpl;
import com.kq.perimission.service.impl.TicketsServiceImpl;
import com.kq.perimission.util.DateUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xyz on 2017/7/20.
 * 定时器 用户批量处理数据权限不符合规则的业务
 */

@Component
public class ScheduledTasks {
    @Autowired
    TicketsServiceImpl ticketsService;

    @Autowired
    RemainingNumberServiceImpl remainingNumberService;
    
    //每天晚上23点半更新数据中间表
    @Scheduled(cron = "0 30 23 * * ?")
    public void sendMessageToClient() {
      /*  DateUtil.isinDate();*/
        String nowString = ticketsService.getDateNow();
        Date now = DateUtil.fomatDate(nowString);

        EntityWrapper<RemainingNumber> ew = new EntityWrapper<RemainingNumber>();
        ew.eq("status", "1");

        List<RemainingNumber> remainingNumberList = this.remainingNumberService.selectList(ew);
        List<String> ids = new ArrayList<String>();
        remainingNumberList.stream().forEach(remainingNumber -> {
            if(!Strings.isNullOrEmpty(remainingNumber.getType())){
                //1生产单、2 采集单
                if(remainingNumber.getType().equals("1")){
                    if(!Strings.isNullOrEmpty(remainingNumber.getStatus())){
                        if(remainingNumber.getStatus().equals("1")){
                            if(remainingNumber.getEffectiveBegtinDate()!=null && remainingNumber.getEffectiveEndDate()!=null){
                                boolean result =   DateUtil.isinDateByJODA(now,remainingNumber.getEffectiveBegtinDate(), remainingNumber.getEffectiveEndDate(),"yyyy-MM-dd");
                                boolean result2 = DateUtil.getFutureTimeFlag(nowString, remainingNumber.getEffectiveBegtinDate());
                                if(result==false && result2==false){
                                    ids.add(remainingNumber.getId());
                                }
                            }
                        }
                    }
                    //解决订单无限，初始化剩余数量导致的订单无限，时间无限的订单权限无效问题
                    if(remainingNumber.getRemainingNumber()<=0 && remainingNumber.getRemainingNumber()!=-1){
                        ids.add(remainingNumber.getId());
                    }
                }else if(remainingNumber.getType().equals("2")){
                    //采集单由于需求不明确暂不处理 2018年7月12日16:31:44
                }
            }

        });

        //得到ID 批量更新中间表
        List<RemainingNumber> list = new ArrayList<RemainingNumber>();
        ids.stream().forEach(id->{
            RemainingNumber rn = new RemainingNumber();
            rn.setId(id);
            rn.setStatus("0");
            list.add(rn);
        });
        remainingNumberService.updateBatchById(list);

    }
    


}
