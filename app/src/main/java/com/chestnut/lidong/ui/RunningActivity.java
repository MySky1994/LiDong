package com.chestnut.lidong.ui;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.chestnut.lidong.LiDongApplication;
import com.chestnut.lidong.R;
import com.chestnut.lidong.callback.Service2ActivitiyCallback;
import com.chestnut.lidong.entity.RunningStatusData;
import com.chestnut.lidong.entity.UserRunningData;
import com.chestnut.lidong.mvp.presenter.RunningActivityPresenter;
import com.chestnut.lidong.mvp.view.RunningActivityView;
import com.chestnut.lidong.service.LocationService;
import com.chestnut.lidong.utils.RunningDataUtil;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RunningActivity extends BaseActivity implements Service2ActivitiyCallback, RunningActivityView {

    private LiDongApplication application;
    private RunningActivityPresenter presenter;

    private UserRunningData userRunningData;//服务器保存的运动记录
    private boolean is10km = false, isHalfMa = false, isMa = false;

    private TextView distance_TV;
    private TextView time_TV;
    private TextView speed_TV;
    private TextView pace_TV;
    private TextView cal_TV;
    private Button startBT;
    private Button endBT;
    private MapView mapView = null;
    private BaiduMap baiduMap;

    private int runningStatus = STOP;//运动状态

    private LocationService.LocationServiceBinder myBinder;

    //绘制轨迹
    //LatLng用这个类的作用是为在地图上添加覆盖物做好准备，因为在地图上绘制轨迹也属于添加覆盖物
    private LatLng lastLocation;
    private boolean isFirstLocation = true;

    public final static int RUNNING = 1;
    public final static int PAUSE = 2;
    public final static int STOP = 3;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder = (LocationService.LocationServiceBinder) iBinder;
            //绑定回调接口
            myBinder.registerCallBack(RunningActivity.this);
            //开始定位
            myBinder.startLocation();
            runningStatus = myBinder.getRunningStatus();
            //绘制轨迹
            drawLine(myBinder.getLocationList());

            //解决轨迹不连续
            List<LatLng> locationList = myBinder.getLocationList();
            if (locationList != null && locationList.size() > 0) {
                lastLocation = locationList.get(locationList.size() - 1);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
              //该方法可以在活动和服务解除绑定时进行调用，此方法可以进行一些收尾工作
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        initView();
        initLocation();
        initData();

        try {
            presenter = new RunningActivityPresenter(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        application = (LiDongApplication) getApplication();
        if (application != null) {
            userRunningData = application.getUserRunningData();
        }
    }

    private void initLocation() {
        baiduMap = mapView.getMap();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));

        //绑定定位服务
        Intent bindIntent = new Intent(this, LocationService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void initView() {

        startBT = (Button) findViewById(R.id.activity_running_start_bt);
        endBT = (Button) findViewById(R.id.activity_running_stop_bt);
        mapView = (MapView) findViewById(R.id.activity_running_mapview);
        distance_TV = (TextView) findViewById(R.id.activity_running_distance_tv);
        time_TV = (TextView) findViewById(R.id.activity_running_time_tv);
        speed_TV = (TextView) findViewById(R.id.activity_running_speed_tv);
        pace_TV = (TextView) findViewById(R.id.activity_running_pace_tv);
        cal_TV = (TextView) findViewById(R.id.activity_running_cal_tv);


        ((RadioGroup) findViewById(R.id.activity_running_rg)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.activity_running_rbt1:
                        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
                        break;
                    case R.id.activity_running_rbt2:
                        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, null));
                        break;
                    case R.id.activity_running_rbt3:
                        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
                        break;
                }

            }
        });
        startBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (runningStatus) {
                    case RUNNING:
                        startBT.setText("继续");
                        runningStatus = PAUSE;
                        if (myBinder != null) {
                            myBinder.updateRunningStatus(runningStatus);
                        }
                        isFirstLocation = true;
                        //暂停跑步...
                        break;

                    case PAUSE:
                        startBT.setText("暂停");
                        runningStatus = RUNNING;
                        if (myBinder != null) {
                            myBinder.updateRunningStatus(runningStatus);
                        }
                        //继续跑步...
                        break;

                    case STOP:
                        startBT.setText("暂停");
                        runningStatus = RUNNING;
                        if (myBinder != null) {
                            myBinder.updateRunningStatus(runningStatus);
                            myBinder.startRunning();
                        }
                        //开始跑步
                        break;
                }
            }
        });

        endBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBT.setText("开始");
                runningStatus = STOP;

                if (Double.parseDouble(distance_TV.getText().toString()) < 0.1) {
                    showResultDialogNotGood();
                } else {
                    showResultDialogDetail();
                    if (Double.parseDouble(speed_TV.getText().toString()) < 100) {
                        try {
                            presenter.saveData(userRunningData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //保存数据
                }

                if (myBinder != null) {
                    myBinder.updateRunningStatus(runningStatus);
                    myBinder.finishRunning();
                }
                //清除轨迹
                mapView.getMap().clear();

                //结束跑步
            }
        });

        findViewById(R.id.activity_running_camera_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicFromCamera();
            }
        });

    }

    private void drawLine(List<LatLng> drawLineList) {
        if (drawLineList == null || drawLineList.size() < 2) {
            return;
        }

        OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAAFF0000).points(drawLineList);
        baiduMap.addOverlay(ooPolyline);
    }

    //完成在地图上绘制轨迹
    private void drawLine(LatLng location) {

        if (lastLocation == null)
            return;

        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(lastLocation);
        latLngList.add(location);
        OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAAFF0000).points(latLngList);
        baiduMap.addOverlay(ooPolyline);
    }

    private void showResultDialogDetail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("本次运动结果");
        View view = LayoutInflater.from(this).inflate(R.layout.activity_running_result_dialog, null);
        builder.setView(view);

        ((TextView) view.findViewById(R.id.activity_running_result_dialog_distance_tv)).setText(distance_TV.getText());
        ((TextView) view.findViewById(R.id.activity_running_result_dialog_time_tv)).setText(time_TV.getText());
        ((TextView) view.findViewById(R.id.activity_running_result_dialog_speed_tv)).setText(speed_TV.getText());
        ((TextView) view.findViewById(R.id.activity_running_result_dialog_pace_tv)).setText(pace_TV.getText());
        ((TextView) view.findViewById(R.id.activity_running_result_dialog_cal_tv)).setText(cal_TV.getText());

        builder.setPositiveButton("继续跑步", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startBT.setText("再跑会儿");
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RunningActivity.this.finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void showResultDialogNotGood() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("你真的尽力了么？不能偷懒哦(⊙o⊙)！");
        builder.setCancelable(false);
        builder.setPositiveButton("我再跑一次♪(^∇^*)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RunningActivity.this.finish();
            }
        });

        builder.show();
    }

    /**
     * 从摄像头中获取，返回结果会在onActivityResult()中
     */
    private void selectPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    @Override
    public void setLocation(MyLocationData locationData) {

        baiduMap.setMyLocationData(locationData);

        LatLng ll = new LatLng(locationData.latitude, locationData.longitude);

        if (isFirstLocation) {
            isFirstLocation = false;
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
        if (runningStatus == RUNNING) {
            drawLine(ll);
        }
        lastLocation = ll;
    }

    @Override
    public void updateRunningData(RunningStatusData runningStatusData) {
        distance_TV.setText(RunningDataUtil.distanceToTextView(runningStatusData.getDistance()));
        time_TV.setText(RunningDataUtil.timeToTextView(runningStatusData.getTime()));
        speed_TV.setText(RunningDataUtil.calculateSpeed(runningStatusData.getDistance(), runningStatusData.getTime()));
        pace_TV.setText(RunningDataUtil.calculatePace(runningStatusData.getDistance(), runningStatusData.getTime()));
        cal_TV.setText(RunningDataUtil.calculateCal(runningStatusData.getDistance()));

        if (userRunningData.getBestPace() > RunningDataUtil.calculatePaceReturnDouble(runningStatusData.getDistance(), runningStatusData.getTime())) {
            userRunningData.setBestPace(RunningDataUtil.calculatePaceReturnDouble(runningStatusData.getDistance(), runningStatusData.getTime()));
        }
        if (!is10km && runningStatusData.getDistance() > 10000.0) {
            is10km = true;
            if (userRunningData.getBest10KM() > runningStatusData.getTime()) {
                userRunningData.setBest10KM(runningStatusData.getTime());
            }
        }
        if (!isHalfMa && runningStatusData.getDistance() > 21097.5) {
            isHalfMa = true;
            if (userRunningData.getBestHalfMa() > runningStatusData.getTime()) {
                userRunningData.setBestHalfMa(runningStatusData.getTime());
            }
        }
        if (!isMa && runningStatusData.getDistance() > 42195.0) {
            isMa = true;
            if (userRunningData.getBestMa() > runningStatusData.getTime()) {
                userRunningData.setBestMa(runningStatusData.getTime());
            }
        }
    }

    @Override
    public RunningStatusData getRunningData() {
        if (myBinder != null) {
            return myBinder.getRunningStatusData();
        }
        return null;
    }


    @Override
    protected void onDestroy() {
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        if (runningStatus == STOP && myBinder != null) {
            myBinder.destroyService();
        }

        presenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }


}
