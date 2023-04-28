package com.shanmu.schedulemaker.models;

import java.sql.Time;
import java.util.List;

public class DayWithTask {

    private String day;
    private String date;

    public DayWithTask(String day, String date) {
        this.day = day;
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




}
