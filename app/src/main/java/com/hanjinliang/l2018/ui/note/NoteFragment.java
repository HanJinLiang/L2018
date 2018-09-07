package com.hanjinliang.l2018.ui.note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanjinliang.l2018.R;

/**
 * 笔记列表
 */
public class NoteFragment extends Fragment {

    public static NoteFragment newInstance( ) {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        return view;
    }



}
