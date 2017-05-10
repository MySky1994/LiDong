package com.chestnut.lidong.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by AshZheng on 2016/9/9.
 */

@DatabaseTable(tableName = "running_data_local")
public class UserRunningDataLocal extends BaseEntity {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "date")
    private int date;
    @DatabaseField(columnName = "distance")
    private double distance;
    @DatabaseField(columnName = "speed")
    private double speed;
    @DatabaseField(columnName = "time")
    private int time;

    public UserRunningDataLocal() {
    }

    @Override
    public String toString() {
        return "UserRunningDataLocal{" +
                "id=" + id +
                ", date=" + date +
                ", distance=" + distance +
                ", speed=" + speed +
                ", time=" + time +
                '}';
    }

    public UserRunningDataLocal(int date, double distance, double speed, int time) {
        this.date = date;
        this.distance = distance;
        this.speed = speed;
        this.time = time;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
