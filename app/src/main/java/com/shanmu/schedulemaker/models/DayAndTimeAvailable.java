package com.shanmu.schedulemaker.models;

public class DayAndTimeAvailable {

    private String day;
    private Long timeAvailable;

    public DayAndTimeAvailable(String day, Long timeAvailable) {
        this.day = day;
        this.timeAvailable = timeAvailable;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getTimeAvailable() {
        return timeAvailable;
    }

    public void setTimeAvailable(Long timeAvailable) {
        this.timeAvailable = timeAvailable;
    }

}
