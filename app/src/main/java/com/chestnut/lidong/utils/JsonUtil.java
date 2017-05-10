package com.chestnut.lidong.utils;

import com.chestnut.lidong.entity.UserInfo;
import com.chestnut.lidong.entity.UserRunningData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AshZheng on 2016/9/19.
 */
public class JsonUtil {

    public static UserInfo parserUserData(JSONObject jsonObject) {
        UserInfo userInfo = new UserInfo();
        try {
            userInfo.setUsername(jsonObject.getString("uname").equals("null") ? null : jsonObject.getString("uname"));
            userInfo.setNiname(jsonObject.getString("uniname").equals("null") ? null : jsonObject.getString("uniname"));
            userInfo.setSex(jsonObject.getString("usex").equals("null") ? null : jsonObject.getString("usex"));
            userInfo.setUserIco(jsonObject.getString("uicon").equals("null") ? null : jsonObject.getString("uicon"));
            userInfo.setUserId(jsonObject.getString("uid").equals("null") ? null : jsonObject.getString("uid"));
            userInfo.setUserSign(jsonObject.getString("usign").equals("null") ? null : jsonObject.getString("usign"));
            userInfo.setDateOfBirth(jsonObject.getString("ubirthday").equals("null") ? null : jsonObject.getString("ubirthday"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static UserRunningData parserUserRunningData(JSONObject jsonObject) throws JSONException {
        UserRunningData userRunningData = new UserRunningData();

        userRunningData.setTotalDistance(jsonObject.getDouble("totalDistance"));
        userRunningData.setTotalFrequency(jsonObject.getInt("totalFrequency"));
        userRunningData.setTotalTime(jsonObject.getInt("totalTime"));
        userRunningData.setBestDistance(jsonObject.getDouble("bestDistance"));
        userRunningData.setBestTime(jsonObject.getInt("bestTime"));
        userRunningData.setBestPace(jsonObject.getDouble("bestPace"));
        userRunningData.setBest10KM(jsonObject.getInt("best10km"));
        userRunningData.setBestHalfMa(jsonObject.getInt("bestHalfma"));
        userRunningData.setBestMa(jsonObject.getInt("bestMa"));
        userRunningData.setUid(jsonObject.getInt("uid"));

        return userRunningData;
    }

    public static JSONObject userRunningData2JSONObject(UserRunningData data) throws JSONException {
        if (data == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalDistance", String.valueOf(data.getTotalDistance()));
        jsonObject.put("totalFrequency", String.valueOf(data.getTotalFrequency()));
        jsonObject.put("totalTime", String.valueOf(data.getTotalTime()));
        jsonObject.put("bestDistance", String.valueOf(data.getBestDistance()));
        jsonObject.put("bestTime", String.valueOf(data.getBestTime()));
        jsonObject.put("bestPace", String.valueOf(data.getBestPace()));
        jsonObject.put("best10km", String.valueOf(data.getBest10KM()));
        jsonObject.put("bestHalfma", String.valueOf(data.getBestHalfMa()));
        jsonObject.put("bestMa", String.valueOf(data.getBestMa()));
        jsonObject.put("uid", String.valueOf(data.getUid()));

        return jsonObject;
    }

}