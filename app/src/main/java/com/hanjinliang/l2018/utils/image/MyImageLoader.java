package com.hanjinliang.l2018.utils.image;

import android.net.Uri;

import com.hanjinliang.l2018.R;

import java.io.File;

/**
 * Created by HanJinLiang on 2018-04-13.
 * 图片加载
 */

public class MyImageLoader {
    private static ILoaderStrategy sLoader;
    private static volatile MyImageLoader sInstance;

    private MyImageLoader() {
    }

    //单例模式
    public static MyImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (MyImageLoader.class) {
                if (sInstance == null) {
                    //若切换其它图片加载框架，可以实现一键替换
                    sInstance = new MyImageLoader();
                }
            }
        }
        return sInstance;
    }

    //提供实时替换图片加载框架的接口
    public void setImageLoader(ILoaderStrategy loader) {
        if (loader != null) {
            sLoader = loader;
        }
    }

    public LoaderOptions load(String path) {
        return mDefaultOptions.url(path);
    }
    public LoaderOptions load(int drawable) {
        return mDefaultOptions.drawableResId(drawable);
    }

    public LoaderOptions load(File file) {
        return mDefaultOptions.file(file);
    }

    public LoaderOptions load(Uri uri) {
        return mDefaultOptions.uri(uri);
    }

    public void loadOptions(LoaderOptions options) {
        sLoader.loadImage(options);
    }

    //默认的Options
    LoaderOptions mDefaultOptions=new LoaderOptions()
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_background);

    public LoaderOptions getDefaultOptions() {
        return mDefaultOptions;
    }

    public void setDefaultOptions(LoaderOptions defaultOptions) {
        mDefaultOptions = defaultOptions;
    }
}
