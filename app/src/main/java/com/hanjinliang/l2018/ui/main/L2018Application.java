package com.hanjinliang.l2018.ui.main;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Administrator on 2018-09-08.
 */
public class L2018Application extends Application {
    public static L2018Application APP;
    @Override
    public void onCreate() {
        super.onCreate();
        APP=this;
        // init it in the function of onCreate in ur Application
        Utils.init(this);

    }
}
