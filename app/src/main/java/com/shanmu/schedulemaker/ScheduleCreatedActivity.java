package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScheduleCreatedActivity extends AppCompatActivity {

    Button checkCalendarBtn, scheduleViewBtn;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_created);

        checkCalendarBtn = findViewById(R.id.schedule_created_check_calendar_btn);
        scheduleViewBtn = (Button) findViewById(R.id.schedule_created_shedule_view_btn);
    }

    public void moveToScheduleView() {
        scheduleViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AllScheduleFlowViewActivity.class));
            }
        });
    }


}