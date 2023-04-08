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

        dbHelper = new DbHelper(this);
        String user = dbHelper.retrieveUser();
        Intent splashScreen = new Intent(MainActivity.this, SplashScreenActivity.class);

        if (user != null) {
            splashScreen.putExtra("username", user);
            startActivity(splashScreen);
            new Handler().postDelayed(new Runnable() {

                public void run() {
                    startActivity(new Intent(getApplicationContext(), GetLocations.class));
                }
            }, 2000);

        } else {
            setContentView(R.layout.activity_main);
            EditText usernameInput = findViewById(R.id.inputUsername);
            Button proceedBtn = (Button) findViewById(R.id.proceedToSplashScreenWithUserInputBtn);

            proceedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userNameEntered = usernameInput.getText().toString();
                    if (userNameEntered == null) {
                        Toast.makeText(MainActivity.this, "Enter valid username", Toast.LENGTH_SHORT);
                    } else {
                        Boolean insertResult = dbHelper.insertUser(userNameEntered);
                        if (insertResult) {
                            Log.d("dbInsert", "inserted successfully");
                            splashScreen.putExtra("username", userNameEntered);
                            startActivity(splashScreen);
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    startActivity(new Intent(getApplicationContext(), GetLocations.class));
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

}