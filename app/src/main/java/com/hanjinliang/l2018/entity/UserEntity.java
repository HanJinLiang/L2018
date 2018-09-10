package com.hanjinliang.l2018.entity;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：
 */

public class UserEntity {
    private int userId;
    private String userName;
    private String userDesc;
    private String account;
    private String header;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userDesc='" + userDesc + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
