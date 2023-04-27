package com.shanmu.schedulemaker.models;

import java.util.List;

public class ScheduleDateWithTimeSlots {

    private String date;
    private List<TimeSlot> timeSlots;

    public ScheduleDateWithTimeSlots(String date, List<TimeSlot> timeSlots) {
        this.date = date;
        this.timeSlots = timeSlots;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
