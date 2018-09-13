package com.hanjinliang.l2018.ui.note.list;

import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.entity.NoteEntity;
import com.hanjinliang.l2018.ui.note.detail.NoteDetailActivity;
import com.hanjinliang.l2018.utils.image.MyImageLoader;
import com.hanjinliang.l2018.utils.image.PicturePreviewActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-08.
 */

public class NoteAdapter extends BaseQuickAdapter<NoteEntity,BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {
    public NoteAdapter( ) {
        super(R.layout.item_note);
        setOnItemChildClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoteEntity item) {
        helper.setText(R.id.id_note_name,item.getUserName());
        helper.setText(R.id.id_note_time, item.getCreateDate());
        helper.setText(R.id.id_note_content,item.getArticleContent());
        //通过html的形式实现超链接
        String testLink1 ="<font color='#0e6c9c'>"+ "<a href="+item.getArticleUrl()+">"+item.getArticleUrl()+"</a>";
        helper.setText(R.id.id_note_link, Html.fromHtml(testLink1));
        MyImageLoader.getInstance().load(item.getUserPic()).into(helper.getView(R.id.id_note_header));
        helper.addOnClickListener(R.id.id_note_link);
        helper.addOnClickListener(R.id.id_note_header);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.id_note_link:
                NoteDetailActivity.goToNoteDetail(mContext,getItem(position).getArticleUrl());
                break;
            case R.id.id_note_header:
                ArrayList<String> paths=new ArrayList<>();
                paths.add(getItem(position).getUserPic());
                PicturePreviewActivity.previewPicture(mContext,paths,0);
                break;
        }
    }
}
