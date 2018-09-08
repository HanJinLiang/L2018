package com.hanjinliang.l2018.ui.note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseFragment;

import butterknife.BindView;

/**
 * 笔记列表
 */
public class NoteFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_note;
    }


    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(()->mSwipeRefreshLayout.postDelayed(()->mSwipeRefreshLayout.setRefreshing(false),2000));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NoteAdapter noteAdapter=new NoteAdapter();
        mRecyclerView.setAdapter(noteAdapter);
        noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");
        noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");
        noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");noteAdapter.addData("1");
    }
}
