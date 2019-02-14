package com.wings.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Purpose: Open website using browser.
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 14, 2019
 */
public class WebLinkHelper {

    /**
     * Open web link as a string using browser
     *
     * @param context - Context to start browser activity
     * @param webLink - Website Link as a string to open in browser
     */
    public static void openWebsiteUsingBrowser(Context context, String webLink) {
        Uri uriUrl = Uri.parse(webLink);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        context.startActivity(launchBrowser);
    }

    /**
     * Open web link as a uri using browser
     *
     * @param context - Context to start browser activity
     * @param webLink - Website Link as a uri to open in browser
     */
    public static void openWebsiteUsingBrowser(Context context, Uri webLink) {
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, webLink);
        context.startActivity(launchBrowser);
    }

}
