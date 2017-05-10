package com.chestnut.lidong.mvp.model;

import android.graphics.Color;

import com.chestnut.lidong.entity.UserRunningDataLocal;
import com.chestnut.lidong.mvp.dao.UserRunningDataLocalDao;
import com.chestnut.lidong.utils.DateUtil;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AshZheng on 2016/9/10.
 */
public class SportFragmentDataManager extends BaseDataManager {

    UserRunningDataLocalDao dao;

    public SportFragmentDataManager(UserRunningDataLocalDao dao) {
        this.dao = dao;
    }

    public UserRunningDataLocal getTodayData() throws SQLException {
        UserRunningDataLocal data;
        List<UserRunningDataLocal> dataList =  dao.queryByProperty("date", DateUtil.getDate());
        if(dataList==null || dataList.size()==0){
            data = new UserRunningDataLocal();
        }else {
            data = dataList.get(0);
        }

        return data;
    }

    public CombinedData getChartData() throws SQLException {
        List<UserRunningDataLocal> dataList = dao.queryAll();
//        List<UserRunningDataLocal> dataList = new ArrayList<>();
        dataList.add(new UserRunningDataLocal(20160910, 2350, 2.3, 7200));
        dataList.add(new UserRunningDataLocal(20160911, 1350, 1.3, 3200));
        dataList.add(new UserRunningDataLocal(20160912, 3350, 5.3, 4200));
        dataList.add(new UserRunningDataLocal(20160913, 750, 0.3, 600));
        dataList.add(new UserRunningDataLocal(20160914, 2750, 6.3, 7200));

        dataList.add(new UserRunningDataLocal(20160910, 2350, 2.3, 7200));
        dataList.add(new UserRunningDataLocal(20160911, 1350, 1.3, 3200));
        dataList.add(new UserRunningDataLocal(20160912, 3350, 5.3, 4200));
        dataList.add(new UserRunningDataLocal(20160913, 750, 0.3, 600));
        dataList.add(new UserRunningDataLocal(20160914, 2750, 6.3, 7200));

        dataList.add(new UserRunningDataLocal(20160910, 2350, 2.3, 7200));
        dataList.add(new UserRunningDataLocal(20160911, 1350, 1.3, 3200));
        dataList.add(new UserRunningDataLocal(20160912, 3350, 5.3, 4200));
        dataList.add(new UserRunningDataLocal(20160913, 750, 0.3, 600));
        dataList.add(new UserRunningDataLocal(20160914, 2750, 6.3, 7200));

        if (dataList == null || dataList.size() == 0) {
            return null;
        }
        if (dataList.size() > 15) {
            dataList = dataList.subList(0, 15);
        }

        CombinedData combinedData = new CombinedData();

        combinedData.setData(getBarData(dataList));
        combinedData.setData(getLineData(dataList));

        return combinedData;
    }

    public LineData getLineData(List<UserRunningDataLocal> dataList) {

        ArrayList<Entry> entries = new ArrayList<>();     //坐标点的集合

        int count = dataList.size();
        for (int i = 0; i < count; i++) {
            UserRunningDataLocal data = dataList.get(i);
            entries.add(new Entry(i, (float) data.getSpeed()));
        }

        int color = Color.rgb(32, 196, 226);

        LineDataSet set = new LineDataSet(entries, "速度");    //坐标线，LineDataSet(坐标点的集合, 线的描述或名称);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);     //以左边坐标轴为准 还是以右边坐标轴为基准
        set.setColor(color);
        set.setLineWidth(2.5f);
        set.setCircleColor(color);
        set.setCircleRadius(5f);
        set.setFillColor(color);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        LineData lineData = new LineData();
        lineData.addDataSet(set);

        return lineData;
    }

    public BarData getBarData(List<UserRunningDataLocal> dataList) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        int count = dataList.size();
        for (int i = 0; i < count; i++) {
            entries.add(new BarEntry(i, (float) (dataList.get(i).getDistance() / 1000.0)));
        }

        int textColor = Color.rgb(217, 104, 49);

        BarDataSet set = new BarDataSet(entries, "距离");
        set.setValueTextColor(textColor);
//        set.setColor(color);
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set.setValueTextSize(10f);

        float barWidth = 0.9f;

        BarData barData = new BarData(set);
        barData.setBarWidth(barWidth);

        return barData;
    }
}
