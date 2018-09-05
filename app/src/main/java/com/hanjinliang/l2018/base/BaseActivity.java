package com.hanjinliang.l2018.base;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.hanjinliang.l2018.R;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：
 */

public abstract class BaseActivity<T1 extends BaseContract.IBasePresenter> extends RxAppCompatActivity implements IBase,BaseContract.IBaseView{


    /**
     * RxBus代替EventBus 将监听在结束是关闭
     */
    public CompositeDisposable compositeDisposable;


    /**
     * 加载框
     */
    public Dialog mLoadingDialog;

    /**
     * 在子类中实现init方法
     */
    @Nullable
    protected T1 mPresenter;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
        initPresenter();
        attachView();
        //todo
    }

    @Override
    public void showLoading(String loadStr) {

        if (mLoadingDialog != null) {
            if(TextUtils.isEmpty(loadStr)) {
                TextView tv = (TextView) mLoadingDialog.findViewById(R.id.tv_load_dialog);
                tv.setText(loadStr);
            }
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * @param failMsg 错误消息
     */
    @Override
    public void showFail(String failMsg) {

        Toast.makeText(this,failMsg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNet() {
        Toast.makeText(this,"没网了SB",Toast.LENGTH_SHORT).show();
    }

    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    /**
     * 绑定BaseView
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mPresenter!=null){
            mPresenter.detachView();
        }

        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
