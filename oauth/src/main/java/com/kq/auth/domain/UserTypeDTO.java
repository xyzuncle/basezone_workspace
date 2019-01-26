package com.kq.auth.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户类型的包装类
 */

@TableName(value = "ZYWX_USER_TYPE_MANAGE")
public class UserTypeDTO {

    @TableField("user_types")
    public String userTypes;
    @TableField("id")
    public Integer id;

    public String getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(String userTypes) {
        this.userTypes = userTypes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
