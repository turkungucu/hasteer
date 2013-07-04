/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author ecolak
 */
public class DateUtils {
    public static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public static final DateFormat shortDateFormat = new SimpleDateFormat("MM/dd/yy");
    public static final DateFormat dateTimeFormat = new SimpleDateFormat();
    public static final long MILLIS_IN_DAY = 24*60*60*1000;
    public static final long MILLIS_IN_HOUR = 60*60*1000;
    public static final long MILLIS_IN_MINUTE = 60*1000;

    public static int daysDiff(Date firstDate, Date secondDate) {
        return diffHelper(firstDate, secondDate, MILLIS_IN_DAY);
    }

    public static int hoursDiff(Date firstDate, Date secondDate) {
        return diffHelper(firstDate, secondDate, MILLIS_IN_HOUR);
    }

    public static int minutesDiff(Date firstDate, Date secondDate) {
        return diffHelper(firstDate, secondDate, MILLIS_IN_MINUTE);
    }

    public static int diffHelper(Date firstDate, Date secondDate, long millis) {
        int result = 0;
        long diffMillis = Math.abs(firstDate.getTime() - secondDate.getTime());
        result = (int)(Math.ceil((double)diffMillis / (double)millis));
        return result;
    }

    public static Date getDateFromMonthYear(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        return cal.getTime();
    }

    // jquery api requires date in format i.e +2d -4h +30m
    public static String getTimeRemainingInCounterFormat(Date endDate) {
        Calendar today = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int dayDiff = daysDiff(endDate, today.getTime());
        int hoursDiff = end.get(Calendar.HOUR_OF_DAY) - today.get(Calendar.HOUR_OF_DAY);
        int minutesDiff = end.get(Calendar.MINUTE) - today.get(Calendar.MINUTE);

        return dayDiff + "d " + hoursDiff + "h " + minutesDiff + "m";
    }
}
