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

    public static String convertStringDateToIntDate(String date) {
        String month = date.substring(0, 4);
        String year = date.substring(date.length() - 4);
        String day = date.substring(4, date.length() - 4).trim();
        String combinedDate = year.trim() + getMonthNumberFromString(month.trim()) + day.trim();
        return combinedDate;
    }

    public static String getIntOnlyFromTime(int hours, int minutes) {

        String sHours = String.valueOf(hours);
        String sMinutes = String.valueOf(minutes);
        if (hours < 10) {
            sHours = "0" + hours;
        }
        if (minutes < 10) {
            sMinutes = "0" + minutes;
        }

        String result = sHours + sMinutes;
        return result;
    }

    public static String convertTo12HourFormat(String time) {
        String sHours = time.substring(0, 2);
        String sMinutes = time.substring(2, 4);
        Integer iHours = Integer.parseInt(sHours);
        Boolean isPm = false;
        if (iHours > 12) {
            iHours = iHours - 12;
            if (iHours < 10) {
                sHours = "0" + iHours;
            } else {
                sHours = String.valueOf(iHours);
            }
            return sHours + ":" + sMinutes + " pm";
        }
        return sHours + ":" + sMinutes + " am";
    }

    public static String getMonthNumberFromString(String month) {
        String intMonth;
        switch (month) {
            case "JAN":
                intMonth = "01";
                break;

            case "FEB":
                intMonth = "02";
                break;

            case "MAR":
                intMonth = "03";
                break;

            case "APR":
                intMonth = "04";
                break;

            case "MAY":
                intMonth = "05";
                break;

            case "JUN":
                intMonth = "06";
                break;

            case "JUL":
                intMonth = "07";
                break;

            case "AUG":
                intMonth = "08";
                break;

            case "SEP":
                intMonth = "09";
                break;

            case "OCT":
                intMonth ="10";
                break;

            case "NOV":
                intMonth = "11";
                break;

            case "DEC":
                intMonth = "12";
                break;

            default:
                intMonth = "01";
                break;
        }

        return intMonth;
    }

}
