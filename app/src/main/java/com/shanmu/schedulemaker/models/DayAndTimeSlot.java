package com.shanmu.schedulemaker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DayAndTimeSlot implements Parcelable {

    private String day;
    private String timeslot;

    public DayAndTimeSlot(String day, String timeslot) {
        this.day = day;
        this.timeslot = timeslot;
    }

    public DayAndTimeSlot(Parcel in) {
        day = in.readString();
        timeslot = in.readString();
    }

    public DayAndTimeSlot(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(day);
        parcel.writeString(timeslot);
    }

    public static final Parcelable.Creator<DayAndTimeSlot> CREATOR = new Parcelable.Creator<DayAndTimeSlot>()
    {
        public DayAndTimeSlot createFromParcel(Parcel in)
        {
            return new DayAndTimeSlot(in);
        }
        public DayAndTimeSlot[] newArray(int size)
        {
            return new DayAndTimeSlot[size];
        }
    };
}
