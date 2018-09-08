package com.hanjinliang.l2018.ui.main;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Administrator on 2018-09-08.
 */
public class L2018Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // init it in the function of onCreate in ur Application
        Utils.init(this);

    }
}
