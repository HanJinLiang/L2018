package com.hanjinliang.l2018.ui.register;

import com.hanjinliang.l2018.base.BaseObserver;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.entity.UserEntity;
import com.hanjinliang.l2018.net.RetrofitFactory;
import com.hanjinliang.l2018.ui.main.UserInfoHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HanJinLiang on 2018-09-06.
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterView> implements RegisterContract.IRegisterPresenter {
    public void login(String tel, String pwd) {
        RetrofitFactory.getRetrofit()
                .register(tel,pwd)
                .flatMap(s-> RetrofitFactory.getRetrofit().loginByPassword(tel,pwd))
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserEntity>(mView,"登录中"){
                    @Override
                    public void onNext(UserEntity userEntity) {
                        UserInfoHelper.getInstance().updateUserInfo(userEntity);
                    }
                });
    }

    @Override
    public void getCode(String tel) {
        RetrofitFactory.getRetrofit()
                .getRegisterCode(tel)
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(mView,"获取验证码中"){
                    @Override
                    public void onNext(String code) {
                        mView.getCodeSuccess(code);
                    }
                });
    }

    @Override
    public void register(String tel, String pwd, String code) {
        RetrofitFactory.getRetrofit()
                .register(tel,pwd,code)
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(mView,"注册中"){
                    @Override
                    public void onNext(String code) {
                        mView.registerSuccess(code);
                    }
                });
    }
}
