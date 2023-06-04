package com.shanmu.schedulemaker.models;

public class TaskPerTimeSlot {

    private String goal;
    private TimeSlot timeSlot;

    public TaskPerTimeSlot() {
    }

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

    @Override
    public String toString() {
        return goal + " time slots " + timeSlot;
    }

}
