package com.hanjinliang.l2018.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by caijun on 2018/9/5.
 * 功能介绍：
 */

public class BaseContract {

    public interface IBasePresenter<T extends IBaseView>{

        void attachView(T view);

        void detachView();

        /**
         *  可以补充一些通用请求
         */


    }


    public interface IBaseView{
        /**
         * 可以弹出加载框
         * @param loadStr 加载框提示语
         */

        void showLoading(String loadStr);

        /**
         * 关闭加载框
         */
        void hideLoading();


        /**
         * 请求失败
         * @param failMsg 错误消息
         */
        void showFail(String failMsg);

        /**
         * 无网络
         */
        void showNoNet();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();
    }
}
