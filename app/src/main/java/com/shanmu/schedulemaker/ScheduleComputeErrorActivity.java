package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ScheduleComputeErrorActivity extends AppCompatActivity {

    TextView timeRequiredText, timeProvidedText, differenceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_compute_error);

        timeProvidedText = findViewById(R.id.schedule_computer_time_provided_change);
        timeRequiredText = findViewById(R.id.schedule_computer_time_required_change);
        differenceText = findViewById(R.id.schedule_computer_difference_change);

        String timeRequired = getIntent().getStringExtra("time-required");
        String timeProvided = getIntent().getStringExtra("time-provided");
        String differenceInTime = getIntent().getStringExtra("difference");

        timeProvidedText.setText(timeProvided);
        timeRequiredText.setText(timeRequired);
        differenceText.setText(differenceInTime);

    }
}