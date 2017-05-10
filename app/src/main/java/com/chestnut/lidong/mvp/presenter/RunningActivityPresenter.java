package com.chestnut.lidong.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chestnut.lidong.LiDongApplication;
import com.chestnut.lidong.constant.AppConstant;
import com.chestnut.lidong.entity.UserRunningData;
import com.chestnut.lidong.entity.UserRunningDataLocal;
import com.chestnut.lidong.mvp.dao.UserRunningDataLocalDao;
import com.chestnut.lidong.mvp.model.RunningActivityDataManager;
import com.chestnut.lidong.mvp.view.RunningActivityView;
import com.chestnut.lidong.utils.DateUtil;
import com.chestnut.lidong.utils.JsonUtil;
import com.chestnut.lidong.utils.LogUtil;
import com.chestnut.lidong.utils.RunningDataUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by AshZheng on 2016/9/10.
 */
public class RunningActivityPresenter extends BasePresenter {

    private RunningActivityView activityView;
    private RunningActivityDataManager dataManager;
    private LiDongApplication application;

    public RunningActivityPresenter(RunningActivityView activityView) throws SQLException {
        this.activityView = activityView;
        dataManager = new RunningActivityDataManager(new UserRunningDataLocalDao(((Activity) activityView).getApplicationContext()));
        application = (LiDongApplication) ((Activity) activityView).getApplication();
    }

    public void lodeData() {

    }

    public void saveData(UserRunningData userRunningData) throws JSONException {
        if (activityView == null) {
            return;
        }

//        LogUtil.printLog(this.getClass(), activityView.getRunningData().toString());

        UserRunningDataLocal dataLocal = new UserRunningDataLocal();
        dataLocal.setDistance(activityView.getRunningData().getDistance());
        dataLocal.setTime(activityView.getRunningData().getTime());
        dataLocal.setDate(DateUtil.getDate());
        dataLocal.setSpeed(Double.parseDouble(RunningDataUtil.calculateSpeed(dataLocal.getDistance(), dataLocal.getTime())));

        userRunningData.setTotalDistance(userRunningData.getTotalDistance() + dataLocal.getDistance());
        userRunningData.setTotalTime(userRunningData.getTotalTime() + dataLocal.getTime());
        userRunningData.setTotalFrequency(userRunningData.getTotalFrequency() + 1);

        if (userRunningData.getBestDistance() < dataLocal.getDistance()) {
            userRunningData.setBestDistance(dataLocal.getDistance());
        }
        if (userRunningData.getBestTime() < dataLocal.getTime()) {
            userRunningData.setBestTime(dataLocal.getTime());
        }
        application.setUserRunningData(userRunningData);


        if (userRunningData == null) {
            LogUtil.printLog(this.getClass(), "userRunningData 为空");
        }
        LogUtil.printLog(this.getClass(), "发出POST请求 " + JsonUtil.userRunningData2JSONObject(userRunningData));
        RequestQueue requestQueue = Volley.newRequestQueue(application.getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( AppConstant.SERVER_URL_POST_USER_RUNDATA, JsonUtil.userRunningData2JSONObject(userRunningData),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.printLog(this.getClass(), "return " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.printLog(this.getClass(), "error = " + error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);


        Action1 dataAction = new Action1<UserRunningDataLocal>() {
            @Override
            public void call(UserRunningDataLocal data) {
                try {
                    dataManager.saveRunningData(data);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        Observable.just(dataLocal)
                .observeOn(Schedulers.io())
                .subscribe(dataAction);


    }

    public void updateRecord(UserRunningData userRunningData) {

    }

    public void destroy() {
        activityView = null;
    }
}
