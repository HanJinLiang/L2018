package com.hanjinliang.l2018.entity;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：
 */

public class ResultEntity<T> {

    /**
     * 请求返回标识
     */
    private int code;

    /**
     * 请求状态 true/false
     */
    private boolean status;

    /**
     * 错误信息
     */
    private String msg;


    /**
     * 数据
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
