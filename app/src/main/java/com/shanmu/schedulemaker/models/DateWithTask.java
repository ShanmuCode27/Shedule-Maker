package com.shanmu.schedulemaker.models;

import java.util.List;

public class DateWithTask {

    private String date;
    private List<TimeSlot> timeSlots;

    public DateWithTask(String date, List<TimeSlot> generatedTimeSlotForDate) {
        this.date = date;
        this.timeSlots = generatedTimeSlotForDate;
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
