package com.kq.perimission.dto;

import com.kq.perimission.domain.BaseOnlineUser;

import java.util.List;

/**
 * @Description: 该DTO是为了适配前端的统计的数据结构，专门做的结构
 * @Author: yerui
 * @CreateDate : 2019/1/24 10:48
 * @Version: 1.0
 *
 */
public class StatisticalDTO {

    public StatisticalDTO() {
    }

    public StatisticalDTO(List<BaseOnlineUser> data, int page,long total,int size) {
        this.result = new StatisticalResult(data, page, total, size);
    }

    String status;
    String message;
    StatisticalResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StatisticalResult getResult() {
        return result;
    }

    public void setResult(StatisticalResult result) {
        this.result = result;
    }
}


class StatisticalResult{
    List<BaseOnlineUser> data;
    int page;
    long total;
    int size;

    public StatisticalResult() {
    }

    public StatisticalResult(List<BaseOnlineUser> data, Integer page, long total, Integer size) {
        this.data = data;
        this.page = page;
        this.total = total;
        this.size = size;
    }

    public List<BaseOnlineUser> getData() {
        return data;
    }

    public void setData(List<BaseOnlineUser> data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
