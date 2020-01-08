package com.study.android.wifi;

public class SingerItem {

    private String Date;
    private String Time;
    private int resId;
    private String Temp;
    private String Water;

    public SingerItem(String Date, String Time,int resId,String Temp, String Water){
        this.Date=Date;
        this.Time=Time;
        this.Temp=Temp;
        this.resId=resId;
        this.Water=Water;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public int getResId() {
        return resId;
    }

    public String getTemp() {
        return Temp;
    }

    public String getWater() {
        return Water;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public void setWater(String water) {
        Water = water;
    }
}
