package com.wings.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Purpose: Open location with map using latitude, longitude and address.
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 14, 2019
 */
public class MapLocationHelper {

    /**
     * Open View to show latitude and longitude
     *
     * @param context   Context to launch Map
     * @param latitude  Latitude as a double
     * @param longitude Longitude as a double
     * @param address   Address of location
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
