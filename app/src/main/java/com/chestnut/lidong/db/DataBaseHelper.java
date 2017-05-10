package com.chestnut.lidong.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chestnut.lidong.entity.UserInfo;
import com.chestnut.lidong.entity.UserRunningDataLocal;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AshZheng on 2016/9/9.
 */

//原生的数据库操作，需要继承SQLiteOpenHelper，这里我们需要继承OrmLiteSqliteOpenHelper

/*需要实现两个方法：
        1、onCreate(SQLiteDatabase database,ConnectionSource connectionSource)
        创建表，我们直接使用ormlite提供的TableUtils.createTable(connectionSource, User.class);进行创建~
        2、onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
        更新表，使用ormlite提供的TableUtils.dropTable(connectionSource, User.class, true);进行删除操作~
        删除完成后，别忘了，创建操作：onCreate(database, connectionSource);
        然后使用单例公布出一个创建实例的方法，getHelper用于获取我们的help实例*/

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static DataBaseHelper dataBaseHelper;

    private Map<String, Dao> daos = new HashMap<>();


    private static String DATEBASE_NAME = "lidong.db";
    private static int DATEBASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATEBASE_NAME, null, DATEBASE_VERSION);
    }

    //
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, UserRunningDataLocal.class);
            TableUtils.createTable(connectionSource, UserInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, UserRunningDataLocal.class, true);
            TableUtils.dropTable(connectionSource, UserInfo.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DataBaseHelper getDBHelper(Context context) {
        context = context.getApplicationContext();
        if (dataBaseHelper == null) {
            synchronized (DataBaseHelper.class) {
                if (dataBaseHelper == null) {
                    dataBaseHelper = new DataBaseHelper(context);
                }
            }
        }
        return dataBaseHelper;
    }

    /**
     * 获得Dao
     *
     * @return
     * @throws SQLException
     */

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        //得到类的简写名称
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }

        return dao;
    }

    /**
     * 释放资源
     */

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
