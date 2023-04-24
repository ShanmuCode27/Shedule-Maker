package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    DbHelper dbHelper;
    EditText usernameInput;
    EditText roleInput;
    EditText passwordInput;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.signup_username_input);
        roleInput = findViewById(R.id.signup_role_input);
        passwordInput = findViewById(R.id.signp_password_input);
        submitBtn = findViewById(R.id.signup_submit_btn);

        dbHelper = new DbHelper(this);
        createUser();
    }

    public void createUser() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameEntered = usernameInput.getText().toString();
                String roleEntered = roleInput.getText().toString();
                String passwordEntered = passwordInput.getText().toString();


                if (userNameEntered == null) {
                    Toast.makeText(SignupActivity.this, "Enter valid username", Toast.LENGTH_SHORT);
                } else {
                    Boolean insertResult = dbHelper.insertUser(userNameEntered, roleEntered, passwordEntered);
                    if (insertResult) {
                        Log.d("dbInsert", "inserted successfully");
                        Intent splashScreen = new Intent(getApplicationContext(), SplashScreenActivity.class);
                        splashScreen.putExtra("username", userNameEntered);
                        startActivity(splashScreen);
                        new Handler().postDelayed(new Runnable() {

                            public void run() {
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        }, 2000);

                    } else {
                        Log.d("dbInsert", "insertion failed");
                    }
                }
            }
        });
    }
}