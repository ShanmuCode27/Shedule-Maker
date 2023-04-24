package com.shanmu.schedulemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.shanmu.schedulemaker.models.DayAndTimeSlot;
import com.shanmu.schedulemaker.models.GoalAndDeadline;
import com.shanmu.schedulemaker.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetTimeSlots extends AppCompatActivity {

    TimePickerDialog mondaySelectFromTimePickerDialog, mondaySelectToTimePickerDialog;
    TimePickerDialog tuesdaySelectFromTimePickerDialog, tuesdaySelectToTimePickerDialog;
    TimePickerDialog wednesdaySelectFromTimePickerDialog, wednesdaySelectToTimePickerDialog;
    TimePickerDialog thursdaySelectFromTimePickerDialog, thursdaySelectToTimePickerDialog;
    TimePickerDialog fridaySelectFromTimePickerDialog, fridaySelectToTimePickerDialog;
    TimePickerDialog saturdaySelectFromTimePickerDialog, saturdaySelectToTimePickerDialog;
    TimePickerDialog sundaySelectFromTimePickerDialog, sundaySelectToTimePickerDialog;
    Button mondayFromBtn, mondayToBtn, tuesdayFromBtn, tuesdayToBtn, wednesdayFromBtn, wednesdayToBtn;
    Button thursdayFromBtn, thursdayToBtn, fridayFromBtn, fridayToBtn, saturdayFromBtn, saturdayToBtn;
    Button sundayFromBtn, sundayToBtn;
    Button submitBtn;
    ArrayList<GoalAndDeadline> listOfGoalAndDeadline;
    ArrayList<DayAndTimeSlot> listOfDayAndTimeSlot;

    DayAndTimeSlot mondayTimeSlot, tuesdayTimeSlot, wednesdayTimeSlot, thursdayTimeSlot, fridayTimeSlot, saturdayTimeSlot, sundayTimeSlot;

    final Date c = Calendar.getInstance().getTime();
    int hours = c.getHours();
    int mins = c.getMinutes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_time_slots);

        listOfGoalAndDeadline  = this.getIntent().getParcelableArrayListExtra("listOfGoalAndDeadline");

        mondayTimeSlot = new DayAndTimeSlot("mon");
        tuesdayTimeSlot = new DayAndTimeSlot("tue");
        wednesdayTimeSlot = new DayAndTimeSlot("wed");
        thursdayTimeSlot = new DayAndTimeSlot("thu");
        fridayTimeSlot = new DayAndTimeSlot("fri");
        saturdayTimeSlot = new DayAndTimeSlot("sat");
        sundayTimeSlot = new DayAndTimeSlot("sun");

        mondayFromBtn = findViewById(R.id.gettimeslot_monday_from_btn);
        mondayToBtn = findViewById(R.id.gettimeslot_monday_to_btn);
        tuesdayFromBtn = findViewById(R.id.gettimeslot_tuesday_from_btn);
        tuesdayToBtn = findViewById(R.id.gettimeslot_tuesday_to_btn);
        wednesdayFromBtn = findViewById(R.id.gettimeslot_wednesday_from_btn);
        wednesdayToBtn = findViewById(R.id.gettimeslot_wednesday_to_btn);
        thursdayFromBtn = findViewById(R.id.gettimeslot_thursday_from_btn);
        thursdayToBtn = findViewById(R.id.gettimeslot_thursday_to_btn);
        fridayFromBtn = findViewById(R.id.gettimeslot_friday_from_btn);
        fridayToBtn = findViewById(R.id.gettimeslot_friday_to_btn);
        saturdayFromBtn = findViewById(R.id.gettimeslot_saturday_from_btn);
        saturdayToBtn = findViewById(R.id.gettimeslot_saturday_to_btn);
        sundayFromBtn = findViewById(R.id.gettimeslot_sunday_from_btn);
        sundayToBtn = findViewById(R.id.gettimeslot_sunday_to_btn);
        submitBtn = findViewById(R.id.gettimeslot_submit_btn);

        mondaySelectFromTimePickerDialog = new TimePickerDialog(this, mondayFromTimeListener,hours, mins, false);
        mondaySelectToTimePickerDialog = new TimePickerDialog(this, mondayToTimeListener, hours, mins, false);
        tuesdaySelectFromTimePickerDialog = new TimePickerDialog(this, tuesdayFromTimeListener, hours, mins, false);
        tuesdaySelectToTimePickerDialog = new TimePickerDialog(this, tuesdayToTimeListener, hours, mins, false);
        wednesdaySelectFromTimePickerDialog = new TimePickerDialog(this, wednesdayFromTimeListener, hours, mins, false);
        wednesdaySelectToTimePickerDialog = new TimePickerDialog(this, wednesdayToTimeListener, hours, mins, false);
        thursdaySelectFromTimePickerDialog = new TimePickerDialog(this, thursdayFromTimeListener, hours, mins, false);
        thursdaySelectToTimePickerDialog = new TimePickerDialog(this, thursdayToTimeListener, hours, mins, false);
        fridaySelectFromTimePickerDialog = new TimePickerDialog(this, fridayFromTimeListener, hours, mins, false);
        fridaySelectToTimePickerDialog = new TimePickerDialog(this, fridayToTimeListener, hours, mins, false);
        saturdaySelectFromTimePickerDialog = new TimePickerDialog(this, saturdayFromTimeListener, hours, mins, false);
        saturdaySelectToTimePickerDialog = new TimePickerDialog(this, saturdayToTimeListener, hours, mins, false);
        sundaySelectFromTimePickerDialog = new TimePickerDialog(this, sundayFromTimeListener, hours, mins, false);
        sundaySelectToTimePickerDialog = new TimePickerDialog(this, sundayToTimeListener, hours, mins, false);

        mondaySelectFromTime();
        mondaySelectToTime();
        tuesdaySelectFromTime();
        tuesdaySelectToTime();
        wednesdaySelectFromTime();
        wednesdaySelectToTime();
        thursdaySelectFromTime();
        thursdaySelectToTime();
        fridaySelectFromTime();
        fridaySelectToTime();
        saturdaySelectFromTime();
        saturdaySelectToTime();
        sundaySelectFromTime();
        sundaySelectToTime();
        submitTimeSlots();

    }

    private void mondaySelectFromTime() {
        mondayFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mondaySelectFromTimePickerDialog.show();
            }
        });
    }

    private void mondaySelectToTime() {
        mondayToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mondaySelectToTimePickerDialog.show();
            }
        });
    }

    private void tuesdaySelectFromTime() {
        tuesdayFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuesdaySelectFromTimePickerDialog.show();
            }
        });
    }

    private void tuesdaySelectToTime() {
        tuesdayToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuesdaySelectToTimePickerDialog.show();
            }
        });
    }

    private void wednesdaySelectFromTime() {
        wednesdayFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wednesdaySelectFromTimePickerDialog.show();
            }
        });
    }

    private void wednesdaySelectToTime() {
        wednesdayToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wednesdaySelectToTimePickerDialog.show();
            }
        });
    }

    private void thursdaySelectFromTime() {
        thursdayFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thursdaySelectFromTimePickerDialog.show();
            }
        });
    }

    private void thursdaySelectToTime() {
        thursdayToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thursdaySelectToTimePickerDialog.show();
            }
        });
    }

    private void fridaySelectFromTime() {
        fridayFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fridaySelectFromTimePickerDialog.show();
            }
        });
    }

    private void fridaySelectToTime() {
        fridayToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fridaySelectToTimePickerDialog.show();
            }
        });
    }

    private void saturdaySelectFromTime() {
        saturdayFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saturdaySelectFromTimePickerDialog.show();
            }
        });
    }

    private void saturdaySelectToTime() {
        saturdayToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saturdaySelectToTimePickerDialog.show();
            }
        });
    }


    private void sundaySelectFromTime() {
        sundayFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sundaySelectFromTimePickerDialog.show();
            }
        });
    }

    private void sundaySelectToTime() {
        sundayToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sundaySelectToTimePickerDialog.show();
            }
        });
    }

    private void submitTimeSlots() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToNextScreen = new Intent(getApplicationContext(), ComputeSchedule.class);
                moveToNextScreen.putParcelableArrayListExtra("listOfGoalAndDeadline", listOfGoalAndDeadline);
                moveToNextScreen.putParcelableArrayListExtra("listOfDayAndTimeSlot", listOfDayAndTimeSlot);

                startActivity(moveToNextScreen);
            }
        });
    }

    TimePickerDialog.OnTimeSetListener mondayFromTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            mondayFromBtn.setText(singleTime);
            mondayTimeSlot.setTimeslot(time);
            Log.d("timers", time);
        }
    };

    TimePickerDialog.OnTimeSetListener mondayToTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            mondayToBtn.setText(singleTime);
            mondayTimeSlot.setTimeslot(mondayTimeSlot.getTimeslot() + "-" + time);
            listOfDayAndTimeSlot.add(mondayTimeSlot);
        }
    };


    TimePickerDialog.OnTimeSetListener tuesdayFromTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            tuesdayFromBtn.setText(singleTime);
            tuesdayTimeSlot.setTimeslot(time);
        }
    };

    TimePickerDialog.OnTimeSetListener tuesdayToTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            tuesdayToBtn.setText(singleTime);
            tuesdayTimeSlot.setTimeslot(tuesdayTimeSlot.getTimeslot() + "-" + time);
            listOfDayAndTimeSlot.add(tuesdayTimeSlot);
        }
    };


    TimePickerDialog.OnTimeSetListener wednesdayFromTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            wednesdayFromBtn.setText(singleTime);
            wednesdayTimeSlot.setTimeslot(time);
        }
    };

    TimePickerDialog.OnTimeSetListener wednesdayToTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            wednesdayToBtn.setText(singleTime);
            wednesdayTimeSlot.setTimeslot(wednesdayTimeSlot.getTimeslot() + "-" + time);
            listOfDayAndTimeSlot.add(wednesdayTimeSlot);
        }
    };


    TimePickerDialog.OnTimeSetListener thursdayFromTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            thursdayFromBtn.setText(singleTime);
            thursdayTimeSlot.setTimeslot(time);
        }
    };

    TimePickerDialog.OnTimeSetListener thursdayToTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            thursdayToBtn.setText(singleTime);
            thursdayTimeSlot.setTimeslot(thursdayTimeSlot.getTimeslot() + "-" + time);
            listOfDayAndTimeSlot.add(thursdayTimeSlot);
        }
    };

    TimePickerDialog.OnTimeSetListener fridayFromTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            fridayFromBtn.setText(singleTime);
            fridayTimeSlot.setTimeslot(time);
        }
    };

    TimePickerDialog.OnTimeSetListener fridayToTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            fridayToBtn.setText(singleTime);
            fridayTimeSlot.setTimeslot(fridayTimeSlot.getTimeslot() + "-" + time);
            listOfDayAndTimeSlot.add(fridayTimeSlot);
        }
    };

    TimePickerDialog.OnTimeSetListener saturdayFromTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            saturdayFromBtn.setText(singleTime);
            saturdayTimeSlot.setTimeslot(time);
        }
    };

    TimePickerDialog.OnTimeSetListener saturdayToTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            saturdayToBtn.setText(singleTime);
            saturdayTimeSlot.setTimeslot(saturdayTimeSlot.getTimeslot() + "-" + time);
            listOfDayAndTimeSlot.add(saturdayTimeSlot);
        }
    };

    TimePickerDialog.OnTimeSetListener sundayFromTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            sundayFromBtn.setText(singleTime);
            sundayTimeSlot.setTimeslot(time);
        }
    };

    TimePickerDialog.OnTimeSetListener sundayToTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            String time = DateUtils.getIntOnlyFromTime(hourOfDay, minute);
            String singleTime = DateUtils.convertTo12HourFormat(time);
            sundayToBtn.setText(singleTime);
            sundayTimeSlot.setTimeslot(sundayTimeSlot.getTimeslot() + "-" + time);
            listOfDayAndTimeSlot.add(sundayTimeSlot);
        }
    };
}