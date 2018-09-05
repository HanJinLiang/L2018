package com.hanjinliang.l2018.net;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by caijun on 2018/5/26.
 * 功能介绍：网络请求基类
 */

public class RetrofitFactory {

    public final static String BASEAPI="https://www.fddlpz.com/";

    private volatile static Retrofit retrofit;

    @NonNull
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {

                    //cookie持久化
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            ;

                    //使用自定义的Log拦截器
                    builder.addInterceptor(new LoggingInterceptor());

                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASEAPI)
                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
