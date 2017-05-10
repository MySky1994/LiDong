package com.chestnut.lidong.utils;

/**
 * Created by AshZheng on 2016/9/22.
 */
public class StringUtil {

    public static boolean isLegal(String s) {

        if (s.indexOf(' ') != -1) {
            return false;
        }
        if (s.indexOf('&') != -1) {
            return false;
        }
        if (s.indexOf('=') != -1) {
            return false;
        }
//        LogUtil.printLog(StringUtil.class, "return true");
        return true;
    }
}
