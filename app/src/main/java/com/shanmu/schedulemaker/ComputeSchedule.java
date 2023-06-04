package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.shanmu.schedulemaker.models.DateWithTask;
import com.shanmu.schedulemaker.models.DayAndTimeAvailable;
import com.shanmu.schedulemaker.models.DayAndTimeSlot;
import com.shanmu.schedulemaker.models.DayWithTask;
import com.shanmu.schedulemaker.models.GoalAndCountOfSlots;
import com.shanmu.schedulemaker.models.GoalAndDeadline;
import com.shanmu.schedulemaker.models.ScheduleWithTasks;
import com.shanmu.schedulemaker.models.TaskPerTimeSlot;
import com.shanmu.schedulemaker.models.TimeSlot;
import com.shanmu.schedulemaker.utils.DateUtils;
import com.shanmu.schedulemaker.utils.DbHelper;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ComputeSchedule extends AppCompatActivity {

    DbHelper dbHelper;

    ArrayList<GoalAndDeadline> listOfGoalAndDeadline;
    ArrayList<DayAndTimeSlot> listOfDayAndTimeSlot;
    ArrayList<DayAndTimeAvailable> listOfAvailableTime;
    ArrayList<DayWithTask> listOfDayWithTask;
    ArrayList<GoalAndDeadline> timeSlotAddedGoalAndDealine;
    ArrayList<DateWithTask> listOfDateWithTask;
    ArrayList<ScheduleWithTasks> listOfScheduleWithTasks;
    GoalAndDeadline currentTargetedGoalAndDeadline = null;
    Integer goalTimeRemaining = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_schedule);

        dbHelper = new DbHelper(this);

        listOfGoalAndDeadline = this.getIntent().getParcelableArrayListExtra("listOfGoalAndDeadline");
        listOfDayAndTimeSlot = this.getIntent().getParcelableArrayListExtra("listOfDayAndTimeSlot");
        listOfAvailableTime = new ArrayList<>();
        listOfDayWithTask = new ArrayList<>();
        timeSlotAddedGoalAndDealine = new ArrayList<>();
        listOfDateWithTask = new ArrayList<>();

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

        createDaysWithTask();
        Long totalTimeAvailable = getTotalTimeAvailableInSchedule();
        Long totalTimeRequired = getTotalTimeRequired();


        if (totalTimeRequired.intValue() > totalTimeAvailable.intValue()) {
            // MOVE TO NEXT SCREEN WITH ERROR

            Intent moveToErrorDisplay = new Intent(getApplicationContext(), ScheduleComputeErrorActivity.class);
            moveToErrorDisplay.putExtra("time-required", String.valueOf(totalTimeRequired) + " minutes");
            moveToErrorDisplay.putExtra("time-provided", String.valueOf(totalTimeAvailable) + "minutes");
            moveToErrorDisplay.putExtra("difference", String.valueOf(totalTimeRequired.intValue() - totalTimeAvailable.intValue()) + " minutes");
            startActivity(moveToErrorDisplay);
        } else {
            createTaskSlots();
        }

    }

    public void createTaskSlots() {

        List<DateWithTask> dateWithTasks = generateGeneralDateWithTasks();
        targetCurrentGoal();
        listOfScheduleWithTasks = new ArrayList<>();

        for (DateWithTask item: dateWithTasks) {

            if (currentTargetedGoalAndDeadline != null) {
                ArrayList<TaskPerTimeSlot> listOfTasksPerSlot = new ArrayList<>();

                for (TimeSlot timeSlot: item.getTimeSlots()) {
                    if (goalTimeRemaining > 0) {
                        TaskPerTimeSlot taskPerTimeSlot = new TaskPerTimeSlot();
                        taskPerTimeSlot.setGoal(currentTargetedGoalAndDeadline.getGoal());
                        taskPerTimeSlot.setTimeSlot(timeSlot);
                        listOfTasksPerSlot.add(taskPerTimeSlot);
                        decrementCurrentGoal(timeSlot);
                    }
                }
                ScheduleWithTasks scheduleWithTasks = new ScheduleWithTasks(item.getDate(), listOfTasksPerSlot);
                listOfScheduleWithTasks.add(scheduleWithTasks);
            }
        }

        //ASSUMING THE SCHEDULE MAKING IS PERFECT

        for (DateWithTask dateWithTask: listOfDateWithTask) {
            dbHelper.insertDateIntoDateTable(dateWithTask.getDate());
        }

        for (DayAndTimeSlot timeSlot: listOfDayAndTimeSlot) {
            String from = timeSlot.getTimeslot().split("-")[0];
            String to = timeSlot.getTimeslot().split("-")[1];

            dbHelper.insertTimeSlotIntoTimeSlotTable(from, to);
        }



        for (GoalAndDeadline goalAndDeadline: listOfGoalAndDeadline) {
            String fromDate = DateUtils.convertStringDateToIntDate(DateUtils.getTodaysDate());
            String toDate = goalAndDeadline.getDeadline();
            String goalName = goalAndDeadline.getGoal();

            dbHelper.insertGoalIntoGoalTable(goalName, fromDate, toDate);
        }


        for (ScheduleWithTasks item: listOfScheduleWithTasks) {
            for (TaskPerTimeSlot taskPerTimeSlot: item.getListOfTaskPerTimeSlot()) {
               dbHelper.insertIntoTaskTimeslotTable(taskPerTimeSlot.getGoal(), taskPerTimeSlot.getTimeSlot().getFrom(), taskPerTimeSlot.getTimeSlot().getTo());
            }
        }


        for (ScheduleWithTasks item: listOfScheduleWithTasks) {
            for (TaskPerTimeSlot taskPerTimeSlot: item.getListOfTaskPerTimeSlot()) {
                Integer dateId = dbHelper.getDateIdfromDate(item.getDate());
                Integer goalId = dbHelper.getGoalIdFromGoalName(taskPerTimeSlot.getGoal());
                Integer timeslotId = dbHelper.getTimeslotIdfromTimeSlot(taskPerTimeSlot.getTimeSlot().getFrom(), taskPerTimeSlot.getTimeSlot().getTo());

                dbHelper.insertScheduleIntoScheduleTable(dateId, goalId, timeslotId);
            }

        }

        calculateSlotsPerGoal();

        new Handler().postDelayed(new Runnable() {

            public void run() {
                startActivity(new Intent(getApplicationContext(), ScheduleCreatedActivity.class));
            }
        }, 1000);


    }

    public void targetCurrentGoal() {
        for (int i = 0; i < listOfGoalAndDeadline.size(); i++) {
            if (!timeSlotAddedGoalAndDealine.contains(listOfGoalAndDeadline.get(i))) {
                currentTargetedGoalAndDeadline = listOfGoalAndDeadline.get(i);
                goalTimeRemaining = currentTargetedGoalAndDeadline.getEstimatedHours() * 60;
                break;
            }
        }
    }

    public void calculateSlotsPerGoal() {
        List<GoalAndCountOfSlots> listOfGoalAndCountOfSlots = new ArrayList<>();
        for (GoalAndDeadline goalAndDeadline: listOfGoalAndDeadline) {
            int count = 0;
            for (ScheduleWithTasks scheduleWithTasks: listOfScheduleWithTasks) {
                for (TaskPerTimeSlot taskPerTimeSlot: scheduleWithTasks.getListOfTaskPerTimeSlot()) {
                    if (taskPerTimeSlot.getGoal() == goalAndDeadline.getGoal()) {
                        count++;
                    }
                }
            }
            GoalAndCountOfSlots goalAndCountOfSlots = new GoalAndCountOfSlots();
            goalAndCountOfSlots.setGoal(goalAndDeadline.getGoal());
            goalAndCountOfSlots.setCountOfTimeSlots(count);
            listOfGoalAndCountOfSlots.add(goalAndCountOfSlots);
        }

        for (GoalAndCountOfSlots goalAndCountOfSlots: listOfGoalAndCountOfSlots) {
            dbHelper.insertSlotCountIntoGoalTable(goalAndCountOfSlots.getCountOfTimeSlots(), goalAndCountOfSlots.getGoal());
        }
    }

    public void decrementCurrentGoal(TimeSlot timeSlot) {
        Long timeGap = DateUtils.getDifferenceBetweenTwoTimes(timeSlot.getFrom(), timeSlot.getTo());
        goalTimeRemaining = goalTimeRemaining - Long.valueOf(timeGap).intValue();
        if (goalTimeRemaining < 1) {
            timeSlotAddedGoalAndDealine.add(currentTargetedGoalAndDeadline);
            targetCurrentGoal();
        }
    }

    public List<DateWithTask> generateGeneralDateWithTasks() {
        for (DayWithTask item: listOfDayWithTask) {
            DayAndTimeSlot dayAndTimeSlot = null;
            for (DayAndTimeSlot dtSlot: listOfDayAndTimeSlot) {
                if (dtSlot.getDay().equals(item.getDay())) {
                    dayAndTimeSlot = dtSlot;
                }
            }

            if (dayAndTimeSlot != null) {
                List<TimeSlot> generatedTimeSlotForDate = generateTimeSlots(dayAndTimeSlot.getTimeslot());
                DateWithTask dateWithTask = new DateWithTask(item.getDate(), generatedTimeSlotForDate);
                listOfDateWithTask.add(dateWithTask);
            }
        }

        for (DateWithTask item: listOfDateWithTask) {
            Log.d("listOfDateWithTask", item.getDate() + " timelsots " + item.getTimeSlots());
        }

        return listOfDateWithTask;
    }

    public List<TimeSlot> generateTimeSlots(String timeslot) {

        ArrayList<TimeSlot> generatedTimeSlots = new ArrayList<>();

        Integer fromTime = Integer.parseInt(timeslot.split("-")[0]);
        Integer toTime = Integer.parseInt(timeslot.split("-")[1]);

        Integer timeOfGap = toTime - fromTime;
        Boolean isTimeAvailable = timeOfGap > 100; // Greater than 1 hour

        Integer newFromTime = fromTime - 100;
        Integer newToTime = toTime;

        while(isTimeAvailable) {
             newFromTime += 100;
             newToTime = newFromTime + 100;
             if (newToTime > toTime) {
                 break;
             }
             isTimeAvailable = newToTime - newFromTime > 0;

             String newFromTimeInStr = String.valueOf(newFromTime);
             String newToTimeInStr = String.valueOf(newToTime);

             if (newFromTime < 1000) {
                 newFromTimeInStr = "0" + newFromTimeInStr;
                 if (newFromTime < 100) {
                     newFromTimeInStr = "0" + newFromTimeInStr;
                 }
             }

             if (newToTime < 1000) {
                 newToTimeInStr = "0" + newToTimeInStr;
                 if (newToTime < 100) {
                     newFromTimeInStr = "0" + newFromTimeInStr;
                 }
             }

             TimeSlot slot = new TimeSlot(newFromTimeInStr, newToTimeInStr);
             generatedTimeSlots.add(slot);

        }

        return generatedTimeSlots;

    }



    public Long getTotalTimeRequired() {

        Long totalTimeRequired = 0L;

        for (GoalAndDeadline item: listOfGoalAndDeadline) {
            totalTimeRequired += (long) (item.getEstimatedHours() * 60);
        }

        return totalTimeRequired;
    }

    public Long getTotalTimeAvailableInSchedule() {
        Long availableMinutes = 0L;

        for (DayWithTask item: listOfDayWithTask) {
            for (DayAndTimeAvailable dayAndTimeAvailable: listOfAvailableTime) {
                if (dayAndTimeAvailable.getDay().equals(item.getDay())) {
                    availableMinutes += dayAndTimeAvailable.getTimeAvailable();
                }
            }
        }

        return availableMinutes;
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