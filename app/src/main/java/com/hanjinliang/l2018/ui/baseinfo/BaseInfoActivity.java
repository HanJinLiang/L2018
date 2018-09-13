package com.hanjinliang.l2018.ui.baseinfo;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.base.Constant;
import com.hanjinliang.l2018.base.RxBus;
import com.hanjinliang.l2018.base.RxBusEvent;
import com.hanjinliang.l2018.ui.baseinfo.update.UpdateBaseInfoActivity;
import com.hanjinliang.l2018.ui.main.UserInfoHelper;
import com.hanjinliang.l2018.utils.image.MyImageLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-09-07.
 */

public class BaseInfoActivity extends BaseActivity<BaseInfoContract.IBaseInfoPresenter> implements BaseInfoContract.IBaseInfoView {
    @BindView(R.id.headerImage)
    ImageView headerImage;
    @BindView(R.id.id_info_name)
    TextView id_info_name;

    @Override
    public void initPresenter() {
        mPresenter=new BaseInfoPresenter();
    }

    @Override
    public void initView() {
        MyImageLoader.getInstance().load(UserInfoHelper.getInstance().getUserInfo().getUserPic()).into(headerImage);
        id_info_name.setText(UserInfoHelper.getInstance().getUserInfo().getUserName());
    }

    @Override
    public String setTitle() {
        return "基础信息";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_base_info;
    }

    @OnClick({R.id.id_info_name,R.id.headerImage})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.id_info_name:
                startActivity(new Intent(this, UpdateBaseInfoActivity.class));
                break;
            case R.id.headerImage:
                select();
                break;
        }
    }
    private void select() {
        new RxPermissions(this).request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        PictureSelector.create(BaseInfoActivity.this)
                                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                .theme(R.style.picture_white_style)
                                .maxSelectNum(1)
                                .compress(true)
                                //.selectionMedia(selectList)// 是否传入已选图片
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                    } else {
                        finish();
                    }
                });

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
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    if(selectList!=null&&selectList.size()>0){
                        mPresenter.uploadFile(selectList.get(0).getCompressPath());
                    }
                    break;
            }
        }
    }


    @Override
    public void uploadSuccess(String picPath) {
        UserInfoHelper.getInstance().getUserInfo().setUserPic(picPath);
        UserInfoHelper.getInstance().saveCurrentUserInfo();
        initView();
        //通知刷新
        RxBus.get().post(new RxBusEvent(Constant.EVENT_UPDATE_USERINFO));
    }
}
