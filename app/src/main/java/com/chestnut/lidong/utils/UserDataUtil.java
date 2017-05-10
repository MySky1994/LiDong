package com.chestnut.lidong.utils;

/**
 * Created by AshZheng on 2016/9/22.
 */
public class UserDataUtil {

    public static String userIdToString(String userId) {

        String z = "0";
        while (userId.length() < 8) {
            userId = z + userId;
        }

        return "ID:" + userId;
    }
}
