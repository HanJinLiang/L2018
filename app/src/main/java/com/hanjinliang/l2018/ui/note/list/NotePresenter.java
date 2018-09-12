package com.hanjinliang.l2018.ui.note.list;

import com.hanjinliang.l2018.base.BaseObserver;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.entity.NoteEntity;
import com.hanjinliang.l2018.net.RetrofitFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-09-11.
 */
public class NotePresenter extends BasePresenter<NoteContract.INoteView> implements NoteContract.INotePresenter {
    private int pageIndex, pageSize = 10;
    ArrayList<NoteEntity> mDatas=new ArrayList<>();
    @Override
    public void loadNoteList(boolean isFirst) {
        if (isFirst) {
            pageIndex = 0;
        }
        RetrofitFactory.getRetrofit()
                .noteList(pageIndex, pageSize)
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notes-> {
                        pageIndex++;
                        if (!isFirst) {
                            if (notes == null || notes.size() == 0) {
                                mView.onLoadNoMoreData();
                            } else {
                                mView.onLoadMoreSuccess();
                            }
                        }

                        if (isFirst) {
                            mDatas.clear();
                        }
                        mDatas.addAll(notes);
                        mView.showNodeList(mDatas);
                    }
                ,  throwable-> mView.onLoadDataError());

    }
}
