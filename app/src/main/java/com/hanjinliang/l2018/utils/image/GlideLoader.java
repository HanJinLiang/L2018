package com.hanjinliang.l2018.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hanjinliang.l2018.GlideApp;
import com.hanjinliang.l2018.GlideRequest;
import com.hanjinliang.l2018.GlideRequests;
import com.hanjinliang.l2018.ui.main.L2018Application;


import java.security.MessageDigest;

/**
 * Created by HanJinLiang on 2018-04-13.
 * 图片加载真正的实现
 */

public class GlideLoader implements ILoaderStrategy {
    @Override
    public void loadImage(final LoaderOptions options) {
        GlideRequests glideRequests= GlideApp.with(L2018Application.getApp());
        GlideRequest<Drawable> glideRequest=null;
        if(options.drawableResId!=0){
            glideRequest=glideRequests.load(options.drawableResId);
        }else if(!TextUtils.isEmpty(options.url)){
            glideRequest=glideRequests.load(options.url);
        }else if(options.file!=null){
            glideRequest=glideRequests.load(options.file);
        }else if(options.uri!=null){
            glideRequest=glideRequests.load(options.uri);
        }else{//路径为空
            glideRequest=glideRequests.load(null);
        }
        if(options.placeholderResId!=0){
            glideRequest.placeholder(options.placeholderResId);
        }else if(options.placeholder!=null){
            glideRequest.placeholder(options.placeholder);
        }

        if(options.errorResId!=0){
            glideRequest.error(options.errorResId);
        }
        if(options.isCenterCrop){
            glideRequest.centerCrop();
        }
        if(options.isCenterInside){
            glideRequest.fitCenter();
        }
        if(options.skipLocalCache){
            glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);//不使用磁盘缓存
        }

        if(options.skipMemoryCache){
            glideRequest.skipMemoryCache(options.skipLocalCache);//不使用磁盘缓存
        }

        if(options.targetWidth!=0&&options.targetHeight!=0){
            glideRequest.override(options.targetWidth,options.targetHeight);
        }

        if(options.callBack!=null) {
            glideRequest.listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    options.callBack.onBitmapFailed(e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    options.callBack.onBitmapLoaded(ImageUtils.drawable2Bitmap(resource));
                    return false;
                }
            });

        }
        if(options.isCircle){//圆形
            glideRequest.transform(new GlideCircleTransform(L2018Application.getApp()));
        }else {
            if (options.bitmapAngle != 0) {
                glideRequest.transform(new GlideRoundTransform(L2018Application.getApp(), options.bitmapAngle));
            }
        }

        if(options.targetView!=null){
            glideRequest.into((ImageView) options.targetView);
        }

    }

    /**
     * 圆角
     */
    public class GlideRoundTransform extends BitmapTransformation {

        private  float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private  Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }

    /**
     * 圆形
     */
    public class GlideCircleTransform extends BitmapTransformation {

        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private   Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }


        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }


}
