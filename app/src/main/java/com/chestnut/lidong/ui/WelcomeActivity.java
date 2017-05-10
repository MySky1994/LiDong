package com.chestnut.lidong.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chestnut.lidong.LiDongApplication;
import com.chestnut.lidong.R;
import com.chestnut.lidong.constant.AppConstant;
import com.chestnut.lidong.entity.UserInfo;
import com.chestnut.lidong.mvp.dao.SharedPreferencesHelper;
import com.chestnut.lidong.mvp.dao.UserInfoDao;
import com.chestnut.lidong.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public class WelcomeActivity extends BaseActivity {

    private int waitTime;
    private boolean isFirstOpen = true;
    private boolean isLogin = false;
    private LiDongApplication application;
    private boolean isOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        timerHandler.postDelayed(runnable, 0);
        try {
            init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void init() throws SQLException {
        isFirstOpen = SharedPreferencesHelper.readBoolean(this, SharedPreferencesHelper.IS_FIRST_OPEN);
        if (isFirstOpen) {
            SharedPreferencesHelper.writeBoolean(this, SharedPreferencesHelper.IS_FIRST_OPEN, false);
        }
        int userId = SharedPreferencesHelper.readInt(this, SharedPreferencesHelper.USER_ID);
        if (userId != AppConstant.ERROR) {
            isLogin = true;
        }

        if (isLogin) {
            UserInfoDao userInfoDao = new UserInfoDao(this);
            List<UserInfo> userInfoList = userInfoDao.queryByProperty("user_id", userId);
            if (userInfoList != null && userInfoList.size() > 0) {
                application = (LiDongApplication) this.getApplication();
                application.setUserInfo(userInfoList.get(0));
                getUserRunningData(String.valueOf(userId));
            }
        }
        isOk = true;
    }

    //计时器
    private Handler timerHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            timerHandler.postDelayed(this, 1000);

            if (waitTime > 2 && isOk) {
                if (isFirstOpen) {
                    Intent intent = new Intent(WelcomeActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!isLogin) {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                //销毁计时器
                timerHandler.removeCallbacks(runnable);
            } else {
                waitTime++;
            }
        }
    };

    public void getUserRunningData(String uid) {

        String urlPart = "?uid=" + uid;
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AppConstant.SERVER_URL_GET_USER_RUNDATA + urlPart, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            application.setUserRunningData(JsonUtil.parserUserRunningData(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}
