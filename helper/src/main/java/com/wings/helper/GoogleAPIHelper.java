package com.wings.helper;


import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Purpose: Google Location API methods
 *
 * @author NikunjD
 * Created on June 13, 2019
 * Modified on June 13, 2019
 */
public class GoogleAPIHelper {


    public enum DistanceUnit {
        DISTANCE_METER,
        DISTANCE_KM,
        DISTANCE_MILE
    }

    /**
     * <p>
     * <b>Travel modes:</b>
     * driving (default) indicates distance calculation using the road network.
     * walking requests distance calculation for walking via pedestrian paths & sidewalks (where available).
     * bicycling requests distance calculation for bicycling via bicycle paths & preferred streets (where available).
     * transit requests distance calculation via public transit routes (where available). This value may only be specified if the request includes an API key or a Google Maps APIs Premium Plan client ID. If you set the mode to transit you can optionally specify either a departure_time or an arrival_time. If neither time is specified, the departure_time defaults to now (that is, the departure time defaults to the current time). You can also optionally include a transit_mode and/or a transit_routing_preference.
     * </p>
     */
    public enum TravelMode {
        TRAVEL_MODE_WALKING("walking"),
        TRAVEL_MODE_BICYCLING("bicycling"),
        TRAVEL_MODE_DRIVING("driving"),
        TRAVEL_MODE_TRANSIT("transit");

        String value = "";

        TravelMode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * <p>
     * <b>Google Distance Unit:</b>
     * units=metric (default) returns distances in kilometers and meters.
     * units=imperial returns distances in miles and feet.
     * </p>
     */
    public enum GoogleDistanceUnitSystem {
        MATRIC_UNIT("matric"),
        IMPERIAL_UNIT("imperial");

        String value = "";

        GoogleDistanceUnitSystem(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Get Location snapshot (image) using google location api
     * <b>Location API should be enable in google console</b>
     *
     * @param latitude                location's latitude
     * @param longitude               location's longitude
     * @param zoomLevel               Google map zoom level (ie. 1 - 20)
     * @param locationImageResolution Google map desired image resolution (ie. 300x300)
     * @param mapType                 type of map
     * @param markerColor             marker color
     * @param markerLabel             marker label in single character
     * @param googleAPIKey            google api key (should be valid)
     * @return path for location snapshot that can be shown using image loading technique.
     */
    public static String getGoogleMapLocationSnapshot(double latitude, double longitude, int zoomLevel, String locationImageResolution,
                                                      String mapType, String markerColor, char markerLabel,
                                                      String googleAPIKey) {
        return "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude +
                "&zoom=" + zoomLevel +
                "&size=" + locationImageResolution +
                "&maptype=" + mapType +
                "&markers=color:" + markerColor +
                "|label:" + Character.toUpperCase(markerLabel) +
                "|" + latitude + "," + longitude +
                "&key=" + googleAPIKey;
    }


    /**
     * Calculate Distance using travel mode with using google api
     * <b>Distance API should be enable in google console</b>
     *
     * @param sourceLatitude       source latitude
     * @param sourceLongitude      source longitude
     * @param destinationLatitude  destination latitude
     * @param destinationLongitude destination longitude
     * @param distanceTravelMode   travel mode
     * @param distanceUnit         distance unit type
     * @param googleAPIKey         google api key (should be valid)
     * @return distance
     */
    public static String calculateDistanceWithTravelMode(final double sourceLatitude, final double sourceLongitude,
                                                         final double destinationLatitude,
                                                         final double destinationLongitude,
                                                         final TravelMode distanceTravelMode,
                                                         final GoogleDistanceUnitSystem distanceUnit,
                                                         final String googleAPIKey) {
        final String[] parsedDistance = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?" +
                            "origin=" + sourceLatitude + "," + sourceLongitude +
                            "&destination=" + destinationLatitude + "," + destinationLongitude +
                            "&sensor=false" +
                            "&units=" + distanceUnit.getValue() +
                            "&mode=" + distanceTravelMode.getValue() +
                            "&key=" + googleAPIKey);
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                    br.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("routes");
                    if (array != null && array.length() > 0) {
                        JSONObject routes = array.getJSONObject(0);
                        JSONArray legs = routes.getJSONArray("legs");
                        if (legs != null && legs.length() > 0) {
                            JSONObject steps = legs.getJSONObject(0);
                            if (steps != null) {
                                JSONObject distance = steps.getJSONObject("distance");
                                if (distance != null) {
                                    parsedDistance[0] = distance.getString("text");
                                }
                            }
                        }
                    }

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String distance = "";
        if (parsedDistance[0] != null) {
            distance = parsedDistance[0];
        }
        return distance;
    }


    /**
     * Calculate distance between two location without travel mode
     *
     * @param sourceLatitude       source latitude
     * @param sourceLongitude      source longitude
     * @param destinationLatitude  destination latitude
     * @param destinationLongitude destination longitude
     * @param distanceUnit         distance unit
     * @return distance
     */
    public static String calculationByDistance(final double sourceLatitude, final double sourceLongitude,
                                               final double destinationLatitude,
                                               final double destinationLongitude, DistanceUnit distanceUnit) {

        Location sourceLocation = new Location("");
        sourceLocation.setLatitude(sourceLatitude);
        sourceLocation.setLongitude(sourceLongitude);

        Location destinationLocation = new Location("");
        destinationLocation.setLatitude(destinationLatitude);
        destinationLocation.setLongitude(destinationLongitude);

        float distance = sourceLocation.distanceTo(destinationLocation);

        switch (distanceUnit) {
            case DISTANCE_KM:
                distance = distance / 1000;
                break;
            case DISTANCE_MILE:
                distance = distance / 1609.34f;
                break;
        }

        return String.valueOf(Math.round(distance));
    }


    
}
