package com.hanjinliang.l2018.base;

import com.hanjinliang.l2018.entity.ResultEntity;

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

    /**
     * Rx优雅处理服务器返回
     * @param <T> ResultEntity<T> to T
     * @return
     */
    public <T> ObservableTransformer<ResultEntity<T>, T> handleResult() {
        return upstream -> {
            return upstream.flatMap(result -> {
                        //此处可以做部分异常处理
                        return createData(result.getData());
                    }
            );
        };
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

    /**
     * 可在此处实现部分通用请求
     */

}
