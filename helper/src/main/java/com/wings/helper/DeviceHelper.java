package com.wings.helper;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Field;

/**
 * Purpose: To get hardware information of device
 *
 * @author NikunjD
 * Created on June 11, 2019
 * Modified on June 11, 2019
 */
public class DeviceHelper {

    /**
     * Get Device model
     *
     * @return device model
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * Get device manufacture
     *
     * @return device manufacture
     */
    public static String getDeviceManufacture() {
        return Build.MANUFACTURER;
    }

    /**
     * Get device sdk version
     *
     * @return device sdk (ie. SDK 23)
     */
    public static String getDeviceSDK() {
        return Build.VERSION.SDK;
    }

    /**
     * Get device version code
     *
     * @return device version code (ie. 6.0.1)
     */
    public static String getDeviceVersionCode() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Get Device Hardware id
     *
     * @param context context
     * @return hardware id
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        if (context != null) {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return "";
    }

    /**
     * Get device os name
     *
     * @return device os
     */
    public static String getDeviceOS() {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String osName = "";
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    osName = field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return osName;
    }

}

