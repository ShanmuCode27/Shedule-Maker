package com.shanmu.schedulemaker.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        if (Integer.parseInt(day) < 10) {
            day += "0" + day;
        }
        String combinedDate = year.trim() + getMonthNumberFromString(month.trim()) + day.trim();
        return combinedDate;
    }

    public static Date convertIntDateToDateFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddd");
        try {
            Date convertedDate = format.parse(date);
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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

    public static Long getDifferenceBetweenTwoTimes(String time1, String time2) {
        String formattedTime1 = time1.substring(0,2) + ":" + time1.substring(2, time1.length()) + ":00";
        String formattedTime2 = time2.substring(0,2) + ":" + time1.substring(2, time1.length()) + ":00";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        try {
            Date date1 = format.parse(formattedTime1);
            Date date2 = format.parse(formattedTime2);
            long difference = date2.getTime() - date1.getTime();
            long minutes = (difference / 1000) / 60;

            return minutes;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String dayFromCalendarDayIndex(int dayIndex) {
        String day;
        Log.d("methodreceived ", "the index fo " + dayIndex);
        switch (dayIndex) {
            case 1:
                day = "sun";
                break;
            case 2:
                day = "mon";
                break;
            case 3:
                day = "tue";
                break;
            case 4:
                day = "wed";
                break;
            case 5:
                day = "thu";
                break;
            case 6:
                day = "fri";
                break;
            case 7:
                day = "sat";
                break;
            default:
                day = "sun";
        }

        return day;
    }

    public static String convertIntDateToDisplayFormat(String date) {
        String day = date.substring(date.length() - 2);
        String dayPostFix = "th";
        switch (Integer.parseInt(day) % 10) {
            case 1:
                dayPostFix = "st";
                break;
            case 2:
                dayPostFix = "nd";
                break;
            case 3:
                dayPostFix = "rd";
                break;
            default:
                dayPostFix = "th";
        }

        String month = getMonthFormat(Integer.parseInt(date.substring(4, 6)));
        String year = date.substring(0, 4);

        return day + dayPostFix + " " + month + " " + year;

    }

}
