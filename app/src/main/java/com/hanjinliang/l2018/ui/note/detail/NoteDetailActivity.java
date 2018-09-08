package com.hanjinliang.l2018.ui.note.detail;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.base.MenuInfoBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * WebView
 */
public class NoteDetailActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView mWebView;
    public static void goToNoteDetail(Context context, String url){
        Intent intent=new Intent(context,NoteDetailActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
    String url=getIntent().getStringExtra("url");
        mWebView.loadUrl(url);

    }

    @Override
    public String setTitle() {
        return "文章详情";
    }

    @Override
    public ArrayList<MenuInfoBean> getMenuInfo() {
        ArrayList<MenuInfoBean> menuInfoBeans=new ArrayList<>();
        menuInfoBeans.add(new MenuInfoBean("完成", ContextCompat.getDrawable(this,R.drawable.video_icon),true));
        menuInfoBeans.add(new MenuInfoBean("分享", ContextCompat.getDrawable(this,R.drawable.icon_nav_collect),false));
        //手动刷新menu
        //invalidateOptionsMenu();
        return menuInfoBeans;
    }

    @Override
    public void onMenuSelected(int itemId, CharSequence title) {
        switch (itemId){
            case 0:
                ToastUtils.showLong("完成");
                break;
            case 1:
                ToastUtils.showLong("分享");
                break;
        }
    }
}
