package com.chestnut.lidong;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.chestnut.lidong.entity.UserInfo;
import com.chestnut.lidong.entity.UserRunningData;

/**
 * Created by AshZheng on 2016/9/5.
 */
public class LiDongApplication extends Application {

    private UserInfo userInfo;
    private UserRunningData userRunningData;

    @Override
    public void onCreate() {
        initAPP();
        super.onCreate();
    }

    public void initAPP() {
        //在百度地图 SDK各功能组件使用之前都需要调用
        SDKInitializer.initialize(getApplicationContext());

        userRunningData = new UserRunningData();
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserRunningData getUserRunningData() {
        return userRunningData;
    }

    public void setUserRunningData(UserRunningData userRunningData) {
        this.userRunningData = userRunningData;
    }
}
