package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shanmu.schedulemaker.utils.DbHelper;

public class ProfileViewActivity extends AppCompatActivity {

    DbHelper dbHelper;
    TextView usernameText, roleText, countryText, addressText;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        usernameText = findViewById(R.id.profile_username);
        roleText = findViewById(R.id.profile_role);
        countryText = findViewById(R.id.profile_country);
        addressText = findViewById(R.id.profile_address);

        dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.retrieveUser();
        Cursor locationCursor = dbHelper.getUserLocation();
        if (cursor.moveToFirst()) {
            String username = cursor.getString(1);
            String role = cursor.getString(2);

            usernameText.setText(username);
            roleText.setText(role);
        }
        cursor.close();

        if (locationCursor.moveToFirst()) {
            String country = locationCursor.getString(4);
            String address = locationCursor.getString(5);

            countryText.setText(country);
            addressText.setText(address);
        }

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationHandler();
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