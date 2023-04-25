package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetGoalsActivity extends AppCompatActivity {

    private static int goalCounter = 0;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private EditText goalInput;
    private EditText estimationInput;
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
            String date = DateUtils.makeDateString(day, month, year);
            String dateInput = DateUtils.getDateOnlyFromIntValues(year,month,day);
            String estimatedHours = estimationInput.getText().toString() + "hrs";
            String goal = goalInput.getText().toString();
            userGoal = goal + " :  before  - " + date + " with " + estimatedHours;
            dateButton.setText(date);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_goals);

        dateButton = findViewById(R.id.deadline_select);
        goalInput = findViewById(R.id.goal_input);
        estimationInput = findViewById(R.id.goal_estimation_input);
        createGoalInputBtn = findViewById(R.id.createGoalInputBtn);
        submitCreatedGoalsBtn = findViewById(R.id.submitInputedGoalsBtn);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        dateButton.setText(DateUtils.getTodaysDate());
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
                dateButton.setText(DateUtils.getTodaysDate());
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
        Bundle bundle = new Bundle();
        int count = 0;
        ArrayList<GoalAndDeadline> listOfGoalAndDeadline = new ArrayList<>();

        for (String item: listOfGoals) {

//            Pattern regex = Pattern.compile("(?<= with ).*(?=hrs)");
//            Matcher matcher = regex.matcher(item);
//            Log.d("matcher ", matcher.group());

            String estimatedHours = item.split(" with ")[1].split("hrs")[0].trim();


            GoalAndDeadline goalAndDeadline = new GoalAndDeadline(item.split(" :  before  - ")[0], DateUtils.convertStringDateToIntDate(item.split(" :  before  - ")[1]), Integer.parseInt(estimatedHours));
            listOfGoalAndDeadline.add(goalAndDeadline);
            count++;
        }
            moveToNextScreen.putParcelableArrayListExtra("listOfGoalAndDeadline", listOfGoalAndDeadline);
            moveToNextScreen.putExtras(bundle);

        startActivity(moveToNextScreen);
    }

    public void addGoalItems(String userGoal) {
        listOfGoals.add(userGoal);
        listAdapter.notifyDataSetChanged();
    }


}