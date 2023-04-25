package com.shanmu.schedulemaker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GoalAndDeadline implements Parcelable {

    private String goal;
    private String deadline;
    private Integer estimatedHours;

    public GoalAndDeadline(String goal, String deadline, Integer estimatedHours) {
        this.goal = goal;
        this.deadline = deadline;
        this.estimatedHours = estimatedHours;
    }

    public GoalAndDeadline(Parcel in) {
        goal = in.readString();
        deadline = in.readString();
        estimatedHours = in.readInt();
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

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goal);
        parcel.writeString(deadline);
        parcel.writeInt(estimatedHours);
    }

    public static final Parcelable.Creator<GoalAndDeadline> CREATOR = new Parcelable.Creator<GoalAndDeadline>()
    {
        public GoalAndDeadline createFromParcel(Parcel in)
        {
            return new GoalAndDeadline(in);
        }
        public GoalAndDeadline[] newArray(int size)
        {
            return new GoalAndDeadline[size];
        }
    };
}
