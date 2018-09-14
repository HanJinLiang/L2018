package com.hanjinliang.l2018.net;

import com.hanjinliang.l2018.entity.NoteEntity;
import com.hanjinliang.l2018.entity.ResultEntity;
import com.hanjinliang.l2018.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：接口类
 */

public interface Api {
    @FormUrlEncoded
    @POST("login/getRegisterCode")
    Observable<ResultEntity<String>> getRegisterCode(@Field("account") String account);
    @FormUrlEncoded
    @POST("login/register")
    Observable<ResultEntity<String>> register(@Field("account") String account,@Field("password") String password,@Field("code") String code);
    @FormUrlEncoded
    @POST("login/register")
    Observable<ResultEntity<String>> register(@Field("account") String account, @Field("password") String password);

    @Multipart
    @POST("user/updateUserPic")
    Observable<ResultEntity<String>> updateUserPic(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("user/updateUser")
    Observable<ResultEntity<String>> updateUser(@Field("userName") String userName);

    @FormUrlEncoded
    @POST("login/loginByPassword")
    Observable<ResultEntity<UserEntity>> loginByPassword(@Field("account") String account, @Field("password") String password);

    @Multipart
    @POST("article/new")
    Observable<ResultEntity<String>> noteNew( @Part("articleTitle") RequestBody articleTitle, @Part("articleContent") RequestBody  articleContent
            , @Part("articleUrl") RequestBody  articleUrl, @Part("articleTag") RequestBody  articleTag,@Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST("article/findArticle")
    Observable<ResultEntity<ArrayList<NoteEntity>>> noteList(@Field("pageIndex") int pageIndex, @Field("pageSize") int pageSize );
}
