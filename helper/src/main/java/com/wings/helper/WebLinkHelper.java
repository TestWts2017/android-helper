package com.wings.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Purpose: Open website using browser.
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 13, 2019
 */
public class WebLinkHelper {

    /**
     * @param context **Apply context to launch browser
     * @param webLink **Apply web site as a string like with "https"
     */
    public static void openWebsiteUsingBrowser(Context context, String webLink) {
        Uri uriUrl = Uri.parse(webLink);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        context.startActivity(launchBrowser);
    }

    /**
     * @param context **Apply context to launch browser
     * @param webLink **Apply web site as a uri like with "https"
     */
    public static void openWebsiteUsingBrowser(Context context, Uri webLink) {
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, webLink);
        context.startActivity(launchBrowser);
    }

}
