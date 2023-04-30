package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shanmu.schedulemaker.models.GoalProgress;
import com.shanmu.schedulemaker.utils.DbHelper;
import com.shanmu.schedulemaker.utils.adapter.AllProgressViewAdapter;

import java.util.ArrayList;

public class ProgressViewActivity extends AppCompatActivity {

    DbHelper dbHelper;
    BottomNavigationView bottomNavigationView;
    ArrayList<GoalProgress> listOfGoalProgress;
    AllProgressViewAdapter adapter;
    ListView listOfGoalProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);

        listOfGoalProgress = new ArrayList<>();
        listOfGoalProgressView = findViewById(R.id.listViewOfGoalProgress);
        dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.grabAllDataFromProgress();

        if (cursor.moveToFirst()) {
            while(cursor.moveToNext()) {
                GoalProgress goalProgress = new GoalProgress();
                goalProgress.setGoalName(cursor.getString(1));
                goalProgress.setProgress(Double.parseDouble(cursor.getString(2)));

                listOfGoalProgress.add(goalProgress);
            }
        }

        cursor.close();


        adapter = new AllProgressViewAdapter(this, listOfGoalProgress);
        listOfGoalProgressView.setAdapter(adapter);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.progress);
        bottomNavigationHandler();
    }

    @Deprecated
    public void bottomNavigationHandler() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), AllScheduleFlowViewActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.progress:
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