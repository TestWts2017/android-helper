package com.wings.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Purpose: Open call dialer with phone number
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 14, 2019
 */
public class DialerHelper {

    /**
     * Open Dialer screen with phone number
     *
     * @param context - Context to open dialer
     * @param phoneNo - Phone number which directly appear in dialer screen
     */
    public static void openDialer(Context context, String phoneNo) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNo));
        context.startActivity(intent);
    }

}
