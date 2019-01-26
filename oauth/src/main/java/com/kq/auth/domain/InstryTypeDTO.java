package com.kq.auth.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户类型的包装类
 */

@TableName(value = "sys_dictionaries")
public class InstryTypeDTO {

    @TableField("name")
    public String type;
    @TableField("code")
    public Integer code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
