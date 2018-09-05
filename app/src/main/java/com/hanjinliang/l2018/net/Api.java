package com.hanjinliang.l2018.net;

import com.hanjinliang.l2018.entity.ResultEntity;
import com.hanjinliang.l2018.entity.UserEntity;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：接口类
 */

public interface Api {

    @GET("user/getAllUser")
    io.reactivex.Observable<ResultEntity<List<UserEntity>>> getString();
}
