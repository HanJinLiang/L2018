package com.hanjinliang.l2018.ui.note.build;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.base.Constant;
import com.hanjinliang.l2018.base.MenuInfoBean;
import com.hanjinliang.l2018.base.RxBus;
import com.hanjinliang.l2018.base.RxBusEvent;
import com.hanjinliang.l2018.ui.main.UserInfoHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        addAdapter();
        getSharePic();
    }

    private void addAdapter(){
        mRecyclerView.setLayoutManager(new GridLayoutManager(AddNoteActivity.this,4));
        mPicAdapter = new AddPicAdapter();
        mRecyclerView.setAdapter(mPicAdapter);
        mPicAdapter.onAttachedToRecyclerView(mRecyclerView);
        mPicAdapter.setOnAddPicClickListener(()->select());
        mPicAdapter.setOnItemClickListener((adapter,view,position)-> {
                ArrayList<LocalMedia> selectList=new ArrayList<>();
                for(String path:mPicAdapter.getData()){
                    LocalMedia localMedia=new LocalMedia();
                    localMedia.setPath(path);
                    selectList.add(localMedia);
                }
                PictureSelector.create(AddNoteActivity.this).externalPicturePreview(position, selectList);
            }
        );
    }

    private void getSharePic() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    mNoteTitle.setText(sharedText);
                }
            } else if (type.startsWith("image/")) {
                Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    mPicAdapter.addData(UriUtils.uri2File(imageUri, MediaStore.Images.ImageColumns.DATA).getPath());
                }
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                for(Uri uri:imageUris){
                    mPicAdapter.addData(UriUtils.uri2File(uri, MediaStore.Images.ImageColumns.DATA).getPath());
                }
            }
        }
    }

    @Override
    public void initPresenter() {
        mPresenter=new AddNotePresenter();
    }
    AddPicAdapter mPicAdapter;
    @Override
    public void initView() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        if(data==null){
            return;
        }
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


    @Override
    public ArrayList<MenuInfoBean> getMenuInfo() {
        ArrayList<MenuInfoBean> menuInfoBeans=new ArrayList<>();
        menuInfoBeans.add(new MenuInfoBean("保存",null,true));
        return menuInfoBeans;
    }

    @Override
    public void onMenuSelected(int itemId, CharSequence title) {
        switch (itemId){
            case 0:
                getAddNoteParam();
                break;
        }
    }

    @Override
    public void addNoteSuccess() {
        RxBus.get().post(new RxBusEvent(Constant.EVENT_BUILD_NOTE_SUCCESS));
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
        mPresenter.addNote(noteContent,noteContent,noteUrl,UserInfoHelper.getInstance().getUserInfo().getUserId(),mPicAdapter.getData());
    }


    private void select() {
        ArrayList<LocalMedia> selectList=new ArrayList<>();
        for(String path:mPicAdapter.getData()){
            LocalMedia localMedia=new LocalMedia();
            localMedia.setPath(path);
            selectList.add(localMedia);
        }
        PictureSelector.create(AddNoteActivity.this)
                .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_white_style)
                .compress(true)
                .selectionMedia(selectList)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    ArrayList<String>  paths=new ArrayList<>();
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                        if(media.isCompressed()){
                            paths.add(media.getCompressPath());
                        }else{
                            paths.add(media.getPath());
                        }

                    }
                    mPicAdapter.getData().clear();
                    mPicAdapter.addData(paths);
                    break;
            }
        }
    }
}
