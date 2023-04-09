package com.shanmu.schedulemaker.utils;

import android.util.Log;

public class DateUtils {

    public static String getDateOnlyFromIntValues(int year, int month, int day) {
        String sMonth = String.valueOf(month);
        String sDay = String.valueOf(day);
        if (month < 10) {
           sMonth  = "0" + month;
        }
        if (day < 10) {
            sDay = "0" + day;
        }
        String dateOnly = year + sMonth + sDay;
        return dateOnly;
    }
}
