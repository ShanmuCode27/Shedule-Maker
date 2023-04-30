package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shanmu.schedulemaker.utils.DbHelper;

public class LoginActivity extends AppCompatActivity {

    DbHelper dbHelper;
    EditText usernameInput;
    EditText passwordInput;
    Button proceedBtn, moveToSignBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbHelper(this);

        usernameInput = findViewById(R.id.login_username_input);
        passwordInput = findViewById(R.id.login_password_input);
        proceedBtn  = (Button) findViewById(R.id.login_submit_btn);
        moveToSignBtn = findViewById(R.id.login_signup_btn);

        onLoginSubmit();
        moveToSignUpScreen();
    }

    public void onLoginSubmit() {
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameEntered = usernameInput.getText().toString();
                String passwordEntered = passwordInput.getText().toString();
                if (userNameEntered == null) {
                    Toast.makeText(LoginActivity.this, "Enter valid username", Toast.LENGTH_SHORT);
                } else {
                    Boolean insertResult = dbHelper.loginUser(userNameEntered, passwordEntered);
                    if (insertResult) {
                        Intent splashScreen = new Intent(getApplicationContext(), SplashScreenActivity.class);
                        splashScreen.putExtra("username", userNameEntered);
                        startActivity(splashScreen);
                        new Handler().postDelayed(new Runnable() {

                            public void run() {

                                Cursor cursor = dbHelper.grabAllDataFromSchedule();
                                if (cursor.moveToFirst()) {
                                    cursor.close();
                                    startActivity(new Intent(getApplicationContext(), AllScheduleFlowViewActivity.class));
                                } else {
                                    cursor.close();
                                    startActivity(new Intent(getApplicationContext(), GetLocations.class));
                                }

                            }
                        }, 2000);

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        Log.d("loginfailed", "login failed");
                    }
                }
            }
        });
    }

    public void moveToSignUpScreen() {
        moveToSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });
    }
}