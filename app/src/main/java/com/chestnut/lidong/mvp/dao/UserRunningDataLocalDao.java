package com.chestnut.lidong.mvp.dao;

import android.content.Context;

import com.chestnut.lidong.entity.UserRunningDataLocal;
import com.chestnut.lidong.db.DataBaseHelper;
import com.chestnut.lidong.utils.LogUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by AshZheng on 2016/9/9.
 */
public class UserRunningDataLocalDao {

    private Context context;
    private Dao<UserRunningDataLocal, Integer> runningDataDaoOpe;
    private DataBaseHelper dataBaseHelper;

    public UserRunningDataLocalDao(Context context) throws SQLException {
        this.context = context;

        dataBaseHelper = DataBaseHelper.getDBHelper(context);
        runningDataDaoOpe = dataBaseHelper.getDao(UserRunningDataLocal.class);
    }

    public void add(UserRunningDataLocal userRunningDataLocal) throws SQLException {
        runningDataDaoOpe.create(userRunningDataLocal);
    }

    public void update(UserRunningDataLocal userRunningDataLocal) throws SQLException {
        runningDataDaoOpe.update(userRunningDataLocal);

    }

    public void createOrUpdate(UserRunningDataLocal userRunningDataLocal) throws SQLException {
        LogUtil.printLog(this.getClass(), "createOrUpdate");
        runningDataDaoOpe.createOrUpdate(userRunningDataLocal);
    }

    public UserRunningDataLocal getById(int id) throws SQLException {
        return runningDataDaoOpe.queryForId(id);
    }

    public List<UserRunningDataLocal> queryAll() throws SQLException {
        return runningDataDaoOpe.queryBuilder().orderBy("date", false).query();
    }

    public List<UserRunningDataLocal> queryByProperty(String columnName, Object obj) throws SQLException {
        return runningDataDaoOpe.queryBuilder().where().eq(columnName, obj).query();
    }

    public void clearAll() throws SQLException {
        runningDataDaoOpe.deleteBuilder().delete();
    }
}
