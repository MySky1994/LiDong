package com.chestnut.lidong.mvp.view;

import com.chestnut.lidong.entity.UserRunningDataLocal;
import com.github.mikephil.charting.data.CombinedData;

/**
 * Created by AshZheng on 2016/9/9.
 */
public interface SportFragmentView extends BaseView {
    void showChart(CombinedData data);
    void setTVData(UserRunningDataLocal data);
}
