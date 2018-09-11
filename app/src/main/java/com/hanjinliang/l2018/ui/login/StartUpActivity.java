package com.hanjinliang.l2018.ui.login;

import android.content.Intent;
import android.widget.ImageView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.ui.main.MainActivity;
import com.hanjinliang.l2018.ui.main.UserInfoHelper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-09-09.
 */

public class StartUpActivity extends BaseActivity {
    @BindView(R.id.startUpImage)
    ImageView startUpImage;
    @Override
    public void initPresenter() {
    }
    Disposable mDisposable;
    @Override
    public void initView() {
        //设置导航栏透明
        BarUtils.setStatusBarAlpha(this,0);
        startUpImage.setImageDrawable(ImageUtils.bitmap2Drawable(ImageUtils.fastBlur(ImageUtils.getBitmap(R.drawable.bg_2018),0.7f,5)));
        Observable.timer(2,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( aLong-> {
                        if(UserInfoHelper.getInstance().getUserInfo()!=null){
                            startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                        }else{
                            startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
                        }
                        finish();
                    });

    }

    @Override
    public String setTitle() {
        return null;
    }
    @Override
    public boolean isWithToolBar() {
        return false;
    }
    @Override
    public int getContentViewId() {
        return R.layout.activity_startup;
    }
}
