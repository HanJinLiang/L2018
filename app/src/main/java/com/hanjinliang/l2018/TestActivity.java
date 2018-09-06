package com.hanjinliang.l2018;

import android.os.Bundle;
import android.widget.Toast;

import com.hanjinliang.l2018.base.BaseActivity;

public class TestActivity extends BaseActivity<DemoPresenter> implements DemoContract.IDemoView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.tv_test).setOnClickListener(v -> {
            mPresenter.getTest();
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    public void initPresenter() {
        mPresenter=new DemoPresenter();
    }


    @Override
    public void test(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void initView() {

    }
}
