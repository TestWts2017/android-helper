package com.wings.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Purpose: Open location with map using latitude, longitude and address.
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 13, 2019
 */
public class MapLocationHelper {

    /**
     * @param context **Apply context to launch Map
     * @param latitude **Apply latitude as a double
     * @param longitude **Apply longitude as a double
     * @param address **Apply address of location
     */
    public static void openMapLocation(Context context, double latitude, double longitude, String address) {
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + address + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

}
