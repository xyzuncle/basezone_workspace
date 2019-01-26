package com.kq.common.DTO;

/**
 * Created by Ace on 2017/6/11.
 */
public class ObjectRestResponse<T> {

    T data;
    boolean rel;
    private int status = 200;
    private String message;

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }


    public ObjectRestResponse rel(boolean rel) {
        this.setRel(rel);
        return this;
    }

    public ObjectRestResponse  status(int status){
        this.setStatus(status);
        return this;
    }

    public ObjectRestResponse  message(String message){
        this.setMessage(message);
        return this;
    }


    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
