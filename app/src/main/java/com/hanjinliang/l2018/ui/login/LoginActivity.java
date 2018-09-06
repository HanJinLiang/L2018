package com.hanjinliang.l2018.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.hanjinliang.l2018.MainActivity;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HanJinLiang on 2018-09-06.
 * 登录界面
 */

public class LoginActivity extends BaseActivity<LoginContract.ILoginPresenter> implements LoginContract.ILoginView {
    @BindView(R.id.layout_root_login)
    View mRootView;
    @BindView(R.id.et_tel)
    EditText et_tel;
    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @Override
    public void initPresenter() {
        mPresenter=new LoginPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        //设置导航栏透明
        BarUtils.setStatusBarAlpha(this,0);
        mRootView.setBackgroundDrawable(ImageUtils.bitmap2Drawable(ImageUtils.fastBlur(ImageUtils.getBitmap(R.drawable.bg_2018),0.7f,5)));
    }

    @OnClick({R.id.login})
    public void onClick(View view){
        String tel = et_tel.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        if(!RegexUtils.isMobileSimple(tel)){
            et_tel.setError("请输入正确的手机号码");
            return;
        }
        mPresenter.login(tel,pwd);
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
