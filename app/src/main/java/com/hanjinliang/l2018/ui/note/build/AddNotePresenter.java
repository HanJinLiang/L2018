package com.hanjinliang.l2018.ui.note.build;

import android.graphics.Bitmap;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.l2018.base.BaseObserver;
import com.hanjinliang.l2018.base.BasePresenter;
import com.hanjinliang.l2018.net.Api;
import com.hanjinliang.l2018.net.RetrofitFactory;
import com.luck.picture.lib.PictureSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018-09-11.
 */

public class AddNotePresenter extends BasePresenter<AddNoteContract.IAddNoteView> implements AddNoteContract.IAddNotePresenter{

    @Override
    public void addNote(String articleTitle, String articleContent, String articleUrl, String articleTag,List<String> picPaths) {
       ArrayList< MultipartBody.Part>  picsMult=new ArrayList<>();
        for(String picPath:picPaths){
            File file = new File(picPath);
            LogUtils.e("图片大小--"+FileUtils.getFileLength(file)/1024+"M");
            if(FileUtils.getFileLength(file)>1024*1024*4){
               boolean save=ImageUtils.save(ImageUtils.compressByQuality(ImageUtils.getBitmap(file),1024*1024*4l),file, Bitmap.CompressFormat.JPEG);
                LogUtils.e("压缩后图片大小--"+FileUtils.getFileLength(file)/1024*1024+"M");
            }

            String fileName=picPath.substring(picPath.lastIndexOf("/"));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image",fileName, requestFile);
            picsMult.add(body);
        }
        RetrofitFactory.getRetrofit()
                .noteNew(toRequestBody(articleTitle),toRequestBody(articleContent),toRequestBody(articleUrl),toRequestBody(articleTag),picsMult)
                //转换数据源
                .compose(handleResult())
                //绑定生命周期
                .compose(mView.bindToLife())
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        if(s!=null){
                            mView.addNoteSuccess();
                        }
                    }
                });
    }

    public RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

}
