package com.chestnut.lidong.mvp.view;

import com.chestnut.lidong.entity.RunningStatusData;
import com.chestnut.lidong.entity.UserRunningDataLocal;

/**
 * Created by AshZheng on 2016/9/10.
 */
public interface RunningActivityView extends BaseView {

    RunningStatusData getRunningData();
}
