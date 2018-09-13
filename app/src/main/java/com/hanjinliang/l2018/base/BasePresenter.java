package com.hanjinliang.l2018.base;

import android.content.Intent;

import com.hanjinliang.l2018.entity.ResultEntity;
import com.hanjinliang.l2018.ui.login.LoginActivity;
import com.hanjinliang.l2018.ui.main.L2018Application;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：
 */

public class BasePresenter <T extends BaseContract.IBaseView> implements BaseContract.IBasePresenter<T> {

    protected T mView;
    @Override
    public void attachView(T view) {
        this.mView=view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView=null;
        }
    }

    public T getView() {
        return mView;
    }

    /**
     * Rx优雅处理服务器返回
     * @param <T> ResultEntity<T> to T
     * @return
     */
    public <T> ObservableTransformer<ResultEntity<T>, T> handleResult() {
        return upstream -> upstream.flatMap(result -> {
                    //此处可以做部分异常处理
                    if (result.getCode() == 200) {
                        if(result.getData()==null){
                            return createData((T)result.getMsg());
                        }else{
                            return createData(result.getData());
                        }
                    }else{
                        if(result.getCode()==10001){//登录失效
                            RxBus.get().post(new RxBusEvent(Constant.EVENT_LOGIN_OUT));
                            Intent intent = new Intent(L2018Application.getApp(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            L2018Application.getApp().startActivity(intent);
                        }
                        mView.showFail(result.getMsg());
                        return Observable.empty();
                    }
                }
        );
    }

    /**
     * 生成数据源
     * @param t
     * @param <T>
     * @return
     */
    private <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                if(mView!=null) {
                    subscriber.onNext(t);
                }
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

}
