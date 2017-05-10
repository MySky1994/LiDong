package com.chestnut.lidong.callback;

import com.baidu.mapapi.map.MyLocationData;
import com.chestnut.lidong.entity.RunningStatusData;

/**
 * Created by AshZheng on 2016/9/7.
 */
public interface Service2ActivitiyCallback {

    public void setLocation(MyLocationData locationData);
    public void updateRunningData(RunningStatusData runningStatusData);
}
