package com.wings.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Purpose: check internet is connected or not.
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 14, 2019
 */
public class InternetConnectionHelper {

    /**
     * Check for internet is connected or not
     *
     * @param context - Context for connectivity service
     * @return value - Value as boolean (true or false)
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * Check for internet is connected or connecting
     *
     * @param context - Context for connectivity service
     * @return value - Value as boolean (true or false)
     */
    public static boolean isConnectedOrConnecting(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
