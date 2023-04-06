package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                startActivity(new Intent(getApplicationContext(), GetLocations.class));
            }
        }, 2000);

        dbHelper = new DbHelper(this);

//        Boolean insertResult = dbHelper.insertUser("testuser");
//        if (insertResult) {
//            Log.d("dbInsert", "inserted successfully");
//        } else {
//            Log.d("dbInsert", "insertion failed");
//        }

    }




}