package com.chestnut.lidong.mvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chestnut.lidong.LiDongApplication;
import com.chestnut.lidong.constant.AppConstant;
import com.chestnut.lidong.entity.UserInfo;
import com.chestnut.lidong.mvp.dao.SharedPreferencesHelper;
import com.chestnut.lidong.mvp.dao.UserInfoDao;
import com.chestnut.lidong.mvp.model.LoginActivityDataManager;
import com.chestnut.lidong.mvp.view.LoginActivityView;
import com.chestnut.lidong.utils.JsonUtil;
import com.chestnut.lidong.utils.LogUtil;
import com.chestnut.lidong.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * Created by AshZheng on 2016/9/19.
 */
public class LoginActivityPresenter extends BasePresenter {

    private LoginActivityView activityView;
    private LoginActivityDataManager dataManager;
    private LiDongApplication application;
    private Context context;

    public LoginActivityPresenter(LoginActivityView activityView) throws SQLException {
        this.activityView = activityView;
        context = (Activity) activityView;
        dataManager = new LoginActivityDataManager(new UserInfoDao(context.getApplicationContext()));
        application = (LiDongApplication) ((Activity) activityView).getApplication();
    }

    public void login(String username, String password) {

        if (username.length() < 6 || !StringUtil.isLegal(username)) {
            activityView.login(AppConstant.LOGIN_NO_USER);
            return;
        }
        if (password.length() < 6 || !StringUtil.isLegal(password)) {
            activityView.login(AppConstant.LOGIN_WRONG_PASSWORD);
            return;
        }

        String urlPart = "?username=" + username + "&" + "password=" + password;
        LogUtil.printLog(this.getClass(), "请求数据URL ：" + AppConstant.SERVER_URL_LOGIN + urlPart);
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AppConstant.SERVER_URL_LOGIN + urlPart, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        LogUtil.printLog(this.getClass(), response.toString());

                        int loginResult = AppConstant.LOGIN;
                        UserInfo userInfo;
                        //解析JSON字符串
                        try {
                            loginResult = response.getInt("loginResult");
                            userInfo = JsonUtil.parserUserData(response.getJSONObject("user"));
                            if (userInfo != null && loginResult == AppConstant.LOGIN_SUCCESS) {
                                application.setUserInfo(userInfo);
                                SharedPreferencesHelper.writeInt(context.getApplicationContext(), SharedPreferencesHelper.USER_ID, Integer.parseInt(userInfo.getUserId()));
                                getUserRunningData(userInfo.getUserId());
                                try {
                                    dataManager.savaUserInfo(userInfo);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        activityView.login(loginResult);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.printLog(this.getClass(), error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void register(String username, String password, String password2) {

        if (!password.equals(password2)) {
            activityView.register(AppConstant.REGISTER_DIFFERENT_PASSWORD);
            return;
        }
        if (username.length() < 6 || !StringUtil.isLegal(username)) {
            activityView.register(AppConstant.REGISTER_ILLEGAL_USERNAME);
            return;
        }
        if (password.length() < 6 || !StringUtil.isLegal(password)) {
            activityView.register(AppConstant.REGISTER_ILLEGAL_PASSWORD);
            return;
        }
        String urlPart = "?username=" + username + "&" + "password=" + password;
        RequestQueue requestQueue = Volley.newRequestQueue(((Activity) (activityView)).getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(AppConstant.SERVER_URL_REGISTER + urlPart, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int registerResult = AppConstant.REGISTER;
                        try {
                            registerResult = response.getInt("registerResult");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        activityView.register(registerResult);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getUserRunningData(String uid) {

        String urlPart = "?uid=" + uid;
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
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
