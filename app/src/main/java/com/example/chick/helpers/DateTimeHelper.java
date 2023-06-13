package com.example.chick.helpers;

import com.example.chick.models.Order;
import com.example.chick.models.UserCourse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DateTimeHelper {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static final DateFormat datetimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");


    public static String getDate(Date date) {
        return dateFormat.format(date);
    }

    public static String getTime(Date date) {
        return timeFormat.format(date);
    }
    public static String getTime(int hours, int minutes) {
        String time = "";
        if (hours < 10) time += "0";
        time += hours;
        time += ":";
        if (minutes < 10) time += "0";
        time += minutes;
        return time;
    }

    public static String getDateTime(Date date) {
        return datetimeFormat.format(date);
    }

//    public static void correctInputData() {
//        if (DataHelper.getUser() != null) {
//            for (UserCourse uc : DataHelper.getUser().getUserCourses()) {
//                correctDate(uc.getStartDate());
//            }
//            for (Order o : DataHelper.getUser().getOrders()) {
//                correctDate(o.getDate());
//            }
//        }
//    }

//    public static void correctDate(Date date) {
//        date.setTime(date.getTime() + 3 * 60 * 60 * 1000);
//    }
}
