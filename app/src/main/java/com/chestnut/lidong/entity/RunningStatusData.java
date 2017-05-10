package com.chestnut.lidong.entity;

/**
 * Created by AshZheng on 2016/9/8.
 * 本类的作用是实现运动时间、运动距离和运动数据的封装
 */
public class RunningStatusData extends BaseEntity {

    private int time;
    private double distance;
    private double speed;

    public RunningStatusData() {
    }

    /**
     * @param time     时间 单位 s
     * @param distance 距离
     * @param speed    单次数据的速度
     */
    public RunningStatusData(int time, double distance, double speed) {
        this.time = time;
        this.distance = distance;
        this.speed = speed;
    }

    //开始计时
    public void addTime() {
        time++;
    }


    public void addToDistance(double distance) {
        this.distance += distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "RunningStatusData{" +
                "time=" + time +
                ", distance=" + distance +
                ", speed=" + speed +
                '}';
    }
}
