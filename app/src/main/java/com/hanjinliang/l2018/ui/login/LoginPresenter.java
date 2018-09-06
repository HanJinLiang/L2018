package com.hanjinliang.l2018.ui.login;

import com.hanjinliang.l2018.base.BasePresenter;
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
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    mView.loginSuccess();
                },throwable -> {
                    //必须实现不然异常时直接崩溃(可以看看有没有通用方法)
                });
    }

}
