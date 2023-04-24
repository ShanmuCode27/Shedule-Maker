package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.shanmu.schedulemaker.models.DayAndTimeSlot;
import com.shanmu.schedulemaker.models.GoalAndDeadline;
import com.shanmu.schedulemaker.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

public class ComputeSchedule extends AppCompatActivity {

    ArrayList<GoalAndDeadline> listOfGoalAndDeadline;
    ArrayList<DayAndTimeSlot> listOfDayAndTimeSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_schedule);

        listOfGoalAndDeadline = this.getIntent().getParcelableArrayListExtra("listOfGoalAndDeadline");
        listOfDayAndTimeSlot = this.getIntent().getParcelableArrayListExtra("listOfDayAndTimeSlot");

        String todayDate = getFormattedDate(DateUtils.getTodaysDate());


        new Handler().postDelayed(new Runnable() {

            public void run() {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        }, 2000);

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