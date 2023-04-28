package com.shanmu.schedulemaker.models;

public class TaskPerTimeSlot {

    private String goal;
    private TimeSlot timeSlot;


    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

}
