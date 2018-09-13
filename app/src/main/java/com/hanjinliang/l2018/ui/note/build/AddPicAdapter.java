package com.hanjinliang.l2018.ui.note.build;

import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.utils.image.MyImageLoader;

/**
 * Created by Administrator on 2018-09-13.
 */
public class AddPicAdapter extends BaseQuickAdapter<String,BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {
    private int selectMax = 9;
    public AddPicAdapter() {
        super(R.layout.item_add_pic);
        setOnItemChildClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //显示加号
        if (helper.getItemViewType() == TYPE_CAMERA) {
            helper.setImageResource(R.id.image,R.drawable.addimg_1x);
            helper.addOnClickListener(R.id.image);
            helper.setVisible(R.id.ll_del,false);
        } else {
            helper.setVisible(R.id.ll_del,true);
            helper.addOnClickListener(R.id.ll_del);
            MyImageLoader.getInstance().load(item).into(helper.getView(R.id.image));
        }
    }
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    @Override
    public int getItemViewType(int position) {
        if (position ==  getData().size()) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }


    @Override
    public int getItemCount() {
        if (getData().size() < selectMax) {
            return getData().size() + 1;
        } else {
            return getData().size();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.image:
                if(mOnAddPicClickListener!=null){
                    mOnAddPicClickListener.onAddPicClick();
                }
                break;
            case R.id.ll_del:
                getData().remove(position);
                notifyItemRemoved(position);
                break;
        }
    }

    /**
     * 点击添加图片跳转
     */
    private OnAddPicClickListener mOnAddPicClickListener;

    public void setOnAddPicClickListener(OnAddPicClickListener onAddPicClickListener) {
        mOnAddPicClickListener = onAddPicClickListener;
    }

    public interface OnAddPicClickListener {
        void onAddPicClick();
    }
}
