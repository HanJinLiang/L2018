package com.hanjinliang.l2018;

import com.hanjinliang.l2018.base.BaseContract;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：演示
 */

public class DemoContract {

    public interface IDemoView extends BaseContract.IBaseView{

        void test(String str);
    }

    public interface IDemoPresenter extends BaseContract.IBasePresenter<IDemoView>{

        void getTest();
    }
}
