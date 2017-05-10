package com.chestnut.lidong.mvp.dao;

import android.content.Context;

import com.chestnut.lidong.db.DataBaseHelper;
import com.chestnut.lidong.entity.UserInfo;
import com.chestnut.lidong.utils.LogUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by AshZheng on 2016/9/22.
 */
public class UserInfoDao {

    private Context context;
    private Dao<UserInfo, Integer> userInfoDaoOpe;
    private DataBaseHelper dataBaseHelper;

    public UserInfoDao(Context context) throws SQLException {
        this.context = context;

        dataBaseHelper = DataBaseHelper.getDBHelper(context);
        userInfoDaoOpe = dataBaseHelper.getDao(UserInfo.class);
    }

    public void add(UserInfo userInfo) throws SQLException {
        userInfoDaoOpe.create(userInfo);
    }

    public void update(UserInfo userInfo) throws SQLException {
        userInfoDaoOpe.update(userInfo);

    }

    public void deleteByProperty(UserInfo userInfo, String columnName, Object obj) throws SQLException {
        userInfoDaoOpe.delete(queryByProperty(columnName, obj));
    }

    public void createOrUpdate(UserInfo userInfo) throws SQLException {
        userInfoDaoOpe.createOrUpdate(userInfo);
    }

    public UserInfo getById(int id) throws SQLException {
        return userInfoDaoOpe.queryForId(id);
    }

    public List<UserInfo> queryAll() throws SQLException {
        return userInfoDaoOpe.queryBuilder().orderBy("date", false).query();
    }

    public List<UserInfo> queryByProperty(String columnName, Object obj) throws SQLException {
        return userInfoDaoOpe.queryBuilder().where().eq(columnName, obj).query();
    }

    public void clearAll() throws SQLException {
        userInfoDaoOpe.deleteBuilder().delete();
    }
}
