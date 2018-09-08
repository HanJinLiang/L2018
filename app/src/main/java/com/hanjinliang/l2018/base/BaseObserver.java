package com.hanjinliang.l2018.base;

import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018-09-07.
 */
public abstract class BaseObserver<T> implements Observer<T> {
    private BaseContract.IBaseView mView;

    private String dialogStr;

    public BaseObserver(BaseContract.IBaseView v){
        mView = v;
    }

    public BaseObserver(BaseContract.IBaseView v,String dialogStr){
        mView = v;
        this.dialogStr=dialogStr;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mView.showLoading(dialogStr);
    }


    @Override
    public void onError(Throwable e) {
        mView.hideLoading();
//        if(e instanceof BaseException){
//            baseView.showFaild(e.getMessage());
//        }
        //TODO 提示错误信息
        ToastUtils.showLong(e.getMessage());
    }

    @Override
    public void onComplete() {
        mView.hideLoading();
    }
}
