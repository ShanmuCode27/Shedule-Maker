package com.shanmu.schedulemaker.models;

import java.util.List;

public class ScheduleWithTasks {

    private String date;
    private List<TaskPerTimeSlot> listOfTaskPerTimeSlot;

    public ScheduleWithTasks(String date, List<TaskPerTimeSlot> listOfTaskPerTimeSlot) {
        this.date = date;
        this.listOfTaskPerTimeSlot = listOfTaskPerTimeSlot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TaskPerTimeSlot> getListOfTaskPerTimeSlot() {
        return listOfTaskPerTimeSlot;
    }

    public void setListOfTaskPerTimeSlot(List<TaskPerTimeSlot> listOfTaskPerTimeSlot) {
        this.listOfTaskPerTimeSlot = listOfTaskPerTimeSlot;
    }


}
