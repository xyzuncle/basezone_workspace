package com.kq.auth;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 实体的公共字段填充
 */
public class MyMetaObjectHandler extends MetaObjectHandler {
    protected final static Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("新增的时候干点不可描述的事情");
       Object creatTime = getFieldValByName("crt_Time",metaObject);
       if(creatTime==null){
           setFieldValByName("crtTime", new Date(System.currentTimeMillis()), metaObject);
       }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("更新的时候干点不可描述的事情");
      //  setFieldValByName("upd_time", new DateTime(System.currentTimeMillis()),metaObject);
    }
}
