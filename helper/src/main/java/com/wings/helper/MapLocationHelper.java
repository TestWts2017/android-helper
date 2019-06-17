package com.wings.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Purpose: Open location with map using latitude, longitude and address.
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on June 13, 2019
 */
public class MapLocationHelper {

    /**
     * Open map location externally using latitude and longitude
     *
     * @param activity  Activity to launch Map
     * @param latitude  Latitude as a double
     * @param longitude Longitude as a double
     * @param address   Address of location
     */
    public static void openMapLocation(Activity activity, double latitude, double longitude, String address) {
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + address + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }


    /**
     * Open map location externally using location address
     *
     * @param activity Activity to launch Map
     * @param address  address of location
     */
    public static void openMapLocation(Activity activity, String address) {
        String map = "http://maps.google.co.in/maps?q=" + address;
        Uri uri = Uri.parse(map);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

}
