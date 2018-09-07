package com.hanjinliang.l2018.net;

import com.hanjinliang.l2018.entity.ResultEntity;
import com.hanjinliang.l2018.entity.UserEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：接口类
 */

public interface Api {
    @FormUrlEncoded
    @POST("user/register")
    Observable<ResultEntity<String>> register(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/loginByPassword")
    Observable<ResultEntity<UserEntity>> loginByPassword(@Field("account") String account, @Field("password") String password);
}
