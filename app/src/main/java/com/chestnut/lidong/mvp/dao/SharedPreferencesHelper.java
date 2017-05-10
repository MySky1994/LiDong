package com.chestnut.lidong.mvp.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.chestnut.lidong.constant.AppConstant;

/**
 * Created by AshZheng on 2016/9/22.
 */
public class SharedPreferencesHelper {

    public static String IS_FIRST_OPEN = "isFirstOpen";
    public static String USER_ID = "userId";


    public static boolean readBoolean(Context context, String key) {
        return context.getSharedPreferences(AppConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getBoolean(key, true);
    }
    public static void writeBoolean(Context context, String key, boolean b){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(AppConstant.SHARED_PREFERENCES_NAME, Context.MODE_APPEND).edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public static String readString(Context context, String key){
        return context.getSharedPreferences(AppConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(key, null);
    }
    public static void writeString(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(AppConstant.SHARED_PREFERENCES_NAME, Context.MODE_APPEND).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int readInt(Context context, String key){
        return context.getSharedPreferences(AppConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(key, AppConstant.ERROR);
    }
    public static void writeInt(Context context, String key, int value){
        SharedPreferences.Editor editor = context.getSharedPreferences(AppConstant.SHARED_PREFERENCES_NAME, Context.MODE_APPEND).edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
