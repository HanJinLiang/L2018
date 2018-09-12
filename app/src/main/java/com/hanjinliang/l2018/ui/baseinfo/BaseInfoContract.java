package com.hanjinliang.l2018.ui.baseinfo;

import com.hanjinliang.l2018.base.BaseContract;
import com.hanjinliang.l2018.entity.UserEntity;

/**
 * Created by HanJinLiang on 2018-09-06.
 */

public class BaseInfoContract {
    public interface IBaseInfoView extends BaseContract.IBaseView{
        void uploadSuccess(String picPath);
    }

    public interface IBaseInfoPresenter extends BaseContract.IBasePresenter<BaseInfoContract.IBaseInfoView>{
        void uploadFile(String picPath);
    }
}
