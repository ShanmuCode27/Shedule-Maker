package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetLocations extends AppCompatActivity implements SensorEventListener {

    private static final double SHAKE_THRESHOLD = 0.5;
    private static final long MIN_TIME_BETWEEN_SHAKES_MILLISECS = 25;
    private Boolean isFirstShake = true;
    private long mLastShakeTime;

    TextView textView;

    SensorManager sensorManager;
    Sensor shakeSensor;

    Boolean isSensorAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_locations);
        Button proceedToMapBtn = (Button) findViewById(R.id.proceedToMapBtn);
        textView = (TextView) findViewById(R.id.proceedToMapText);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            shakeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isSensorAvailable = true;
            textView.setText("Sensor Available, Shake your phone to open maps");
            proceedToMapBtn.setVisibility(View.VISIBLE);
        } else {
            isSensorAvailable = false;
            textView.setText("Your mobile does not have the required sensor, Click the button below to enter your home address");
            proceedToMapBtn.setVisibility(View.VISIBLE);
        }

        proceedToMapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveToMapScreen();
            }
        });

    }

    private void moveToMapScreen() {
        Intent moveToMap = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(moveToMap);
    }

    static int count = 0;
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if(!isFirstShake) {
                if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    double acceleration = Math.sqrt(Math.pow(x, 2) +
                            Math.pow(y, 2) +
                            Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;


                    if (acceleration > SHAKE_THRESHOLD) {
                        mLastShakeTime = curTime;
                        count++;
                        textView.setText("shake a little bit more");
                        if (count == 5) {
                           moveToMapScreen();
                        }
                    }
                }
            }

            isFirstShake = false;
            mLastShakeTime = curTime;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }




    @Override
    protected void onResume() {
        super.onResume();

        if (isSensorAvailable) {
            sensorManager.registerListener(this, this.shakeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (isSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

}