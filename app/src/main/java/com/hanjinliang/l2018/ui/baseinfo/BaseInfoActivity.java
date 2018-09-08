package com.hanjinliang.l2018.ui.baseinfo;

import android.content.Intent;
import android.view.View;

import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.ui.baseinfo.update.UpdateBaseInfoActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2018-09-07.
 */

public class BaseInfoActivity extends BaseActivity {
    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public String setTitle() {
        return "基础信息";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_base_info;
    }

    @OnClick({R.id.id_info_name})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.id_info_name:
                startActivity(new Intent(this, UpdateBaseInfoActivity.class));
                break;
        }
    }
}
