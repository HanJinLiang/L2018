package com.hanjinliang.l2018.net;

import com.hanjinliang.l2018.entity.NoteEntity;
import com.hanjinliang.l2018.entity.ResultEntity;
import com.hanjinliang.l2018.entity.UserEntity;

import java.util.ArrayList;
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
    @POST("login/register")
    Observable<ResultEntity<String>> register(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("login/loginByPassword")
    Observable<ResultEntity<UserEntity>> loginByPassword(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("article/new")
    Observable<ResultEntity<String>> noteNew(@Field("userId") String userId, @Field("articleTitle") String articleTitle
            , @Field("articleContent") String articleContent, @Field("articleUrl") String articleUrl, @Field("articleTag") String articleTag);

    @FormUrlEncoded
    @POST("article/findArticle")
    Observable<ResultEntity<ArrayList<NoteEntity>>> noteList(@Field("pageIndex") int pageIndex, @Field("pageSize") int pageSize );
}
