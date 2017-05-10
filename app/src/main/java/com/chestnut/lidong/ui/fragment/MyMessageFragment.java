package com.chestnut.lidong.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chestnut.lidong.R;

/**
 * Created by AshZheng on 2016/9/5.
 */
public class MyMessageFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myMessageFragment = inflater.inflate(R.layout.fragment_my_message, container, false);

        return myMessageFragment;
    }
}
