package com.hanjinliang.l2018.ui.register;

import com.hanjinliang.l2018.base.BaseContract;
import com.hanjinliang.l2018.entity.UserEntity;

/**
 * Created by HanJinLiang on 2018-09-06.
 */

public class RegisterContract {
    public interface IRegisterView extends BaseContract.IBaseView{
        void getCodeSuccess(String code);
        void registerSuccess(String code);
    }

    public interface IRegisterPresenter extends BaseContract.IBasePresenter<RegisterContract.IRegisterView>{
        void getCode(String tel);
        void register(String tel,String pwd,String code);
    }
}
