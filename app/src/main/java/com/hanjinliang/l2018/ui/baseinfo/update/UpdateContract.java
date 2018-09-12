package com.hanjinliang.l2018.ui.baseinfo.update;

import com.hanjinliang.l2018.base.BaseContract;

/**
 * Created by HanJinLiang on 2018-09-06.
 */

public class UpdateContract {
    public interface IUpdateView extends BaseContract.IBaseView{
        void updateSuccess(String name);
    }

    public interface IUpdatePresenter extends BaseContract.IBasePresenter<UpdateContract.IUpdateView>{
        void updateName(String name);
    }
}
