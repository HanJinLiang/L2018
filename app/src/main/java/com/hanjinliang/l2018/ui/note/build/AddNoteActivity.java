package com.hanjinliang.l2018.ui.note.build;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.base.RxBus;
import com.hanjinliang.l2018.ui.main.UserInfoHelper;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018-09-07.
 * 新增笔记
 */
public class AddNoteActivity extends BaseActivity<AddNoteContract.IAddNotePresenter> implements AddNoteContract.IAddNoteView{
    @BindView(R.id.id_add_link)
    EditText mNoteLink;
    @BindView(R.id.id_add_title)
    EditText mNoteTitle;

    @Override
    public void initPresenter() {
        mPresenter=new AddNotePresenter();
    }

    @Override
    public void initView() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);
        String content = item.getText().toString();
        String urlLink ="<font color='#0e6c9c'>"+ "<a href="+content+">"+content+"</a>";

        //对剪贴板文字的操作
        mNoteLink.setText(Html.fromHtml(urlLink));
        mNoteLink.setSelection(content.length());
    }

    @Override
    public String setTitle() {
        return "新增笔记";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_note_add;
    }

    @OnClick({R.id.save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.save:
                getAddNoteParam();
                break;
        }
    }

    @Override
    public void addNoteSuccess() {
        RxBus.get().post(new Integer(1));
        ToastUtils.showLong("笔记添加成功");
        finish();
    }

    @Override
    public void getAddNoteParam() {
        String noteUrl=mNoteLink.getText().toString().trim();
        String noteContent=mNoteTitle.getText().toString().trim();

        if(StringUtils.isTrimEmpty(noteUrl)||StringUtils.isTrimEmpty(noteContent)){
            ToastUtils.showLong("内容不能为空");
            return;
        }

        mPresenter.addNote(UserInfoHelper.getInstance().getUserInfo().getUserId(),noteContent,noteContent,noteUrl,UserInfoHelper.getInstance().getUserInfo().getUserId());
    }
}
