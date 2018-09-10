package com.hanjinliang.l2018.utils.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.File;

/**
 * Created by HanJinLiang on 2018-04-13.
 * 图片加载参数  为适配个框架参数设置不统一
 */
public class LoaderOptions {
    public int placeholderResId;//占位符id
    public int errorResId;//图片加载失败id
    public boolean isCenterCrop;
    public boolean isCenterInside;
    public boolean skipLocalCache;//是否缓存到本地
    public boolean skipMemoryCache;
    public Bitmap.Config config = Bitmap.Config.RGB_565;

    public int targetWidth;
    public int targetHeight;
    public int bitmapAngle; //圆角角度
    public boolean isCircle; //是不是圆形
    public float degrees; //旋转角度.注意:picasso针对三星等本地图片，默认旋转回0度，即正常位置。此时不需要自己rotate
    public Drawable placeholder;
    public View targetView;//targetView展示图片
    public BitmapCallBack callBack;
    public String url;
    public File file;
    public int drawableResId;
    public Uri uri;

    public LoaderOptions() {
    }

    public LoaderOptions url(String url) {
        this.url = url;
        return this;
    }

    public LoaderOptions file(File file) {
        this.file = file;
        return this;
    }

    public LoaderOptions drawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
        return this;
    }

    public LoaderOptions uri(Uri uri) {
        this.uri = uri;
        return this;
    }
    public LoaderOptions isCircle(boolean isCircle) {
        this.isCircle = isCircle;
        return this;
    }


    public void into(View targetView) {
        this.targetView = targetView;
        MyImageLoader.getInstance().loadOptions(this);
    }

    public void bitmap(BitmapCallBack callBack) {
        this.callBack = callBack;
        MyImageLoader.getInstance().loadOptions(this);
    }

    public LoaderOptions setCallBack(BitmapCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public LoaderOptions placeholder(@DrawableRes int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }

    public LoaderOptions placeholder(Drawable placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public LoaderOptions error(@DrawableRes int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public LoaderOptions centerCrop() {
        isCenterCrop = true;
        return this;
    }

    public LoaderOptions centerInside() {
        isCenterInside = true;
        return this;
    }

    public LoaderOptions config(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    public LoaderOptions resize(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    /**
     * 圆角
     * @param bitmapAngle   度数
     * @return
     */
    public LoaderOptions angle(int bitmapAngle) {
        this.bitmapAngle = bitmapAngle;
        return this;
    }

    public LoaderOptions skipLocalCache(boolean skipLocalCache) {
        this.skipLocalCache = skipLocalCache;
        return this;
    }

    public LoaderOptions skipMemoryCache(boolean skipMemoryCache) {
        this.skipMemoryCache = skipMemoryCache;
        return this;
    }

    public LoaderOptions rotate(float degrees) {
        this.degrees = degrees;
        return this;
    }



}
