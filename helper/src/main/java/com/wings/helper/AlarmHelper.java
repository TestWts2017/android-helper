package com.wings.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Purpose: Add and get alarms
 *
 * @author NikunjD
 * Created on March 12, 2019
 * Modified on March 12, 2019
 */

public class AlarmHelper {

    private static final String TAG_ALARM = "alarms";

    /**
     * Schedules an alarm using {@link AlarmManager}.
     *
     * @param context  context
     * @param intent   intent for pending task
     * @param alarmId  unique alarm id
     * @param calendar time interval for schedule alarm
     */
    public static void scheduleAlarm(Context context, Intent intent, int alarmId, Calendar calendar) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmManager != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }

            saveAlarmId(context, alarmId);
        }

    }

    /**
     * Cancels the scheduled alarm.
     *
     * @param context context
     * @param intent  intent to be canceled
     * @param alarmId alarm id to be canceled
     */

    public static void cancelAlarm(Context context, Intent intent, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        pendingIntent.cancel();

        removeAlarmId(context, alarmId);
    }

    /**
     * Cancel all scheduled alarms
     *
     * @param context context
     * @param intent  intent
     */
    public static void cancelAllAlarms(Context context, Intent intent) {
        for (int idAlarm : getAlarmIds(context)) {
            cancelAlarm(context, intent, idAlarm);
        }
    }

    /**
     * Check for schedule alarm
     *
     * @param context context
     * @param intent  intent
     * @param alarmId alarm id to check for
     * @return boolean true or false
     */
    public static boolean hasAlarm(Context context, Intent intent, int alarmId) {
        return PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    /**
     * Save Alarm id to preference
     *
     * @param context context
     * @param alarmId alarm id to save
     */
    public static void saveAlarmId(Context context, int alarmId) {
        List<Integer> idsAlarms = getAlarmIds(context);

        if (idsAlarms.contains(alarmId)) {
            return;
        }

        idsAlarms.add(alarmId);

        saveIdsInPreferences(context, idsAlarms);
    }


    /**
     * Remove alarm id from preference
     *
     * @param context context
     * @param alarmId alarm id to remove
     */
    public static void removeAlarmId(Context context, int alarmId) {
        List<Integer> idsAlarms = getAlarmIds(context);

        for (int i = 0; i < idsAlarms.size(); i++) {
            if (idsAlarms.get(i) == alarmId) {
                idsAlarms.remove(i);
            }
        }

        saveIdsInPreferences(context, idsAlarms);
    }

    /**
     * Get Alarm id's which are already set
     *
     * @param context context
     * @return list of alarm id's
     */
    public static List<Integer> getAlarmIds(Context context) {
        List<Integer> ids = new ArrayList<>();
        try {
            SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.with(context, TAG_ALARM,
                    Context.MODE_PRIVATE);

            JSONArray jsonArray = new JSONArray(sharedPreferenceHelper.getString(context.getPackageName() + TAG_ALARM,
                    "[]"));

            for (int i = 0; i < jsonArray.length(); i++) {
                ids.add(jsonArray.getInt(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    /**
     * Save alarm id's in shared preference
     *
     * @param context context
     * @param listIds list of id's to be save in preference
     */
    public static void saveIdsInPreferences(Context context, List<Integer> listIds) {
        JSONArray jsonArray = new JSONArray();
        for (Integer idAlarm : listIds) {
            jsonArray.put(idAlarm);
        }
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.with(context, TAG_ALARM,
                Context.MODE_PRIVATE);
        sharedPreferenceHelper.save(context.getPackageName() + TAG_ALARM, jsonArray.toString());
    }

}
