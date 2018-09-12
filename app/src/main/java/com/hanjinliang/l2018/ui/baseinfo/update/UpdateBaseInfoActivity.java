package com.hanjinliang.l2018.ui.baseinfo.update;

import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.base.BaseContract;
import com.hanjinliang.l2018.base.BaseObserver;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.base.Constant;
import com.hanjinliang.l2018.base.RxBus;
import com.hanjinliang.l2018.base.RxBusEvent;
import com.hanjinliang.l2018.net.RetrofitFactory;
import com.hanjinliang.l2018.ui.main.UserInfoHelper;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-09-08.
 * 更新基本信息
 */
public class UpdateBaseInfoActivity extends BaseActivity<UpdateContract.IUpdatePresenter> implements UpdateContract.IUpdateView{
    @BindView(R.id.id_update_name)
    EditText update_name;
    @Override
    public void initPresenter() {
        mPresenter=new UpdatePresenter();
    }

    @Override
    public void initView() {
        update_name.setText(UserInfoHelper.getInstance().getUserInfo().getUserName());
    }

    @Override
    public String setTitle() {
        return "昵称";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_info_update;
    }

    @OnClick(R.id.id_save)
    public void onClick(View view){
        String name=update_name.getText().toString().trim();
       mPresenter.updateName(name);
    }

    @Override
    public void updateSuccess(String name) {
        UserInfoHelper.getInstance().getUserInfo().setUserName(name);
        UserInfoHelper.getInstance().saveCurrentUserInfo();

        RxBus.get().post(new RxBusEvent(Constant.EVENT_UPDATE_USERINFO));
    }
}
