package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MarkGoalProgressActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView goalName, goalTime;
    Button doneBtn;
    CheckBox didBox, didNotBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_goal_progress);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationHandler();

        goalName = findViewById(R.id.mark_progress_goal_name);
        goalTime = findViewById(R.id.mark_progress_goal_time);
        didBox = findViewById(R.id.mark_progress_checkbox_did);
        didNotBox = findViewById(R.id.mark_progress_checkbox_did_not);
        doneBtn = findViewById(R.id.mark_progress_done);

        String goal = getIntent().getStringExtra("goal");
        String timeDisplay = getIntent().getStringExtra("timeSlot");

        goalName.setText(goal);
        goalTime.setText(timeDisplay);
        doneClick();
    }

    public void doneClick() {
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (didBox.isChecked()) {
                    // add progress
                }

                startActivity(new Intent(getApplicationContext(), AllScheduleFlowViewActivity.class));
            }
        });
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