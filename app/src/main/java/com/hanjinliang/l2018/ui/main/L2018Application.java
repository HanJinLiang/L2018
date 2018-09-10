package com.hanjinliang.l2018.ui.main;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.hanjinliang.l2018.utils.image.GlideLoader;
import com.hanjinliang.l2018.utils.image.MyImageLoader;

/**
 * Created by Administrator on 2018-09-08.
 */
public class L2018Application extends Application {
    private static L2018Application mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
        //设置图片加载为Glide
        MyImageLoader.getInstance().setImageLoader(new GlideLoader());

        // init it in the function of onCreate in ur Application
        Utils.init(this);

    }

    public static L2018Application getApp() {
        return mApp;
    }
}
