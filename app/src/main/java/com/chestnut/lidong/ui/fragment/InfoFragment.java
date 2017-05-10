package com.chestnut.lidong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chestnut.lidong.R;
import com.chestnut.lidong.ui.view.MyScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AshZheng on 2016/9/5.
 */
public class InfoFragment extends BaseFragment {

    private Banner banner;
    private List<String> imagesUrl;

    private RelativeLayout topBarLayout;
    private LinearLayout selectLayout;
    private LinearLayout staticLayout;
    private MyScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View infoFragment = inflater.inflate(R.layout.fragment_info, container, false);

        initView(infoFragment);
        setScrollView();

        return infoFragment;
    }

    private void initView(View infoFragment) {

        topBarLayout = (RelativeLayout) infoFragment.findViewById(R.id.fragment_info_top_bar_layout);
        selectLayout = (LinearLayout) infoFragment.findViewById(R.id.fragment_info_select_layout);
        staticLayout = (LinearLayout) infoFragment.findViewById(R.id.fragment_info_static_layout);
        scrollView = (MyScrollView) infoFragment.findViewById(R.id.fragment_info_sv);

        imagesUrl = new ArrayList<>();
        imagesUrl.add("http://i2.buimg.com/567571/7a89bc9191ac71ba.jpg");
        imagesUrl.add("http://i2.buimg.com/567571/337c5612ca9244d4.jpg");
        imagesUrl.add("http://i2.buimg.com/567571/e8d83fd9a8c7afeb.jpg");
        imagesUrl.add("http://i2.buimg.com/567571/2fb88cb9ddec9fd6.jpg");
        imagesUrl.add("http://i2.buimg.com/567571/ac58b0874cd4f494.jpg");
        banner = (Banner) infoFragment.findViewById(R.id.fragment_info_banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(imagesUrl);


        infoFragment.findViewById(R.id.fragment_info_camera_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicFromCamera();
            }
        });
    }


    private void setScrollView() {
        scrollView.setMyScrollViewListener(new MyScrollView.MyScrollViewListener() {
            @Override
            public void onScroll(int scrollY) {

                if (scrollY > banner.getBottom()) {
                    staticLayout.setVisibility(View.VISIBLE);
                } else {
                    staticLayout.setVisibility(View.GONE);
                }
//                LogUtil.printLog(InfoFragment.class, banner.getBottom() + " " + scrollY);
            }
        });
    }


    /**
     * 从摄像头中获取，返回结果会在onActivityResult()中
     */
    private void selectPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }


}
