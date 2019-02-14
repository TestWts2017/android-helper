package com.wings.helper;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Purpose: Date and Time functions
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 13, 2019
 */
public class DateTimeHelper {


    /**
     * @param format **Apply date format
     * @return current date as per date format
     */
    public static String getCurrentDateTime(String format) {
        return DateFormat.format(format, new Date()).toString();
    }

    public static long getSystemCurrentTimeInMills() {
        return System.currentTimeMillis();
    }

    /**
     * @param inputDate  **Apply date as a string
     * @param dateFormat **Apply date format as per input date
     * @return millisecond
     */
    public static long getMilliSecondFromDate(String inputDate, String dateFormat) {
        long millis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(inputDate);
            millis = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }

    /**
     * @param inputDate **Apply input date string
     * @param inputDateFormat **Input date format (UTC date format)
     * @param outputDateFormat **Output date format (local date format)
     * @return local date and time from UTC
     */
    public static String convertDateTimeUTCToLocal(String inputDate, String inputDateFormat, String outputDateFormat) {
        String localDate = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(inputDateFormat);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(inputDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(outputDateFormat);
            dateFormatter.setTimeZone(TimeZone.getDefault());
            localDate = dateFormatter.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDate;
    }

    /**
     * @param dateTime **Apply date and time as a String
     * @param inputPattern **Input date format
     * @param outputPattern **Output date format
     * @return formatted date
     */
    public static String changeDateFormat(String dateTime, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(dateTime);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * @param milliSeconds **millisecond in long
     * @param dateFormat **Apply require date format
     * @return date from millisecond with specific pattern
     */
    public static String changeMilliSecondToDateFormat(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        String time = formatter.format(calendar.getTime());
        time = time.replace(".", "").replace("am", "AM").replace("pm", "PM");
        return time;
    }
}
