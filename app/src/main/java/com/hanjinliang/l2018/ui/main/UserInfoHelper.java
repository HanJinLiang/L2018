package com.hanjinliang.l2018.ui.main;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.hanjinliang.l2018.entity.UserEntity;
import com.hanjinliang.l2018.net.RetrofitFactory;

/**
 * Created by HanJinLiang on 2018-09-10.
 */
public class UserInfoHelper {
    private static UserEntity USERINFO;
    private static UserInfoHelper userInfoHelper;
    SPUtils mSPUtils;
    private UserInfoHelper(){
        mSPUtils= SPUtils.getInstance("userInfo");
    }

    public static UserInfoHelper getInstance(){
        if(userInfoHelper==null){
            synchronized (UserInfoHelper.class) {
                if(userInfoHelper==null) {
                    userInfoHelper = new UserInfoHelper();
                }
            }

        }
        return userInfoHelper;
    }

    public UserEntity getUserInfo(){
        if(USERINFO==null){
            USERINFO=new Gson().fromJson(mSPUtils.getString("userInfo",null),UserEntity.class);
        }
        return USERINFO;
    }
    public void clearUserInfo(){
        USERINFO=null;
        mSPUtils.clear();
    }

    public void updateUserInfo(UserEntity userEntity){
        if(userEntity==null){
            return;
        }
        USERINFO=userEntity;
        mSPUtils.put("userInfo",new Gson().toJson(userEntity) );
    }
    public void saveCurrentUserInfo(){
        if(USERINFO==null){
            return;
        }
        mSPUtils.put("userInfo",new Gson().toJson(USERINFO) );
    }

}
