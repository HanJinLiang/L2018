package com.hanjinliang.l2018.ui.login;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.hanjinliang.l2018.base.BaseObserver;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.entity.UserEntity;
import com.hanjinliang.l2018.net.Api;
import com.hanjinliang.l2018.net.RetrofitFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HanJinLiang on 2018-09-06.
 */
public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter {
    @Override
    public void login(String tel, String pwd) {
        RetrofitFactory.getRetrofit().create(Api.class)
                .register(tel,pwd)
                .flatMap(s-> RetrofitFactory.getRetrofit().create(Api.class).loginByPassword(tel,pwd))
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserEntity>(mView,"登录中"){
                    @Override
                    public void onNext(UserEntity userEntity) {
                        SPUtils.getInstance().put("userInfo",new Gson().toJson(userEntity));
                        mView.loginSuccess(userEntity);
                    }
                });
    }

}
