package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreenActivity extends AppCompatActivity {

    TextView welcomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        welcomeUser = findViewById(R.id.user_welcome);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        welcomeUser.setText("Welcome " + username);
    }
}