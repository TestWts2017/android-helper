package com.wings.helper;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Purpose: Date and Time functions
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on June 10, 2019
 */
public class DateTimeHelper {


    /**
     * Get current date and time
     *
     * @param format Format of date
     * @return value - current date as per date format
     */
    public static String getCurrentDateTime(String format) {
        return DateFormat.format(format, new Date()).toString();
    }

    /**
     * Get current time in millisecond
     *
     * @return value - current time in millisecond
     */
    public static long getSystemCurrentTimeInMills() {
        return System.currentTimeMillis();
    }

    /**
     * Get time in millisecond
     *
     * @param inputDate  Input date as a string
     * @param dateFormat Default date format as per input date
     * @return value - millisecond from date
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
     * Convert date and time from UTC timezone to local timezone
     *
     * @param inputDate        Input date string
     * @param inputDateFormat  Input date format (UTC date format)
     * @param outputDateFormat Output date format (local date format)
     * @return value - local date and time from UTC
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
     * Convert date and time from one timezone to other timezone
     * for ex. if you want to convert local timezone to UTC timezone then
     * inputTimeZone should be - TimeZone.getDefault()
     * outputTimeZone should be - TimeZone.getTimeZone("UTC")
     *
     *
     * @param ourDate Input date string
     * @param inputDateFormat Input date format
     * @param outputDateFormat Output date format
     * @param inputTimeZone Input date timezone
     * @param outputTimeZone Output date timezone
     * @return value - date and time as per output timezone
     */
    @SuppressLint("SimpleDateFormat")
    public static String convertDateTimeUsingTimeZone
            (String ourDate, String inputDateFormat, String outputDateFormat, TimeZone inputTimeZone,
             TimeZone outputTimeZone) {

        String outputDateFormatWithTimeZone = "";
        Date parseInputDate = null;
        SimpleDateFormat formatter;
        SimpleDateFormat parser;

        parser = new SimpleDateFormat(inputDateFormat);
        parser.setTimeZone(inputTimeZone);
        try {
            parseInputDate = parser.parse(ourDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        formatter = new SimpleDateFormat(outputDateFormat);
        formatter.setTimeZone(inputTimeZone);

        formatter.setTimeZone(outputTimeZone);
        if (parseInputDate != null) {
            outputDateFormatWithTimeZone = formatter.format(parseInputDate);
        }

        return outputDateFormatWithTimeZone;
    }




    /**
     * Convert Date format
     *
     * @param dateTime      Date and time as a string
     * @param inputPattern  Input date format
     * @param outputPattern Output date format
     * @return value - formatted date
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
     * Convert millisecond date
     *
     * @param milliSeconds Millisecond in long
     * @param dateFormat   Date format
     * @return value - date from millisecond with specific pattern
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
