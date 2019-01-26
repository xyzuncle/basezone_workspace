package com.kq.perimission.service.impl;

import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.domain.Tickets;
import com.kq.perimission.mapper.TicketsMapper;
import com.kq.perimission.service.ITicketsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kq.perimission.util.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-07-09
 */
@Service
public class TicketsServiceImpl extends ServiceImpl<TicketsMapper, Tickets> implements ITicketsService {

    @Override
    @Transactional
    public String getAppLastId(String appId, BaseGroup baseGroup) {
        this.baseMapper.selectSquence(appId);
        Integer lastId  =   this.baseMapper.selectLastId();
        String tempId = this.fileZero(lastId);
        StringBuffer buffer = new StringBuffer();
        String date = Tools.date2Str(new Date(), "YYYYMMdd");

        if(baseGroup!=null){
            if(baseGroup.getGroupType()!=null && !"".equals(baseGroup.getGroupType())){
                if(baseGroup.getGroupType().equals("1")){
                    buffer.append("N");
                }else if(baseGroup.getGroupType().equals("2")){
                    buffer.append("W");
                }
                buffer.append(date).append(tempId);
            }

        }
        return buffer.toString();
    }


    public String fileZero(Integer num){
        String resultNum = "";
        if(num<9999){
            String tempNum = String.valueOf(num);
            if(tempNum.length()==1){
                resultNum = "000" + tempNum;
            }else if(tempNum.length()==2){
                resultNum = "00" + tempNum;
            }else if(tempNum.length()==3){
                resultNum = "0" + tempNum;
            }else{
                resultNum = tempNum;
            }
        }

        return resultNum;
    }


    public String getDateNow(){
        return  this.baseMapper.getDateNow();
    }


}
