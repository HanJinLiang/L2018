package com.hanjinliang.l2018.utils.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hanjinliang.l2018.R;

/**
 * Created by HanJinLiang on 2018-01-11.
 */

public class MyImageLoader implements ImageLoader {
    private static volatile MyImageLoader mMyImageLoader;
    public static MyImageLoader getInstance(){
        if(mMyImageLoader==null){
            synchronized (MyImageLoader.class) {
                mMyImageLoader = new MyImageLoader();
            }
        }
        return mMyImageLoader;
    }
    @Override
    public void showImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.bg_gray)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
