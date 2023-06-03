package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class UpcomingTaskNotificationActivity extends AppCompatActivity {

    TextView displayNotificationTxt;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_task_notification);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationHandler();

        String goal = getIntent().getStringExtra("goal");
        String timeRemaining = getIntent().getStringExtra("timeRemaining");

        displayNotificationTxt = findViewById(R.id.notification_upcoming_text);
        displayNotificationTxt.setText("You need to work on " + goal + "\n in " + timeRemaining + " minutes");

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