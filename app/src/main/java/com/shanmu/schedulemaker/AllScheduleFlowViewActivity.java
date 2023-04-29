package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import com.shanmu.schedulemaker.models.DateTimeslotAndGoal;
import com.shanmu.schedulemaker.utils.DbHelper;

import java.util.ArrayList;

public class AllScheduleFlowViewActivity extends AppCompatActivity {

    DbHelper dbHelper;

    ArrayList<DateTimeslotAndGoal> listOfDateTimeslotAndGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedule_flow_view);

        dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.grabAllDataFromSchedule();
//
//        if (cursor.moveToFirst()) {
//            while(cursor.moveToNext()) {
//
//                cursor.getString()
//
//            }
//        }

    }
}