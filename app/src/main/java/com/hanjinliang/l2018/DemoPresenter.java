package com.hanjinliang.l2018;

import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.net.Api;
import com.hanjinliang.l2018.net.RetrofitFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：
 */

public class DemoPresenter  extends BasePresenter<DemoContract.IDemoView> implements DemoContract.IDemoPresenter{

    /**
     * 请求数据
     */
    @Override
    public void getTest() {
        RetrofitFactory.getRetrofit().create(Api.class)
                .getString()
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    mView.test(s.toString());
                },throwable -> {
                    //必须实现不然异常时直接崩溃(可以看看有没有通用方法)
                });

    }
}
