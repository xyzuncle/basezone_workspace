package com.kq.perimission.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yerui
 * @since 2018-07-09
 */
public class Tickets extends Model<Tickets> {

    private static final long serialVersionUID = 1L;

    /**
     * 通过mysql的自增序列维护序列号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 应用ID
     */
    private String stub;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStub() {
        return stub;
    }

    public void setStub(String stub) {
        this.stub = stub;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Tickets{" +
        ", id=" + id +
        ", stub=" + stub +
        "}";
    }
}
