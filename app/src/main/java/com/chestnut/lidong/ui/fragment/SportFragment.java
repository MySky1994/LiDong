package com.chestnut.lidong.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chestnut.lidong.LiDongApplication;
import com.chestnut.lidong.R;
import com.chestnut.lidong.callback.Fragment2ActivityCallback;
import com.chestnut.lidong.entity.UserRunningDataLocal;
import com.chestnut.lidong.mvp.model.SportFragmentDataManager;
import com.chestnut.lidong.mvp.presenter.SportFragmentPresenter;
import com.chestnut.lidong.mvp.view.SportFragmentView;
import com.chestnut.lidong.service.LocationService;
import com.chestnut.lidong.ui.RunningActivity;
import com.chestnut.lidong.utils.LogUtil;
import com.chestnut.lidong.utils.RunningDataUtil;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by AshZheng on 2016/9/5.
 */
public class SportFragment extends BaseFragment implements SportFragmentView {

    private TextView gpsTV;
    private ImageView gpsIV;
    private TextView zongCiShuTV;
    private TextView zongLiChengTV;
    private TextView zongYongShiTV;
    private TextView stepTV;

    private CombinedChart combinedChart;
    private int gpsSignalStatus = GPS_SIGNAL_LEVEL_1;

    public static int GPS_SIGNAL_LEVEL_1 = 1;
    public static int GPS_SIGNAL_LEVEL_2 = 2;
    public static int GPS_SIGNAL_LEVEL_3 = 3;

    private Fragment2ActivityCallback myCallBack;
    private SportFragmentPresenter presenter;

    private LiDongApplication application;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View sportFragment = inflater.inflate(R.layout.fragment_sport, container, false);
        application = (LiDongApplication) getActivity().getApplication();

        try {
            presenter = new SportFragmentPresenter(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initview(sportFragment);
        setGPSListener();


        return sportFragment;
    }

    private void initview(View sportFragment) {

        gpsTV = (TextView) sportFragment.findViewById(R.id.fragment_sport_gps_tv);
        gpsIV = (ImageView) sportFragment.findViewById(R.id.fragment_sport_gps_iv);
        combinedChart = (CombinedChart) sportFragment.findViewById(R.id.fragment_sport_chart);
        zongCiShuTV = (TextView) sportFragment.findViewById(R.id.fragment_sport_zongcishu_tv);
        zongLiChengTV = (TextView) sportFragment.findViewById(R.id.fragment_sport_zonglicheng_tv);
        zongYongShiTV = (TextView) sportFragment.findViewById(R.id.fragment_sport_zongyongshi_tv);
        stepTV = (TextView) sportFragment.findViewById(R.id.fragment_sport_today_step_tv);

        sportFragment.findViewById(R.id.top_bar_open_drawer_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCallBack.openDrawerLayout();
            }
        });

        sportFragment.findViewById(R.id.start_running_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startServiceIntent = new Intent(getActivity(), LocationService.class);
                getActivity().startService(startServiceIntent);
                Intent intent = new Intent(getActivity(), RunningActivity.class);
                startActivity(intent);
            }
        });

        loadChartData();
    }

    //加载数据
    public void loadData() {
        loadChartData();
        presenter.getData();

        zongCiShuTV.setText(application.getUserRunningData().getTotalFrequency() + "");
        zongLiChengTV.setText(RunningDataUtil.distanceToTextView(application.getUserRunningData().getTotalDistance()));
        zongYongShiTV.setText(RunningDataUtil.timeToTextViewH(application.getUserRunningData().getTotalTime()));
    }

    public void loadChartData() {
        presenter.updateChartData();
    }


    @Override
    public void setTVData(UserRunningDataLocal data) {

        stepTV.setText((int)(data.getDistance() / 0.9) + "");

    }

    @Override
    public void showChart(CombinedData combinedData) {

        combinedChart.setPinchZoom(true);
        combinedChart.setDescription("最近的运动数据");
        //如果用户还有的运动数据的话，则显示下面的信息
        combinedChart.setNoDataTextDescription("快去跑步吧 O(∩_∩)O~");

        combinedChart.setDrawGridBackground(false);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setHighlightFullBarEnabled(false);
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });


        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value == 0) {
                    return "今天";
                }
                return (int) value + "";
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        if (combinedData != null) {
            xAxis.setAxisMaxValue(combinedData.getXMax() + 0.35f);
            xAxis.setAxisMinValue(combinedData.getXMin() - 0.35f);
        }

        Legend l = combinedChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        combinedChart.getAxisRight().setEnabled(false);

        combinedChart.setData(combinedData);
        combinedChart.invalidate();
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();

        LogUtil.printLog(this.getClass(), "更新数据！");
    }

    /**
     * 获取卫星数量
     */
    GpsStatus.Listener gpsListener;

    private void setGPSListener() {

        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        //判断是否已经打开GPS模块
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            //GPS模块打开，可以定位操作
            LogUtil.printLog(SportFragment.class, "GPS模块打开，可以定位操作");
        }

        //权限相关
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        gpsListener = new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int i) {
                GpsStatus gpsStatus = locationManager.getGpsStatus(null);// 取当前状态

                switch (i) {
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        break;
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        //卫星状态改变

                        //获取最大的卫星数（这个只是一个预设值）
                        int maxSatellites = gpsStatus.getMaxSatellites();
                        Iterator<GpsSatellite> it = gpsStatus.getSatellites().iterator();

                        //记录实际的卫星数目
                        int count = 0;
                        while (it.hasNext() && count <= maxSatellites) {
                            //保存卫星的数据到一个队列，用于刷新界面
                            it.next();
                            count++;
                        }
                        if (count > 14) {
                            if (gpsSignalStatus != GPS_SIGNAL_LEVEL_3) {
                                gpsSignalStatus = GPS_SIGNAL_LEVEL_3;
                                gpsIV.setImageResource(R.drawable.gps_icon_3);
                                gpsTV.setText("GPS 信号强");

                            }
                        } else if (count > 5) {
                            if (gpsSignalStatus != GPS_SIGNAL_LEVEL_2) {
                                gpsSignalStatus = GPS_SIGNAL_LEVEL_2;
                                gpsIV.setImageResource(R.drawable.gps_icon_2);
                                gpsTV.setText("GPS 信号中");
                            }

                        } else {
                            if (gpsSignalStatus != GPS_SIGNAL_LEVEL_1) {
                                gpsSignalStatus = GPS_SIGNAL_LEVEL_1;
                                gpsIV.setImageResource(R.drawable.gps_icon_1);
                                gpsTV.setText("GPS 信号弱");
                            }

                        }
                        break;
                    case GpsStatus.GPS_EVENT_STARTED:
                        break;
                    case GpsStatus.GPS_EVENT_STOPPED:
                        break;
                }
            }
        };

        // 设置状态监听回调函数。statusListener是监听的回调函数。
        locationManager.addGpsStatusListener(gpsListener);
        // 设置监听器，设置自动更新间隔这里设置1000ms，移动距离：0米。
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 3000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof Fragment2ActivityCallback)) {
            throw new IllegalStateException("MyFragment所在的Activity必须实现MyCallBack接口！");
        }
        myCallBack = (Fragment2ActivityCallback) activity;

        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        if (!(context instanceof Fragment2ActivityCallback)) {
            throw new IllegalStateException("MyFragment所在的Activity必须实现MyCallBack接口！");
        }
        myCallBack = (Fragment2ActivityCallback) context;

        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }


}
