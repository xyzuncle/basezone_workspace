package com.kq.perimission.util;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Locale;

/**
 * 用于排序的字段的对象
 */
public class Sort implements Serializable{

    public static final String ASC="ASC";
    public static final String DESC="DESC";

    public Sort() {
    }

    //字段名称
    private String colName;
    private String direction;
    private String[] colNames;


    public Sort(String direction, String colName) {
        this.colName = colName;
        this.direction = direction;
    }

    public Sort(String direction, String[] colNames){
        this.colNames = colNames;
        this.direction = direction;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getColNames() {
        return colNames;
    }

    public void setColNames(String[] colNames) {
        this.colNames = colNames;
    }




    public String getColName() {
        if(this.colName!=null){
            return colName;
        }else if(this.colNames!=null && this.colNames.length>0){
            return StringUtils.join(this.colNames,",");
        }
        return "";
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}
