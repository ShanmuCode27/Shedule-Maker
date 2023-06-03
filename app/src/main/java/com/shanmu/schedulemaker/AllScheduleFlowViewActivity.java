package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shanmu.schedulemaker.models.DateTimeslotAndGoal;
import com.shanmu.schedulemaker.models.TimeSlot;
import com.shanmu.schedulemaker.utils.DateUtils;
import com.shanmu.schedulemaker.utils.DbHelper;
import com.shanmu.schedulemaker.utils.adapter.AllScheduleViewAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AllScheduleFlowViewActivity extends AppCompatActivity {

    DbHelper dbHelper;
    MenuItem menuItem;
    BottomNavigationView bottomNavigationView;
    Button resetBtn;

    ArrayList<DateTimeslotAndGoal> listOfDateTimeslotAndGoal;
    AllScheduleViewAdapter arrayAdapter;
    ListView listViewOfDateTimeAndGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedule_flow_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(AllScheduleFlowViewActivity.this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AllScheduleFlowViewActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        listOfDateTimeslotAndGoal = new ArrayList<>();
        listViewOfDateTimeAndGoal = findViewById(R.id.listOfDateTimeGoalView);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationHandler();

        resetBtn = findViewById(R.id.reset_all_schedule_btn);
        dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.grabAllDataFromSchedule();

        if (cursor.moveToFirst()) {
            while(cursor.moveToNext()) {

                DateTimeslotAndGoal dateTimeslotAndGoal = new DateTimeslotAndGoal();
                String date = dbHelper.getDateFromScheduleInDateTable(Integer.parseInt(cursor.getString(1)));
                dateTimeslotAndGoal.setDate(date);

                TimeSlot timeSlot = dbHelper.getTimeslotFromScheduleTaskTimeslotTable(Integer.parseInt(cursor.getString(3)));
                String fromTime = DateUtils.convertTo12HourFormat(timeSlot.getFrom());
                String toTime = DateUtils.convertTo12HourFormat(timeSlot.getTo());
                String finalTime = fromTime + " - " + toTime;

                dateTimeslotAndGoal.setTimeslotDisplay(finalTime);

                String goalName = dbHelper.getGoalFromScheduleInGoalTable(Integer.parseInt(cursor.getString(2)));
                dateTimeslotAndGoal.setGoalName(goalName);

                listOfDateTimeslotAndGoal.add(dateTimeslotAndGoal);
            }
        }

        cursor.close();

        arrayAdapter = new AllScheduleViewAdapter(this, listOfDateTimeslotAndGoal);
        listViewOfDateTimeAndGoal.setAdapter(arrayAdapter);

        handleReset();
    }

    @Override
    public void onStart() {
        super.onStart();
        notificationsForUpcomingTask();
        notificationForRecentEndedTask();
    }

    @Override
    public void onPause() {
        super.onPause();
        notificationsForUpcomingTask();
        notificationForRecentEndedTask();
    }

    public void notificationsForUpcomingTask() {
        Timer timer = new Timer();
        TimerTask everyMinute = new TimerTask() {

            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void run() {
                // check for notification of new activity

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                month = month + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String makeString = DateUtils.makeDateString(day, month, year);
                String actualDate = DateUtils.convertStringDateToIntDate(makeString);

                int count = 1;
                while(count <= 15) {
                    LocalDateTime time = LocalDateTime.now().plusMinutes(count);
                    int newHours = time.getHour();
                    int newMinutes = time.getMinute();

                    String newCurrentTime = DateUtils.getIntOnlyFromTime(newHours, newMinutes);
                    checkForUpcomingTask(newCurrentTime, actualDate, count);
                    count++;
                }
            }
        };

        timer.schedule(everyMinute,0, 1000L * 60L);
    }


    public void notificationForRecentEndedTask() {
        Timer timer = new Timer();
        TimerTask everyMinute = new TimerTask() {

            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void run() {
                // check for notification of new activity

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                month = month + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String makeString = DateUtils.makeDateString(day, month, year);
                String actualDate = DateUtils.convertStringDateToIntDate(makeString);

                int count = 1;
                while(count <= 15) {
                    LocalDateTime time = LocalDateTime.now().minusMinutes(15);
                    int newHours = time.getHour();
                    int newMinutes = time.getMinute();

                    String newCurrentTime = DateUtils.getIntOnlyFromTime(newHours, newMinutes);
                    checkForPreviousTask(newCurrentTime, actualDate, count);
                    count++;
                }
            }
        };

        timer.schedule(everyMinute,0, 1000L * 60L);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void checkForPreviousTask(String currentTime, String currentDate, int count) {
        for (DateTimeslotAndGoal goal: listOfDateTimeslotAndGoal) {
//            if (goal.getDate() == currentDate) {
                String toTime = goal.getTimeslotDisplay().substring(goal.getTimeslotDisplay().length() - 9, goal.getTimeslotDisplay().length() - 3).replace(":","");
//                if (currentTime == toTime) {
                    //Create notification
                    String CHANNEL_ID = "SHANMU_NOTIFICATION";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                    builder.setSmallIcon(R.drawable.ic_notifications);
                    builder.setContentTitle("Please mark your progress on goal " + goal.getGoalName());
                    builder.setContentText("Task happened before " + count + " minutes");
                    builder.setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    Intent intent = new Intent(getApplicationContext(), MarkGoalProgressActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("goal", goal.getGoalName());
                    intent.putExtra("timeSlot", goal.getTimeslotDisplay());

                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
                    builder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
                        if (notificationChannel == null) {
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            notificationChannel = new NotificationChannel(CHANNEL_ID, "new notification", importance);
                            notificationChannel.setLightColor(Color.GREEN);
                            notificationChannel.enableVibration(true);
                            notificationManager.createNotificationChannel(notificationChannel);
                        }
                    }

                    notificationManager.notify(0, builder.build());

//                }
//            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void checkForUpcomingTask(String currentTime, String currentDate, int count) {
        for (DateTimeslotAndGoal goal: listOfDateTimeslotAndGoal) {
            if (goal.getDate() == currentDate) {
                String fromTime = goal.getTimeslotDisplay().substring(0, 5).replace(":","");
                if (currentTime == fromTime) {
                    //Create notification
                    String CHANNEL_ID = "SHANMU_NOTIFICATION";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                    builder.setSmallIcon(R.drawable.ic_notifications);
                    builder.setContentTitle("Get Ready to work on your goal in " + count + " minutes");
                    builder.setContentText("Work on the goal " + goal.getGoalName());
                    builder.setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    Intent intent = new Intent(getApplicationContext(), UpcomingTaskNotificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("goal", goal.getGoalName());
                    intent.putExtra("timeRemaining", count);

                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
                    builder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
                        if (notificationChannel == null) {
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            notificationChannel = new NotificationChannel(CHANNEL_ID, "new notification", importance);
                            notificationChannel.setLightColor(Color.GREEN);
                            notificationChannel.enableVibration(true);
                            notificationManager.createNotificationChannel(notificationChannel);
                        }
                    }

                    notificationManager.notify(0, builder.build());

                }
            }
        }
    }

    public void handleReset() {
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteAllScheduleData();
                startActivity(new Intent(getApplicationContext(), GetGoalsActivity.class));
            }
        });
    }

    @Deprecated
    public void bottomNavigationHandler() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.progress:
                        startActivity(new Intent(getApplicationContext(), ProgressViewActivity.class));
                        overridePendingTransition(0, 0);
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