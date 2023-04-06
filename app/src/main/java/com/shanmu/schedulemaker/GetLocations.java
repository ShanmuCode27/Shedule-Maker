package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetLocations extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_locations);

        Button proceedToMap = findViewById(R.id.proceedToMapBtn);

        proceedToMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent moveToMap = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(moveToMap);
            }
        });

    }
}