package com.hanjinliang.l2018.ui.note.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.l2018.R;
import com.hanjinliang.l2018.base.BaseFragment;
import com.hanjinliang.l2018.base.Constant;
import com.hanjinliang.l2018.base.RxBus;
import com.hanjinliang.l2018.base.RxBusEvent;
import com.hanjinliang.l2018.entity.NoteEntity;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * 笔记列表
 */
public class NoteListFragment extends BaseFragment<NoteContract.INotePresenter> implements NoteContract.INoteView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    NoteAdapter mNoteAdapter;
    public static NoteListFragment newInstance() {
        NoteListFragment fragment = new NoteListFragment();
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_note;
    }


    @Override
    public void initPresenter() {
       mPresenter=new NotePresenter();
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(()->mPresenter.loadNoteList(true));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNoteAdapter=new NoteAdapter();
        mNoteAdapter.bindToRecyclerView(mRecyclerView);
        mNoteAdapter.setOnLoadMoreListener(()->mPresenter.loadNoteList(false),mRecyclerView);
        mPresenter.loadNoteList(true);
    }

    @Override
    public void onHandlerRxBusEvent(RxBusEvent event) {
        super.onHandlerRxBusEvent(event);
        switch (event.getEventCode()){
            case Constant.EVENT_BUILD_NOTE_SUCCESS:
                mPresenter.loadNoteList(true);
                break;
        }
    }

    @Override
    public void onLoadNoMoreData() {
        mNoteAdapter.loadMoreEnd();
    }

    @Override
    public void showNodeList(ArrayList<NoteEntity> datas) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNoteAdapter.getData().clear();
        mNoteAdapter.addData(datas);
        //mNoteAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void onLoadMoreSuccess() {
        mNoteAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadDataError() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
