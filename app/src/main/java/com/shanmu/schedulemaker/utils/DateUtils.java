package com.shanmu.schedulemaker.utils;

import android.util.Log;

import java.util.Calendar;

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


    public static String getMonthFormat(int month)
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


    public static String makeDateString(int day, int month, int year)
    {
        return DateUtils.getMonthFormat(month) + " " + day + " " + year;
    }

    public static String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return DateUtils.makeDateString(day, month, year);
    }

}
