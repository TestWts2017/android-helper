package com.wings.helper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Purpose: JSON Related task - parsing, get data from json using key etc.
 *
 * @author NikunjD
 * Created on June 18, 2019
 * Modified on June 18, 2019
 */
public class JSONHelper {

    /**
     * Get string value from json
     *
     * @param object json object
     * @param key    key to get value from json
     * @return value if json contains key, otherwise blank
     */
    public static String getStringValueFromJSONObject(JSONObject object, String key) {
        String value = "";
        try {
            if (object != null && object.has(key)) {
                value = object.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * Get integer value from json
     *
     * @param object json object
     * @param key    key to get value from json
     * @return value if json contains key, otherwise zero
     */
    public static int getIntValueFromJSONObject(JSONObject object, String key) {
        int value = 0;
        try {
            if (object != null && object.has(key)) {
                value = object.getInt(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * Get boolean value from json
     *
     * @param object json object
     * @param key    key to get value from json
     * @return value if json contains key, otherwise false
     */
    public static boolean getBooleanValueFromJSONObject(JSONObject object, String key) {
        boolean value = false;
        try {
            if (object != null && object.has(key)) {
                value = object.getBoolean(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * Get double value from json
     *
     * @param object json object
     * @param key    key to get value from json
     * @return value if json contains key, otherwise zero
     */
    public static double getDoubleValueFromJSONObject(JSONObject object, String key) {
        double value = 0.0;
        try {
            if (object != null && object.has(key)) {
                value = object.getDouble(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * Get Json Object from Json Object
     *
     * @param object json object
     * @param key    key to get json object
     * @return json object if object contains key, otherwise null
     */
    public static JSONObject getJSONObjectFromJSONObject(JSONObject object, String key) {
        JSONObject jsonObject = null;
        try {
            if (object != null && object.has(key)) {
                jsonObject = object.getJSONObject(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
