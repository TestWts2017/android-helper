package com.wings.helper;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.wings.utils.HttpHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Purpose: Google GeoCoding, Reverse GeoCoding, Place AutoComplete, Direction & Place API
 *
 * @author HetalD
 * Created On June 26,2019
 * Modified On June 26,2019
 */

public class GooglePlaceAPIHelper {

    /**
     * Google GeoCoding API - Get Address From Latitude & Longitude
     *
     * @param address Address to get Latitude & Longitude
     * @param API_KEY Google Authorized API KEY
     * @return List of latitude & longitude
     */
    public static Location getLatLngFromAddress(final String address, final String API_KEY) {

        final Location location = new Location(LocationManager.NETWORK_PROVIDER);
        location.setLatitude(0.0);
        location.setLongitude(0.0);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?" + "address=" + address + "&key=" + API_KEY);
                    HttpHandler handler = new HttpHandler();
                    String jsonStr = handler.makeServiceCall(String.valueOf(url));

                    if (jsonStr != null) {
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        if (jsonArray != null && jsonArray.length() > 0) {
                            JSONObject geometryObject = jsonArray.getJSONObject(2).getJSONObject("geometry");

                            if (geometryObject != null) {
                                JSONObject locationObject = geometryObject.getJSONObject("location");
                                double lat = locationObject.getDouble("lat");
                                double lng = locationObject.getDouble("lng");

                                location.setLatitude(lat);
                                location.setLongitude(lng);

                                //String lat_lng = "Lat: " + lat + "\n" + "Long: " + lng;
                            }
                        }
                    }
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

        return location;
    }

    /**
     * Google Reverse GeoCoding API - Get Latitude & Longitude From Address
     *
     * @param latitude  Double latitude
     * @param longitude Double longitude
     * @param API_KEY   Google Authorized API KEY
     * @return String[] address
     */
    public static String getAddressFromLatLng(final double latitude, final double longitude, final String API_KEY) {

        final List<Double> list = new ArrayList<>();

        final String[] formatted_address = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?" + "latlng=" + latitude + "," + longitude + "&key=" + API_KEY);
                    HttpHandler handler = new HttpHandler();
                    String jsonStr = handler.makeServiceCall(String.valueOf(url));

                    if (jsonStr != null) {
                        JSONObject jsonObject = new JSONObject(jsonStr);

                        JSONArray latlngArray = jsonObject.getJSONArray("results");
                        if (latlngArray != null && latlngArray.length() > 0) {

                            JSONObject addressObj = latlngArray.getJSONObject(1);
                            formatted_address[0] = addressObj.getString("formatted_address");
                        }

                    }
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

        String address = "";
        if (formatted_address[0] != null) {
            address = formatted_address[0];
        }
        return address;
    }


    /**
     * Google Place AutoComplete API
     *
     * @param context                     context to activity
     * @param API_KEY                     Google Authorized API KEY
     * @param autocompleteSupportFragment AutocompleteSupportFragment to search
     * @return String of Place ID & Place Name
     */
    public static String getPlaceAutoComplete(Context context, String API_KEY, AutocompleteSupportFragment autocompleteSupportFragment) {

        final String[] place_str = new String[1];
        Places.initialize(context, API_KEY);
        PlacesClient placesClient = Places.createClient(context);

        if (autocompleteSupportFragment != null) {
            autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

            autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    // textView.setText(place.getName()+","+place.getId());
                    // Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());

                    place_str[0] = place.getName() + "," + place.getId();
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    //Log.i(TAG, "An error occurred: " + status);
                }
            });
        }

        String places = "";
        if (place_str[0] != null) {
            places = place_str[0];
        }
        return places;
    }


    /**
     * Google Direction API - Get direction between two locations (Ex: turn left,turn right...)
     *
     * @param sourceAddress      Source address
     * @param destinationAddress Destination address
     * @param API_KEY            Google Authorized API KEY
     * @return List of direction points
     */
    public static List<Object> getDirectionBetweenTwoLocation(final String sourceAddress, final String destinationAddress, final String API_KEY) {

        final List<String> list = new ArrayList<>();
        final List<Object> directionList = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?" + "origin=" + sourceAddress + "&destination=" + destinationAddress + "&key=" + API_KEY);
                    HttpHandler handler = new HttpHandler();
                    String jsonStr = handler.makeServiceCall(String.valueOf(url));

                    if (jsonStr != null) {

                        JSONObject jsonObject = new JSONObject(jsonStr);
                        JSONArray jsonArray = jsonObject.getJSONArray("routes");

                        if (jsonArray != null && jsonArray.length() > 0) {
                            JSONArray legsObject = jsonArray.getJSONObject(2).getJSONArray("legs");
                            if (legsObject != null) {
                                JSONArray stepsArray = legsObject.getJSONObject(6).getJSONArray("steps");
                                if (stepsArray != null && stepsArray.length() > 0) {
                                    for (int i = 1; i < stepsArray.length(); i++) {
                                        JSONObject object = stepsArray.getJSONObject(i);
                                        System.out.println("response1" + object);

                                        String maneuver = object.getString("maneuver");
                                        list.add(maneuver);
                                    }
                                    directionList.addAll(list);
                                }
                            }
                        }

                    }
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

        return directionList;
    }

    /**
     * Google Place API - Get mid locations between two locations
     *
     * @param sourceAddress      Source address
     * @param destinationAddress Destination address
     * @param API_KEY            Google Authorized API KEY
     * @return List of mid location points
     */
    public static List<Object> getMidLocationBetweenTwoLocation(final String sourceAddress, final String destinationAddress, final String API_KEY) {


        final List<String> list = new ArrayList<>();
        final List<Object> midPointList = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?" + "origin=" + sourceAddress + "&destination=" + destinationAddress + "&key=" + API_KEY);
                    HttpHandler handler = new HttpHandler();
                    String jsonStr = handler.makeServiceCall(String.valueOf(url));

                    if (jsonStr != null) {

                        JSONObject jsonObject = new JSONObject(jsonStr);
                        JSONArray jsonArray = jsonObject.getJSONArray("routes");

                        System.out.println("response:" + jsonArray);

                        if (jsonArray != null && jsonArray.length() > 0) {
                            JSONArray legsObject = jsonArray.getJSONObject(2).getJSONArray("legs");
                            if (legsObject != null) {
                                JSONArray stepsArray = legsObject.getJSONObject(6).getJSONArray("steps");
                                if (stepsArray != null && stepsArray.length() > 0) {
                                    for (int i = 0; i < stepsArray.length(); i++) {
                                        JSONObject object = stepsArray.getJSONObject(i);

                                        if (object.has("end_location")) {
                                            JSONObject end_locationObject = object.getJSONObject("end_location");
                                            double lat = end_locationObject.getDouble("lat");
                                            double lng = end_locationObject.getDouble("lng");

                                            URL lat_lng_url = new URL("https://maps.googleapis.com/maps/api/geocode/json?" + "latlng=" + lat + "," + lng + "&key=" + API_KEY);

                                            JSONObject latlngObject = new JSONObject(String.valueOf(lat_lng_url));

                                            JSONArray latlngArray = latlngObject.getJSONArray("results");

                                            if (latlngArray != null && latlngArray.length() > 0) {
                                                JSONObject addressObj = latlngArray.getJSONObject(1);
                                                String formatted_address = addressObj.getString("formatted_address");
                                                list.add(formatted_address);
                                            }
                                        }
                                    }
                                    midPointList.addAll(list);
                                }
                            }
                        }
                    }
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

        return midPointList;
    }
}
