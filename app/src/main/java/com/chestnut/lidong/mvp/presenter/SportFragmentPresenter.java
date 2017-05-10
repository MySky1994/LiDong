package com.chestnut.lidong.mvp.presenter;

import android.app.Fragment;

import com.chestnut.lidong.entity.UserRunningDataLocal;
import com.chestnut.lidong.mvp.dao.UserRunningDataLocalDao;
import com.chestnut.lidong.mvp.model.SportFragmentDataManager;
import com.chestnut.lidong.mvp.view.SportFragmentView;
import com.github.mikephil.charting.data.CombinedData;

import java.sql.SQLException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by AshZheng on 2016/9/9.
 */
public class SportFragmentPresenter extends BasePresenter {

    private SportFragmentView fragmentView;
    private SportFragmentDataManager dataManager;

    public SportFragmentPresenter(SportFragmentView fragmentView) throws SQLException {
        this.fragmentView = fragmentView;

        dataManager = new SportFragmentDataManager(new UserRunningDataLocalDao(((Fragment) fragmentView).getActivity()));
    }

    public void updateChartData() {

        Func1 dataAction = new Func1() {
            @Override
            public CombinedData call(Object o) {
                try {
                    return dataManager.getChartData();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        Action1 viewAction = new Action1<CombinedData>() {
            @Override
            public void call(CombinedData data) {
                fragmentView.showChart(data);
            }
        };

        Observable.just("")
                .observeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);
    }

    public void getData(){
        Func1 dataAction = new Func1() {
            @Override
            public UserRunningDataLocal call(Object o) {
                try {
                    return dataManager.getTodayData();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        Action1 viewAction = new Action1<UserRunningDataLocal>() {
            @Override
            public void call(UserRunningDataLocal o) {
                fragmentView.setTVData(o);
            }
        };
        Observable.just("")
                .observeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);
    }

    public void destroy() {
        fragmentView = null;
    }
}
