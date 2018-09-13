package com.hanjinliang.l2018.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.l2018.R;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Fragment基类
 * @param <T>
 */
public abstract class BaseFragment<T extends BaseContract.IBasePresenter> extends RxFragment implements IBase,BaseContract.IBaseView{

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
    protected T mPresenter;

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mLoadingDialog = DialogHelper.getLoadingDialog(activity);
    }

    @Override
    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
         super.onViewCreated(view,savedInstanceState);
        initPresenter();
        attachView();
        initView();
        initRxBusEvent();
    }

    private void initRxBusEvent() {
        compositeDisposable=new CompositeDisposable();
        Disposable subscribe = RxBus.get().toObservable(RxBusEvent.class).subscribe(rxBusEvent -> onHandlerRxBusEvent(rxBusEvent));
        compositeDisposable.add(subscribe);
    }

    /**
     * 处理事件  父类重新此方法就行
     * @param event
     */
    public void onHandlerRxBusEvent(RxBusEvent event){

    }
    public abstract int getContentViewId();


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
        ToastUtils.showLong(failMsg);
    }

    @Override
    public void showNoNet() {
        ToastUtils.showLong("没网了SB");
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
    public void onDetach() {
        super.onDetach();
        if(mPresenter!=null){
            mPresenter.detachView();
        }

        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }


    @Override
    public String setTitle() {
        return null;
    }

}
