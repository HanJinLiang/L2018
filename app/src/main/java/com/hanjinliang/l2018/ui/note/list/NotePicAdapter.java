package com.hanjinliang.l2018.ui.note.list;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.utils.image.MyImageLoader;
import com.hanjinliang.l2018.utils.image.PicturePreviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanJinLiang on 2018-01-10.
 */
public class NotePicAdapter extends BaseQuickAdapter<String,BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public NotePicAdapter(List<String> articlePic){
       super(R.layout.item_pic,articlePic);
       setOnItemClickListener(this);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        MyImageLoader.getInstance().load(item).into(helper.getView(R.id.noteImageView));
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PicturePreviewActivity.previewPicture(mContext, (ArrayList<String>) getData(),position);
    }
}
