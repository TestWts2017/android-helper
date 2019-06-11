package com.wings.helper;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.content.pm.PackageInfoCompat;

/**
 * Purpose: Information regarding package
 *
 * @author NikunjD
 * Created on June 11, 2019
 * Modified on June 11, 2019
 */
public class PackageInfoHelper {

    /**
     * Get application unique identifier
     *
     * @param context application context (ie. getApplicationContext())
     * @return application package name
     */
    public static String getPackageName(Context context) {
        if (context != null) {
            return context.getPackageName();
        }
        return "";
    }

    /**
     * Get application version name
     *
     * @param context application context (ie. getApplicationContext())
     * @return version name (ie. 1.0.1)
     */
    public static String getVersionName(Context context) {
        if (context != null) {
            PackageManager manager = context.getPackageManager();
            try {
                PackageInfo pInfo = manager.getPackageInfo(context.getPackageName(), 0);
                return pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                return "";
            }
        }
        return "";
    }


    /**
     * Get application version code
     *
     * @param context application context (ie. getApplicationContext())
     * @return version code
     */
    public static int getVersionCode(Context context) {
        if (context != null) {
            PackageManager manager = context.getPackageManager();
            try {
                PackageInfo pInfo = manager.getPackageInfo(context.getPackageName(), 0);
                return pInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                return 0;
            }
        }
        return 0;
    }
}
