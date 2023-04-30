package com.shanmu.schedulemaker.models;

public class GoalProgress {

    private String goalName;
    private String progress;

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        String progressInStr = String.valueOf(progress) + "%";
        this.progress = progressInStr;
    }

}
