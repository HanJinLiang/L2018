package com.hanjinliang.l2018.base;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018-09-08.
 * BaseActivity里面封装的右边Menu
 */
public class MenuInfoBean {
    String title;
    Drawable icon;
    boolean isShow;

    public MenuInfoBean(String title, Drawable icon, boolean isShow) {
        this.title = title;
        this.icon = icon;
        this.isShow = isShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
