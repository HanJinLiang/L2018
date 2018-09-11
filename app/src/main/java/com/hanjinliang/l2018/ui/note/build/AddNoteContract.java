package com.hanjinliang.l2018.ui.note.build;

import com.hanjinliang.l2018.base.BaseContract;
import com.hanjinliang.l2018.entity.UserEntity;
import com.hanjinliang.l2018.ui.login.LoginContract;

/**
 * Created by Administrator on 2018-09-11.
 */

public class AddNoteContract {
    public interface IAddNoteView extends BaseContract.IBaseView {
        void addNoteSuccess();
        void getAddNoteParam();
    }

    public interface IAddNotePresenter extends BaseContract.IBasePresenter<AddNoteContract.IAddNoteView>{
        void addNote(String userId,String articleTitle,String articleContent,String articleUrl,String articleTag);
    }
}
