package com.hanjinliang.l2018.utils.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;


import com.blankj.utilcode.util.TimeUtils;
import com.hanjinliang.l2018.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by HanJinLiang on 2018-07-11.
 */

public class ShareToolUtil {
    private static String sharePicPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"L2018"+ File.separator;
    /**
     * 保存图片，并返回一个File类型的文件
     */
    public static File saveSharePic(Context context, Bitmap bitmap,String picName){
        if (bitmap == null) {
            return null;
        }
        if (Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED)){
            File file = new File(sharePicPath);
            if (!file.exists()){
                file.mkdirs();
            }
            if(TextUtils.isEmpty(picName)){
                picName=TimeUtils.getNowString(new SimpleDateFormat("yyyyMMdd_HH:mm:ss"))+".jpg";
            }
            File filePic = new File(sharePicPath,picName);
            if (filePic.exists()){
                filePic.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(filePic);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return filePic;
        }
        return null;
    }
}
