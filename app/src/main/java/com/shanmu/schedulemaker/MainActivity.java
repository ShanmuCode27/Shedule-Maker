package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        dbHelper = new DbHelper(this);
//        String user = dbHelper.retrieveUser();
//        Intent splashScreen = new Intent(MainActivity.this, SplashScreenActivity.class);
//
//        if (user != null) {
//            splashScreen.putExtra("username", user);
//            startActivity(splashScreen);
//            new Handler().postDelayed(new Runnable() {
//
//                public void run() {
//                    startActivity(new Intent(getApplicationContext(), GetLocations.class));
//                }
//            }, 2000);
//
//        } else {
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        }

    }

}