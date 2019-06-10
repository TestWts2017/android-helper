package com.wings.helper;

import android.util.Log;


/**
 * Purpose: Create log files for project and disable whole logs in single method call
 *
 * @author NikunjD
 * Created on March 11, 2019
 * Modified on June 10, 2019
 */
public class LogHelper {

    private static boolean logEnabled = false;
    private static final String TAG = "LogHelper";

    /**
     * Get current log status
     *
     * @return value - current log enable state
     */
    public static boolean isLogEnabled() {
        return logEnabled;
    }

    /**
     * Set Log State - Enable using 'true' and Disable using 'false'
     * Default Log is disabled
     *
     * @param logEnabled set log state
     */
    public static void setLogEnabled(boolean logEnabled) {
        LogHelper.logEnabled = logEnabled;
    }


    /**
     * Log a verbose message with LogHelper
     *
     * @param tag     message tag
     * @param message message
     */
    public static void v(String tag, String message) {
        if (LogHelper.logEnabled) {
            if (tag != null && tag.length() > 0) {
                Log.v(tag, message);
            } else {
                Log.v(TAG, message);
            }
        }
    }


    /**
     * Log a debug message with LogHelper
     *
     * @param tag     message tag
     * @param message message
     */

    public static void d(String tag, String message) {
        if (LogHelper.logEnabled) {
            if (tag != null && tag.length() > 0) {
                Log.d(tag, message);
            } else {
                Log.d(TAG, message);
            }
        }
    }


    /**
     * Log a info message with LogHelper
     *
     * @param tag     message tag
     * @param message message
     */

    public static void i(String tag, String message) {
        if (LogHelper.logEnabled && tag.length() > 0) {
            if (tag != null) {
                Log.i(tag, message);
            } else {
                Log.i(TAG, message);
            }
        }
    }


    /**
     * Log a warning message with LogHelper
     *
     * @param tag     message tag
     * @param message message
     */

    public static void w(String tag, String message) {
        if (LogHelper.logEnabled && tag.length() > 0) {
            if (tag != null) {
                Log.w(tag, message);
            } else {
                Log.w(TAG, message);
            }
        }
    }


    /**
     * Log a error message with LogHelper
     *
     * @param tag     message tag
     * @param message message
     */

    public static void e(String tag, String message) {
        if (LogHelper.logEnabled && tag.length() > 0) {
            if (tag != null) {
                Log.e(tag, message);
            } else {
                Log.e(TAG, message);
            }
        }
    }


}
