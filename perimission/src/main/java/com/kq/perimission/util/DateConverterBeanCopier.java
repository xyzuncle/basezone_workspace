package com.kq.perimission.util;

import org.springframework.cglib.core.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverterBeanCopier implements Converter {
    public Object convert(Object arg1, Class arg0, Object context){
        if (arg1 instanceof String && arg0 != java.lang.String.class) {
            String p = (String) arg1;
            if (p == null || p.trim().length() == 0) {
                return null;
            }
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.parse(p.trim());
            } catch (Exception e) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    return df.parse(p.trim());
                } catch (ParseException ex) {
                    return arg1;
                }
            }
        }/** 输入String ,输出String */
        else if (arg1 instanceof String && arg0 == java.lang.String.class) {
            return arg1;
        }/** 输入Date ,输出String */
        else if (arg1 instanceof java.util.Date) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //因为自己的类型就是date 所以不用处理成日期
                return arg1;
               /* return df.parse(arg1.toString());*/
            } catch (Exception e) {
                return null;
            }
        }/** 输入Date ,输出String */
        else if (arg1 instanceof java.sql.Date) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return arg1;
             /*   return df.parse(arg1.toString());*/
            } catch (Exception e) {
                return null;
            }
        } else if (arg1 instanceof Integer) {
            try {
                return (Integer)arg1;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
