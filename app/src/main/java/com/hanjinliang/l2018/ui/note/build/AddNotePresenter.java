package com.hanjinliang.l2018.ui.note.build;

import com.hanjinliang.l2018.base.BaseObserver;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.net.Api;
import com.hanjinliang.l2018.net.RetrofitFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-09-11.
 */

public class AddNotePresenter extends BasePresenter<AddNoteContract.IAddNoteView> implements AddNoteContract.IAddNotePresenter{

    @Override
    public void addNote(String userId, String articleTitle, String articleContent, String articleUrl, String articleTag) {
        RetrofitFactory.getRetrofit()
                .noteNew(userId,articleTitle,articleContent,articleUrl,articleTag)
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        if(s!=null){
                            mView.addNoteSuccess();
                        }
                    }
                });
    }
}
