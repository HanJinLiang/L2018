package com.hanjinliang.l2018.ui.login;

import android.content.Intent;
import android.widget.ImageView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.ui.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-09-09.
 */

public class StartUpActivity extends BaseActivity {
    @BindView(R.id.startUpImage)
    ImageView startUpImage;
    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        startUpImage.setImageDrawable(ImageUtils.bitmap2Drawable(ImageUtils.fastBlur(ImageUtils.getBitmap(R.drawable.bg_2018),0.7f,5)));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(SPUtils.getInstance().contains("userInfo")){
                    startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                }else{
                    startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2000);
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
