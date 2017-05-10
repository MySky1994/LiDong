package com.chestnut.lidong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.chestnut.lidong.LiDongApplication;
import com.chestnut.lidong.R;
import com.chestnut.lidong.db.DataBaseHelper;
import com.chestnut.lidong.mvp.dao.SharedPreferencesHelper;
import com.chestnut.lidong.mvp.dao.UserInfoDao;
import com.chestnut.lidong.mvp.dao.UserRunningDataLocalDao;
import com.chestnut.lidong.ui.LoginActivity;
import com.chestnut.lidong.utils.LogUtil;
import com.chestnut.lidong.utils.RunningDataUtil;
import com.chestnut.lidong.utils.UserDataUtil;

import java.sql.SQLException;

/**
 * Created by AshZheng on 2016/9/5.
 */
public class AboutMeFragment extends BaseFragment {

    private LiDongApplication application;

    private TextView usernameTV;
    private TextView userIdTV;


    private TextView totalDistaceTV;
    private TextView totalFrequencyTV;
    private TextView totalTimeTV;
    private TextView bestDistanceTV;
    private TextView bestTimeTV;
    private TextView bestSpeedTV;
    private TextView best10kmTV;
    private TextView bestHalfMaTV;
    private TextView bestMaTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View aboutMeFragment = inflater.inflate(R.layout.fragment_about_me, container, false);
        application = (LiDongApplication) getActivity().getApplication();

        initView(aboutMeFragment);
        setData();

        return aboutMeFragment;
    }

    private void initView(View aboutMeFragment) {

        usernameTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_username_tv);
        userIdTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_userid_tv);

        totalDistaceTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_total_distance_tv);
        totalFrequencyTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_total_frequency_tv);
        totalTimeTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_total_time_tv);
        bestDistanceTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_best_distance_tv);
        bestTimeTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_best_time_tv);
        bestSpeedTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_best_speed_tv);
        best10kmTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_best_10km_tv);
        bestHalfMaTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_best_half_ma_tv);
        bestMaTV = (TextView) aboutMeFragment.findViewById(R.id.fragment_about_me_best_ma_tv);

        aboutMeFragment.findViewById(R.id.fragment_about_me_loginout_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    destroyUserInfo();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(AboutMeFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
                AboutMeFragment.this.getActivity().finish();
            }


        });

    }


    private void setData() {
        if (application == null || application.getUserRunningData() == null) {
            LogUtil.printLog(this.getClass(), "application null");
            return;
        }
        totalDistaceTV.setText(RunningDataUtil.distanceToTextView(application.getUserRunningData().getTotalDistance()));
        totalFrequencyTV.setText(application.getUserRunningData().getTotalFrequency() + "");
        totalTimeTV.setText(RunningDataUtil.timeToTextViewH(application.getUserRunningData().getTotalTime()));
        bestDistanceTV.setText(RunningDataUtil.distanceToTextView(application.getUserRunningData().getBestDistance()));
        bestTimeTV.setText(RunningDataUtil.timeToTextViewH(application.getUserRunningData().getBestTime()));
        bestSpeedTV.setText(application.getUserRunningData().getBestPace() + "");
        best10kmTV.setText(RunningDataUtil.timeToTextViewH(application.getUserRunningData().getBest10KM()));
        bestHalfMaTV.setText(RunningDataUtil.timeToTextViewH(application.getUserRunningData().getBestHalfMa()));
        bestMaTV.setText(RunningDataUtil.timeToTextViewH(application.getUserRunningData().getBestMa()));

        if (application.getUserInfo() == null) {
            LogUtil.printLog(this.getClass(), "getUserInfo null");
            return;
        }
        usernameTV.setText(application.getUserInfo().getUsername());
        userIdTV.setText(UserDataUtil.userIdToString(application.getUserInfo().getUserId()));
    }

    private void destroyUserInfo() throws SQLException {
        LogUtil.printLog(this.getClass(), "删除数据库中的数据！");
        UserInfoDao userInfoDao = new UserInfoDao(this.getActivity());
        UserRunningDataLocalDao userRunningDataLocalDao = new UserRunningDataLocalDao(this.getActivity());
        userInfoDao.clearAll();
        userRunningDataLocalDao.clearAll();
    }

    @Override
    public void onResume() {
        setData();
        super.onResume();
    }
}
