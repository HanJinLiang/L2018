package com.hanjinliang.l2018.ui.login;

import com.hanjinliang.l2018.base.BaseContract;
import com.hanjinliang.l2018.entity.UserEntity;

/**
 * Created by HanJinLiang on 2018-09-06.
 */

public class LoginContract {
    public interface ILoginView extends BaseContract.IBaseView{
        void loginSuccess(UserEntity userEntity);
    }

    public interface ILoginPresenter extends BaseContract.IBasePresenter<LoginContract.ILoginView>{
        void login(String tel,String pwd);
    }
}
