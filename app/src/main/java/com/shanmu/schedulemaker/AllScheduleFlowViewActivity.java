package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shanmu.schedulemaker.models.DateTimeslotAndGoal;
import com.shanmu.schedulemaker.models.TimeSlot;
import com.shanmu.schedulemaker.utils.DateUtils;
import com.shanmu.schedulemaker.utils.DbHelper;
import com.shanmu.schedulemaker.utils.adapter.AllScheduleViewAdapter;

import java.util.ArrayList;

public class AllScheduleFlowViewActivity extends AppCompatActivity {

    DbHelper dbHelper;
    MenuItem menuItem;
    BottomNavigationView bottomNavigationView;

    ArrayList<DateTimeslotAndGoal> listOfDateTimeslotAndGoal;
    AllScheduleViewAdapter arrayAdapter;
    ListView listViewOfDateTimeAndGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedule_flow_view);
        listOfDateTimeslotAndGoal = new ArrayList<>();
        listViewOfDateTimeAndGoal = findViewById(R.id.listOfDateTimeGoalView);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationHandler();

        dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.grabAllDataFromSchedule();

        if (cursor.moveToFirst()) {
            while(cursor.moveToNext()) {

                DateTimeslotAndGoal dateTimeslotAndGoal = new DateTimeslotAndGoal();
                String date = dbHelper.getDateFromScheduleInDateTable(Integer.parseInt(cursor.getString(1)));
                dateTimeslotAndGoal.setDate(date);

                TimeSlot timeSlot = dbHelper.getTimeslotFromScheduleTaskTimeslotTable(Integer.parseInt(cursor.getString(3)));
                String fromTime = DateUtils.convertTo12HourFormat(timeSlot.getFrom());
                String toTime = DateUtils.convertTo12HourFormat(timeSlot.getTo());
                String finalTime = fromTime + " - " + toTime;

                dateTimeslotAndGoal.setTimeslotDisplay(finalTime);

                String goalName = dbHelper.getGoalFromScheduleInGoalTable(Integer.parseInt(cursor.getString(2)));
                dateTimeslotAndGoal.setGoalName(goalName);

                listOfDateTimeslotAndGoal.add(dateTimeslotAndGoal);
            }
        }

        cursor.close();

        arrayAdapter = new AllScheduleViewAdapter(this, listOfDateTimeslotAndGoal);
        listViewOfDateTimeAndGoal.setAdapter(arrayAdapter);

    }

    @Deprecated
    public void bottomNavigationHandler() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.progress:
                        startActivity(new Intent(getApplicationContext(), ProgressViewActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileViewActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
    }

}