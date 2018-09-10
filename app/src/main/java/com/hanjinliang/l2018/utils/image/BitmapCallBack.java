package com.hanjinliang.l2018.utils.image;

import android.graphics.Bitmap;

/**
 * Created by HanJinLiang on 2018-04-13.
 */

public interface BitmapCallBack {
    void onBitmapLoaded(Bitmap bitmap);

    void onBitmapFailed(Exception e);

    public static class EmptyCallback implements BitmapCallBack {


        @Override
        public void onBitmapLoaded(Bitmap bitmap) {

        }

        @Override
        public void onBitmapFailed(Exception e) {

        }
    }
}
