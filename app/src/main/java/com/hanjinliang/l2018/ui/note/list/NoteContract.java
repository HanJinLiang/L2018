package com.hanjinliang.l2018.ui.note.list;

import com.hanjinliang.l2018.base.BaseContract;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.entity.NoteEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-11.
 */

public class NoteContract {
    public interface INoteView extends BaseContract.IBaseView{

        void onLoadNoMoreData();

        void showNodeList(ArrayList<NoteEntity> datas);

        void onLoadMoreSuccess();

        void onLoadDataError();
    }

    public interface INotePresenter extends BaseContract.IBasePresenter<INoteView>{
        void loadNoteList(boolean isFirst);
    }
}
