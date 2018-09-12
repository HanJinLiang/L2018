package com.hanjinliang.l2018.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseActivity;
import com.hanjinliang.l2018.base.RxBus;
import com.hanjinliang.l2018.base.RxBusEvent;
import com.hanjinliang.l2018.ui.baseinfo.BaseInfoActivity;
import com.hanjinliang.l2018.ui.login.LoginActivity;
import com.hanjinliang.l2018.ui.note.list.NoteListFragment;
import com.hanjinliang.l2018.ui.note.build.AddNoteActivity;
import com.hanjinliang.l2018.utils.image.MyImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 首页
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fake_status_bar)
    View fake_status_bar;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.viewPager)
    ViewPager mainViewPager;
    ArrayList<Fragment> mFragmentArrayList = new ArrayList<>();
    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean isWithToolBar() {
        return false;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        initNavigationView();
        mFragmentArrayList.add(NoteListFragment.newInstance());

        mainViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentArrayList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentArrayList.size();
            }
        });
        //BarUtils.setStatusBarColor4Drawer(this,drawer,fake_status_bar, ContextCompat.getColor(this,R.color.top_color),100,false);
      //  BarUtils.addMarginTopEqualStatusBarHeight(toolbar);//

        compositeDisposable=new CompositeDisposable();
        Disposable subscribe = RxBus.get().toObservable(RxBusEvent.class).subscribe(rxBusEvent -> initNavigationView());
        compositeDisposable.add(subscribe);
    }

    @Override
    public String setTitle() {
        return null;
    }


    /**
     * 初始化侧滑栏
     */
    private void initNavigationView() {
        View headerLayout;
        if(navigationView.getHeaderCount()==0){
            headerLayout =navigationView.inflateHeaderView(R.layout.nav_header_main);
            headerLayout.setOnClickListener(v->startActivity(new Intent(this, BaseInfoActivity.class)));
        }else{
            headerLayout=navigationView.getHeaderView(0);
        }

        TextView userName= headerLayout.findViewById(R.id.nav_userName);
        TextView userPhone=headerLayout.findViewById(R.id.nav_userPhone);
        ImageView imageView=headerLayout.findViewById(R.id.imageView);

        userName.setText(UserInfoHelper.getInstance().getUserInfo().getUserName());
        userPhone.setText(UserInfoHelper.getInstance().getUserInfo().getAccount());
        MyImageLoader.getInstance().load(UserInfoHelper.getInstance().getUserInfo().getUserPic()).isCircle(true).into(imageView);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_collect://收藏
                return true;
            case R.id.nav_loginOut://退出登录
                UserInfoHelper.getInstance().clearUserInfo();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
        }
        return false;
    }

    @OnClick({R.id.fab})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:// 新建笔记
                startActivity(new Intent(this, AddNoteActivity.class));
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
