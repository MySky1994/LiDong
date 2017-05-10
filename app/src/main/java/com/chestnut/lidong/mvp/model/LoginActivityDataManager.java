package com.chestnut.lidong.mvp.model;

import com.chestnut.lidong.entity.UserInfo;
import com.chestnut.lidong.mvp.dao.UserInfoDao;

import java.sql.SQLException;

/**
 * Created by AshZheng on 2016/9/19.
 */
public class LoginActivityDataManager extends BaseDataManager {

    private UserInfoDao userInfoDao;

    public LoginActivityDataManager(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public void savaUserInfo(UserInfo userInfo) throws SQLException {
        userInfoDao.deleteByProperty(userInfo, "username", userInfo.getUsername());
        userInfoDao.add(userInfo);
    }


}
