package com.chestnut.lidong.entity;

/**
 * Created by AshZheng on 2016/9/9.
 */
public class UserRunningData {

    private int uid;
    private double totalDistance;
    private int totalFrequency;
    private int totalTime;

    private double bestDistance;
    private int bestTime;
    private double bestPace;
    private int best10KM;
    private int bestHalfMa;
    private int bestMa;

    public UserRunningData() {
    }

    public UserRunningData(int uid, double totalDistance, int totalFrequency, int totalTime, double bestDistance, int bestTime, double bestPace, int best10KM, int bestHalfMa, int bestMa) {
        this.uid = uid;
        this.totalDistance = totalDistance;
        this.totalFrequency = totalFrequency;
        this.totalTime = totalTime;
        this.bestDistance = bestDistance;
        this.bestTime = bestTime;
        this.bestPace = bestPace;
        this.best10KM = best10KM;
        this.bestHalfMa = bestHalfMa;
        this.bestMa = bestMa;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getTotalDistance() {
        return totalDistance;

    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    //跑步总次数
    public int getTotalFrequency() {
        return totalFrequency;
    }

    public void setTotalFrequency(int totalFrequency) {
        this.totalFrequency = totalFrequency;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public double getBestDistance() {
        return bestDistance;
    }

    public void setBestDistance(double bestDistance) {
        this.bestDistance = bestDistance;
    }

    public int getBestTime() {
        return bestTime;
    }

    public void setBestTime(int bestTime) {
        this.bestTime = bestTime;
    }

    public double getBestPace() {
        return bestPace;
    }

    public void setBestPace(double bestPace) {
        this.bestPace = bestPace;
    }

    public int getBest10KM() {
        return best10KM;
    }

    public void setBest10KM(int best10KM) {
        this.best10KM = best10KM;
    }

    public int getBestHalfMa() {
        return bestHalfMa;
    }

    public void setBestHalfMa(int bestHalfMa) {
        this.bestHalfMa = bestHalfMa;
    }

    public int getBestMa() {
        return bestMa;
    }

    public void setBestMa(int bestMa) {
        this.bestMa = bestMa;
    }

    @Override
    public String toString() {
        return "UserRunningData{" +
                "uid=" + uid +
                ", totalDistance=" + totalDistance +
                ", totalFrequency=" + totalFrequency +
                ", totalTime=" + totalTime +
                ", bestDistance=" + bestDistance +
                ", bestTime=" + bestTime +
                ", bestPace=" + bestPace +
                ", best10KM=" + best10KM +
                ", bestHalfMa=" + bestHalfMa +
                ", bestMa=" + bestMa +
                '}';
    }
}
