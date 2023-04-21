package com.shanmu.schedulemaker.models;

import java.io.Serializable;

public class GoalAndDeadline implements Serializable {

    private String goal;
    private String deadline;

    public GoalAndDeadline(String goal, String deadline) {
        this.goal = goal;
        this.deadline = deadline;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}
