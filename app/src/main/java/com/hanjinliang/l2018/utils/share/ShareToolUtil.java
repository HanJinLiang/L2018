package com.hanjinliang.l2018.utils.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;


import com.hanjinliang.l2018.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by HanJinLiang on 2018-07-11.
 */

public class ShareToolUtil {
    private static String sharePicName = "share_pic.jpg";
    private static String sharePicPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"DiBao"+ File.separator+"sharepic"+ File.separator;
    /**
     * 保存图片，并返回一个File类型的文件
     */
    public static File saveSharePic(Context context, Bitmap bitmap){
        if (Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED)){
            File file = new File(sharePicPath);
            if (!file.exists()){
                file.mkdirs();
            }
            File filePic = new File(sharePicPath,sharePicName);
            if (filePic.exists()){
                filePic.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(filePic);
                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
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
