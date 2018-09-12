package com.hanjinliang.l2018.ui.baseinfo;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.l2018.base.BaseObserver;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.entity.UserEntity;
import com.hanjinliang.l2018.net.RetrofitFactory;
import com.hanjinliang.l2018.ui.baseinfo.BaseInfoContract;
import com.hanjinliang.l2018.ui.login.LoginContract;
import com.hanjinliang.l2018.ui.main.UserInfoHelper;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by HanJinLiang on 2018-09-06.
 */
public class BaseInfoPresenter extends BasePresenter<BaseInfoContract.IBaseInfoView> implements BaseInfoContract.IBaseInfoPresenter {


    @Override
    public void uploadFile(String picPath) {
        File file = new File(picPath);
        String fileName=picPath.substring(picPath.lastIndexOf("/"));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",fileName, requestFile);
        RetrofitFactory.getRetrofit()
                .updateUserPic(body)
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(mView,"登录中"){
                    @Override
                    public void onNext(String result) {
                        LogUtils.e("result=="+result);
                        mView.uploadSuccess(result);
                    }
                });
    }
}
