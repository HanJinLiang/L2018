package com.hanjinliang.l2018.utils.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.base.MenuInfoBean;
import com.hanjinliang.l2018.utils.share.ShareToolUtil;
import com.hanjinliang.l2018.utils.share.ShareUtil;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 浏览大图
 */
public class PicturePreviewActivity extends BaseActivity {
    public static final String PIC_PATHS="PIC_PATHS";
    public static final String PIC_POSITION="PIC_POSITION";

    @BindView(R.id.preview_pager)
    PreviewViewPager viewPager;
    private int position;
    private List<String> mImagePaths = new ArrayList<>();

    /**
     * 开始浏览图片
     * @param context
     * @param imagePaths
     * @param position
     */
    public static void previewPicture(Context context, ArrayList<String> imagePaths, int position){
        Intent intent=new Intent(context,PicturePreviewActivity.class);
        intent.putExtra(PIC_POSITION,position);
        intent.putStringArrayListExtra(PIC_PATHS,imagePaths);
        context.startActivity(intent);
    }


    @Override
    public void initView() {
        position = getIntent().getIntExtra(PIC_POSITION, 0);
        mImagePaths = getIntent().getStringArrayListExtra(PIC_PATHS);
        setTitle(position + 1 + "/" + mImagePaths.size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int i) {
                position = i;
                setTitle(position + 1 + "/" + mImagePaths.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setAdapter(new SimpleFragmentAdapter());
        viewPager.setCurrentItem(position,true);
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public ArrayList<MenuInfoBean> getMenuInfo() {
        ArrayList<MenuInfoBean> menuInfoBeans=new ArrayList<>();
        menuInfoBeans.add(new MenuInfoBean("保存图片",null,true));
        menuInfoBeans.add(new MenuInfoBean("QQ",null,false));
        menuInfoBeans.add(new MenuInfoBean("微信",null,false));
        menuInfoBeans.add(new MenuInfoBean("朋友圈",null,false));
        return menuInfoBeans;
    }

    @Override
    public void onMenuSelected(int itemId, CharSequence title) {
        switch (itemId){
            case 0:
                saveFile(0,false);
                break;
            default://分享
                    saveFile(itemId,true);
        }
    }

    /**
     * 先保存到本地  然后再分享
     * @param menuItemId
     */
    private void saveFile(final int menuItemId,final boolean isShare) {
        LogUtils.e("saveFile", TimeUtils.getNowString());
        Glide.with(PicturePreviewActivity.this)
                .asBitmap()
                .load(mImagePaths.get(viewPager.getCurrentItem()))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        LogUtils.e("saveSharePic1", TimeUtils.getNowString());
                        String picName=mImagePaths.get(viewPager.getCurrentItem()).substring(mImagePaths.get(viewPager.getCurrentItem()).lastIndexOf("/"));
                        File file= ShareToolUtil.saveSharePic(PicturePreviewActivity.this,resource,picName);
                        if(isShare) {
                            LogUtils.e("saveSharePic2", TimeUtils.getNowString());
                            if (file != null) {
                                switch (menuItemId) {
                                    case 1:
                                        ShareUtil.shareImageToQQ(PicturePreviewActivity.this, resource);
                                        break;
                                    case 2:
                                        ShareUtil.shareWechatFriend(PicturePreviewActivity.this, file);
                                        break;
                                    case 3:
                                        ShareUtil.shareWechatMoment(PicturePreviewActivity.this, "来自L2018的分享", file);
                                        break;
                                }
                            }
                        }else{
                            if(file==null){
                                ToastUtils.showLong("保存失败");
                            }else{
                                ToastUtils.showLong("保存成功");
                            }
                        }
                    }
                });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_picture_preview;
    }

    public class SimpleFragmentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImagePaths.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View contentView = LayoutInflater.from(PicturePreviewActivity.this).inflate(R.layout.item_picture_preview, container, false);
            // 常规图控件
            final PhotoView imageView = (PhotoView) contentView.findViewById(R.id.preview_image);
            // 长图控件
            final SubsamplingScaleImageView longImg = (SubsamplingScaleImageView) contentView.findViewById(R.id.longImg);
            final ProgressBar progressBar=contentView.findViewById(R.id.progressBar);
            final String path = mImagePaths.get(position);
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(PicturePreviewActivity.this)
                    .asBitmap()
                    .load(path)
                    .apply(options)
                    .into(new SimpleTarget<Bitmap>(480, 800) {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            progressBar.setVisibility(View.GONE);
                            if (isLongImg(resource)) {
                                imageView.setVisibility(View.GONE);
                                longImg.setVisibility(View.VISIBLE);
                                displayLongPic(resource, longImg);
                            } else {
                                imageView.setVisibility(View.VISIBLE);
                                longImg.setVisibility(View.GONE);
                                imageView.setImageBitmap(resource);
                            }
                        }

                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            progressBar.setVisibility(View.GONE);
                        }

                    });

            (container).addView(contentView, 0);
            //单击结束页面
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            longImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            return contentView;
        }
    }

    private boolean isLongImg(Bitmap bitmap) {
        if(null != bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int h = width * 3;
            return height > h;
        } else {
            return false;
        }
    }

    /**
     * 加载长图
     *
     * @param bmp
     * @param longImg
     */
    private void displayLongPic(Bitmap bmp, SubsamplingScaleImageView longImg) {
        longImg.setQuickScaleEnabled(true);
        longImg.setZoomEnabled(true);
        longImg.setPanEnabled(true);
        longImg.setDoubleTapZoomDuration(100);
        longImg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        longImg.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
        longImg.setImage(ImageSource.cachedBitmap(bmp), new ImageViewState(0, new PointF(0, 0), 0));
    }

}
