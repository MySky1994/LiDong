package com.chestnut.lidong.mvp.model;

import com.chestnut.lidong.entity.UserRunningDataLocal;
import com.chestnut.lidong.mvp.dao.UserRunningDataLocalDao;
import com.chestnut.lidong.utils.LogUtil;
import com.chestnut.lidong.utils.RunningDataUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by AshZheng on 2016/9/10.
 */
public class RunningActivityDataManager extends BaseDataManager {

    private UserRunningDataLocalDao userRunningDataLocalDao;

    public RunningActivityDataManager(UserRunningDataLocalDao userRunningDataLocalDao) {
        this.userRunningDataLocalDao = userRunningDataLocalDao;
    }

    public void saveRunningData(UserRunningDataLocal data) throws SQLException {

        List<UserRunningDataLocal> dataList = userRunningDataLocalDao.queryByProperty("date", data.getDate());
        if (dataList == null || dataList.size() == 0) {
            userRunningDataLocalDao.add(data);

        } else {
            UserRunningDataLocal lastData = dataList.get(0);
            data.setId(lastData.getId());

            data.setDistance(data.getDistance() + lastData.getDistance());
            data.setTime(data.getTime() + lastData.getTime());
            data.setSpeed(Double.parseDouble(RunningDataUtil.calculateSpeed(data.getDistance(), data.getTime())));

            userRunningDataLocalDao.update(data);
        }

//        getAll();
    }

    public void getAll() throws SQLException {
        List<UserRunningDataLocal> dataList = userRunningDataLocalDao.queryAll();

        for (UserRunningDataLocal d : dataList) {
            LogUtil.printLog(this.getClass(), d.toString());
        }
    }
}
