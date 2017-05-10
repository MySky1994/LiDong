package com.chestnut.lidong.utils;

import android.util.Log;

/**
 * Created by AshZheng on 2016/9/6.
 */
public class LogUtil {
    public static void printLog(Class claz, String message) {
        Log.d("myinfo", claz.getName() + ": " + message);
    }


}
