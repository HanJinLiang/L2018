package com.hanjinliang.l2018.ui.baseinfo.update;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.l2018.base.BaseObserver;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.net.RetrofitFactory;
import com.hanjinliang.l2018.ui.baseinfo.BaseInfoContract;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by HanJinLiang on 2018-09-06.
 */
public class UpdatePresenter extends BasePresenter<UpdateContract.IUpdateView> implements UpdateContract.IUpdatePresenter {


    @Override
    public void updateName(String name) {
        RetrofitFactory.getRetrofit()
                .updateUser(name)
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(mView,"修改中"){
                    @Override
                    public void onNext(String result) {
                        mView.updateSuccess(name);
                    }
                });
    }
}
