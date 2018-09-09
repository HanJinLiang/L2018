package com.hanjinliang.l2018.ui.note;

import android.text.Html;
import android.view.View;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.ui.note.detail.NoteDetailActivity;
import com.hanjinliang.l2018.utils.image.MyImageLoader;
import com.hanjinliang.l2018.utils.image.PicturePreviewActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-08.
 */

public class NoteAdapter extends BaseQuickAdapter<String,BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {
    public NoteAdapter( ) {
        super(R.layout.item_note);
        setOnItemChildClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.id_note_name,"裁军");
        helper.setText(R.id.id_note_time, TimeUtils.getNowString());
        helper.setText(R.id.id_note_content,"这是第一笔记标题");
        //通过html的形式实现超链接
        String link="https://blog.csdn.net/zhangjinhuang/article/details/52416608";
        String testLink1 ="<font color='#0e6c9c'>"+ "<a href="+link+">"+link+"</a>";
        helper.setText(R.id.id_note_link, Html.fromHtml(testLink1));
        MyImageLoader.getInstance().showImage(mContext,
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536425846760&di=f84d26fcfddb56b752548483437ea9fb&imgtype=0&src=http%3A%2F%2Fimage.uc.cn%2Fs%2Fwemedia%2Fs%2F2017%2F09aad549d74aa227606b51edaefa08f7x1280x852x87.jpeg"
        ,helper.getView(R.id.id_note_header));
        helper.addOnClickListener(R.id.id_note_link);
        helper.addOnClickListener(R.id.id_note_header);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.id_note_link:
                NoteDetailActivity.goToNoteDetail(mContext,"https://blog.csdn.net/zhangjinhuang/article/details/52416608");
                break;
            case R.id.id_note_header:
                ArrayList<String> paths=new ArrayList<>();
                paths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536425846760&di=f84d26fcfddb56b752548483437ea9fb&imgtype=0&src=http%3A%2F%2Fimage.uc.cn%2Fs%2Fwemedia%2Fs%2F2017%2F09aad549d74aa227606b51edaefa08f7x1280x852x87.jpeg");

                PicturePreviewActivity.previewPicture(mContext,paths,0);
                break;
        }
    }
}
