package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.shanmu.schedulemaker.models.DayAndTimeAvailable;
import com.shanmu.schedulemaker.models.DayAndTimeSlot;
import com.shanmu.schedulemaker.models.DayWithTask;
import com.shanmu.schedulemaker.models.GoalAndDeadline;
import com.shanmu.schedulemaker.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ComputeSchedule extends AppCompatActivity {

    ArrayList<GoalAndDeadline> listOfGoalAndDeadline;
    ArrayList<DayAndTimeSlot> listOfDayAndTimeSlot;
    ArrayList<DayAndTimeAvailable> listOfAvailableTime;
    ArrayList<DayWithTask> listOfDayWithTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_schedule);

        listOfGoalAndDeadline = this.getIntent().getParcelableArrayListExtra("listOfGoalAndDeadline");
        listOfDayAndTimeSlot = this.getIntent().getParcelableArrayListExtra("listOfDayAndTimeSlot");
        listOfAvailableTime = new ArrayList<>();
        listOfDayWithTask = new ArrayList<>();

        for (DayAndTimeSlot item: listOfDayAndTimeSlot) {
            String timeSlot = item.getTimeslot();
            Long timeAvailable = DateUtils.getDifferenceBetweenTwoTimes(timeSlot.split("-")[0], timeSlot.split("-")[1]);

            DayAndTimeAvailable dayAndTimeAvailable = new DayAndTimeAvailable(item.getDay(), timeAvailable);
            listOfAvailableTime.add(dayAndTimeAvailable);
        }

        Collections.sort(listOfGoalAndDeadline, new CompareDeadlines());

        createShedule();

    }


    public void createShedule() {

        Long availableMinutes = null;
        createDaysWithTask();

        for (DayWithTask item: listOfDayWithTask) {
            for (DayAndTimeAvailable dayAndTimeAvailable: listOfAvailableTime) {
                Log.d("checkavail", dayAndTimeAvailable.getDay() + " : " + item.getDay());
                if (dayAndTimeAvailable.getDay().equals(item.getDay())) {
                    Log.d("insideavail", "time is " + dayAndTimeAvailable.getTimeAvailable());
                    availableMinutes += dayAndTimeAvailable.getTimeAvailable();
                }
            }
        }

        Log.d("availtime", "time is " + availableMinutes);

//        for (int i = 0; i < listOfGoalAndDeadline.size(); i++) {
//            int differenceInDays = differentBetweenDays(currentDate, listOfGoalAndDeadline.get(i).getDeadline());
//
//        }

//        for (GoalAndDeadline item: listOfGoalAndDeadline) {
//            Log.d("goaltime", "in hours " + item.getEstimatedHours() + " days " + item.getDeadline());
//        }

//        for (DayAndTimeAvailable item: listOfAvailableTime) {
//            Log.d("intime", item.getDay() + " --- " + item.getTimeAvailable());
//        }
    }

    public void createDaysWithTask() {
        String todayDate = getFormattedDate(DateUtils.getTodaysDate());

        String lastDeadline = listOfGoalAndDeadline.get(listOfGoalAndDeadline.size() - 1).getDeadline();
        int numberOfDaysForTotalDeadline = differentBetweenDays(todayDate, lastDeadline);

        for(int i = 1; i < numberOfDaysForTotalDeadline - 1; i++) {
            Date date = new Date();
            Date currentDateOfTask = new Date(date.getTime() + (1000 * 60 * 60 * 24) * i);
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDateOfTask);

            String taskDate = DateUtils.getDateOnlyFromIntValues(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
            String day = DateUtils.dayFromCalendarDayIndex(cal.get(Calendar.DAY_OF_WEEK));

            DayWithTask dayWithTask = new DayWithTask(day, taskDate);
            listOfDayWithTask.add(dayWithTask);
        }
    }


    public int differentBetweenDays(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dateOne = format.parse(date1);
            Date dateTwo = format.parse(date2);

            Long difference = Math.abs(dateTwo.getTime() - dateOne.getTime());
            int diffInDays  = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

            return diffInDays;

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public class CompareDeadlines implements Comparator<GoalAndDeadline> {

        @Override
        public int compare(GoalAndDeadline obj1, GoalAndDeadline obj2) {

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");


            try {
                Date date1 = format.parse(obj1.getDeadline());
                Date date2 = format.parse(obj2.getDeadline());

                return date1.compareTo(date2);

            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }

    }


    public String getFormattedDate(String date) {
        String[] splitDate = date.split("\\s");
        String month = splitDate[0].trim();
        month = DateUtils.getMonthNumberFromString(month);
        String day = splitDate[1].trim();
        String year = splitDate[2].trim();

        return year + month + day;
    }
}