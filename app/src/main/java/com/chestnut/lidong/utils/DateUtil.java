package com.chestnut.lidong.utils;

import java.util.Calendar;

/**
 * Created by AshZheng on 2016/9/10.
 */
public class DateUtil {


    public static boolean isOneDay(int s1, int s2) {
        return s1 == s2;
    }

    /**
     * 获取当前时间，格式为2015 10 09（去除空格）
     *
     * @return
     */
    public static int getDate() {
        int year, month, day;

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH) + 1;

        day = calendar.get(Calendar.DAY_OF_MONTH);

        return year * 10000 + month * 100 + day;
    }
}

