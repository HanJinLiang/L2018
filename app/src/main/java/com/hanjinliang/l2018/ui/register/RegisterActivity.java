package com.hanjinliang.l2018.ui.register;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.entity.UserEntity;
import com.hanjinliang.l2018.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HanJinLiang on 2018-09-06.
 * 注册
 */

public class RegisterActivity extends BaseActivity<RegisterContract.IRegisterPresenter> implements RegisterContract.IRegisterView {
    @BindView(R.id.et_tel)
    EditText et_tel;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @Override
    public void initPresenter() {
        mPresenter=new RegisterPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
    }

    @Override
    public String setTitle() {
        return "注册";
    }

    @OnClick({R.id.id_register_code,R.id.idRegister})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.id_register_code:
                String tel=et_tel.getText().toString().trim();
                if(RegexUtils.isMobileSimple(tel)){
                    mPresenter.getCode(tel);
                }else{
                    ToastUtils.showLong("请输入正确手机号");
                }
                break;
            case R.id.idRegister:
                register();
                break;

        }
    }

    private void register() {
        String tel=et_tel.getText().toString().trim();
        String pwd=et_pwd.getText().toString().trim();
        String code=et_code.getText().toString().trim();
        if(RegexUtils.isMobileSimple(tel)){
            mPresenter.register(tel,pwd,code);
        }else{
            ToastUtils.showLong("请输入正确手机号");
        }
    }


    @Override
    public void getCodeSuccess(String code) {

    }

    @Override
    public void registerSuccess(String code) {
        ToastUtils.showLong("注册成功");
        finish();
    }
}
