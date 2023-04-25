package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.shanmu.schedulemaker.models.DayAndTimeAvailable;
import com.shanmu.schedulemaker.models.DayAndTimeSlot;
import com.shanmu.schedulemaker.models.GoalAndDeadline;
import com.shanmu.schedulemaker.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class ComputeSchedule extends AppCompatActivity {

    ArrayList<GoalAndDeadline> listOfGoalAndDeadline;
    ArrayList<DayAndTimeSlot> listOfDayAndTimeSlot;
    ArrayList<DayAndTimeAvailable> listOfAvailableTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_schedule);

        listOfGoalAndDeadline = this.getIntent().getParcelableArrayListExtra("listOfGoalAndDeadline");
        listOfDayAndTimeSlot = this.getIntent().getParcelableArrayListExtra("listOfDayAndTimeSlot");
        listOfAvailableTime = new ArrayList<>();

        String todayDate = getFormattedDate(DateUtils.getTodaysDate());

        for (DayAndTimeSlot item: listOfDayAndTimeSlot) {
            String timeSlot = item.getTimeslot();
            Long timeAvailable = DateUtils.getDifferenceBetweenTwoTimes(timeSlot.split("-")[0], timeSlot.split("-")[1]);

            DayAndTimeAvailable dayAndTimeAvailable = new DayAndTimeAvailable(item.getDay(), timeAvailable);
            listOfAvailableTime.add(dayAndTimeAvailable);
        }

        for (DayAndTimeAvailable item: listOfAvailableTime) {
            Log.d("intime", item.getDay() + " --- " + item.getTimeAvailable());
        }

    }

    public String getFormattedDate(String date) {
        String[] splitDate = date.split("\\s");
        String month = splitDate[0].trim();
        month = DateUtils.getMonthNumberFromString(month);
        String day = splitDate[1].trim();
        String year = splitDate[2].trim();

        return year + month + day;
    }
}