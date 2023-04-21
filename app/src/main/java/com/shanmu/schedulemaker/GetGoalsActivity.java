package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.shanmu.schedulemaker.models.GoalAndDeadline;
import com.shanmu.schedulemaker.utils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GetGoalsActivity extends AppCompatActivity {

    private static int goalCounter = 0;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private EditText goalInput;
    private Button createGoalInputBtn;
    private Button submitCreatedGoalsBtn;
    String userGoal;

    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    ArrayList<String> listOfGoals = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    ListView goalListView;


    int style = AlertDialog.THEME_HOLO_LIGHT;


    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            String date = makeDateString(day, month, year);
            String dateInput = DateUtils.getDateOnlyFromIntValues(year,month,day);

            String goal = goalInput.getText().toString();
            userGoal = goal + " :  before  - " + date;
            dateButton.setText(date);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_goals);

        dateButton = findViewById(R.id.deadline_select);
        goalInput = findViewById(R.id.goal_input);
        createGoalInputBtn = findViewById(R.id.createGoalInputBtn);
        submitCreatedGoalsBtn = findViewById(R.id.submitInputedGoalsBtn);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        createGoalInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGoalItems(userGoal);
                goalInput.setText("");
                dateButton.setText(getTodaysDate());
            }
        });

        listAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listOfGoals
        );
        goalListView = (ListView) findViewById(R.id.listOfGoalsInput);
        goalListView.setAdapter(listAdapter);

        submitCreatedGoalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToGetTimeSlotScreen();
            }
        });

    }

    private void moveToGetTimeSlotScreen() {
        Intent moveToNextScreen = new Intent(getApplicationContext(), GetTimeSlots.class);
        int count = 0;
        ArrayList<GoalAndDeadline> listOfGoalAndDeadline = new ArrayList<>();
        for (String item: listOfGoals) {
            GoalAndDeadline goalAndDeadline = new GoalAndDeadline(item.split(" :  before  - ")[0], DateUtils.convertStringDateToIntDate(item.split(" :  before  - ")[1]));
            listOfGoalAndDeadline.add(goalAndDeadline);
            count++;
        }
        moveToNextScreen.putExtra("listOfGoalAndDeadline" + count, (Serializable) listOfGoalAndDeadline);

        startActivity(moveToNextScreen);
    }

    public void addGoalItems(String userGoal) {
        listOfGoals.add(userGoal);
        listAdapter.notifyDataSetChanged();
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }
}