package com.hanjinliang.l2018.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.l2018.R;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：
 */

public abstract class BaseActivity<T extends BaseContract.IBasePresenter> extends RxAppCompatActivity implements IBase,BaseContract.IBaseView{

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

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

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //隐藏标题栏
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        LinearLayout rootView= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_base,null);
        rootView.addView(LayoutInflater.from(this).inflate(getContentViewId(),null),new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(rootView);
        ButterKnife.bind(this);

        //设置布局
        if(isWithToolBar()){
            initToolBar();
        }else{
            setSupportActionBar(null);
            mToolbar.setVisibility(View.GONE);
        }


        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, getStatusBarColor()));
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
        initPresenter();
        attachView();
        initView();
    }

    /**
     * 是否含有ToolBar
     * @return
     */
    public boolean isWithToolBar(){
        return true;
    }
    public abstract int getContentViewId();
    /**
     * 初始化 ToolBar
     */
    protected void initToolBar(){
        mToolbar.setTitle(setTitle());
        mToolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v->finish());
    }
    /**
     * 返回头部颜色
     * @return
     */
    public    @ColorRes int getStatusBarColor(){
        return R.color.top_color;
    }

    @Override
    public void showLoading(String loadStr) {
        if (mLoadingDialog != null) {
            if(TextUtils.isEmpty(loadStr)) {
                TextView tv = mLoadingDialog.findViewById(R.id.tv_load_dialog);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        for(int i=0;i<getMenuInfo().size();i++){
            MenuInfoBean menuInfoBean = getMenuInfo().get(i);
            MenuItem menuItem = menu.add(0, i, i, menuInfoBean.getTitle());
            if(menuInfoBean.getIcon()!=null){
                menuItem.setIcon(menuInfoBean.getIcon());
            }
            if(menuInfoBean.isShow()){
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            }else{
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            }
        }
        return true;
    }

    /**
     * 解决menu不显示图标问题
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if(menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onMenuSelected(item.getItemId(),item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    /**
     * 右边菜单按钮被点击
     * @param itemId
     * @param title
     */
    public void onMenuSelected(int itemId, CharSequence title){}

    /**
     * 重写此方法 实现右边菜单
     * @return
     */
    public ArrayList<MenuInfoBean> getMenuInfo(){
        return new ArrayList<>();
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
