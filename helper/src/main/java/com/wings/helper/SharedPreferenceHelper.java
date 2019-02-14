package com.wings.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.Set;

/**
 * Purpose: Easy to manage Shared Preferences
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 14, 2019
 */

public class SharedPreferenceHelper {
    private static SharedPreferenceHelper singleton = null;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    /**
     * Default shared preference
     *
     * @param context Context for default shared preference
     */
    public SharedPreferenceHelper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    /**
     * Shared preference as per name and preference mode
     *
     * @param context Context for get shared preference
     * @param name    Name of shared preference
     * @param mode    Mode of shared preference
     */
    public SharedPreferenceHelper(Context context, String name, int mode) {
        preferences = context.getSharedPreferences(name, mode);
        editor = preferences.edit();
    }

    /**
     * Default Builder
     *
     * @param context Context for get shared preference
     * @return SharedPreferenceHelper - Shared preference helper object
     */
    public static SharedPreferenceHelper with(Context context) {
        if (singleton == null) {
            singleton = new Builder(context, null, -1).build();
        }
        return singleton;
    }

    /**
     * Builder with name and preference mode
     *
     * @param context Context for get shared preference
     * @param name    Name of shared preference
     * @param mode    Mode of shared preference
     * @return SharedPreferenceHelper - Shared preference helper object
     */
    public static SharedPreferenceHelper with(Context context, String name, int mode) {
        if (singleton == null) {
            singleton = new Builder(context, name, mode).build();
        }
        return singleton;
    }

    /**
     * Save a boolean shared preference
     *
     * @param key   Key to set shared preference
     * @param value Value for the key
     */
    public void save(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    /**
     * Save a string shared preference
     *
     * @param key   Key to set shared preference
     * @param value Value for the key
     */
    public void save(String key, String value) {
        editor.putString(key, value).apply();
    }

    /**
     * Save a integer shared preference
     *
     * @param key   Key to set shared preference
     * @param value Value for the key
     */
    public void save(String key, int value) {
        editor.putInt(key, value).apply();
    }

    /**
     * Save a float shared preference
     *
     * @param key   Key to set shared preference
     * @param value Value for the key
     */
    public void save(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    /**
     * Save a long shared preference
     *
     * @param key   Key to set shared preference
     * @param value Value for the key
     */
    public void save(String key, long value) {
        editor.putLong(key, value).apply();
    }

    /**
     * Save a set of string shared preference
     *
     * @param key   Key to set shared preference
     * @param value Value for the key
     */
    public void save(String key, Set<String> value) {
        editor.putStringSet(key, value).apply();
    }

    /**
     * Get a boolean shared preference
     *
     * @param key      Key to look up in shared preferences.
     * @param defValue Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    /**
     * Get a string shared preference
     *
     * @param key      Key to look up in shared preferences.
     * @param defValue Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    /**
     * Get a integer shared preference
     *
     * @param key      Key to look up in shared preferences.
     * @param defValue Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    /**
     * Get a float shared preference
     *
     * @param key      Key to look up in shared preferences.
     * @param defValue Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    /**
     * Get a long shared preference
     *
     * @param key      Key to look up in shared preferences.
     * @param defValue Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    /**
     * Get a set of string shared preference
     *
     * @param key      Key to look up in shared preferences.
     * @param defValue Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }

    /**
     * Get all shared preferences
     *
     * @return value - All shared preferences
     */
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    /**
     * Remove from shared preference
     *
     * @param key Key to remove from shared preference
     */
    public void remove(String key) {
        editor.remove(key).apply();
    }

    /**
     * Remove all key value pairs from shared preference
     */
    public void removeAll() {
        editor.clear().apply();
    }

    /**
     * Check whether key is available in shared preference
     *
     * @param key Key to look up in shared preference
     * @return value - Value as a boolean (true or false)
     */
    public boolean contains(String key) {
        return preferences.contains(key);
    }

    private static class Builder {

        private final Context context;
        private final int mode;
        private final String name;

        /**
         * Builder class constructor
         *
         * @param context Context for shared preference
         * @param name    Name of shared preference
         * @param mode    Mode of shared preference
         */
        public Builder(Context context, String name, int mode) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
            this.name = name;
            this.mode = mode;
        }

        /**
         * Method that creates instance of preference
         *
         * @return value - Value of shared preference helper object
         */
        public SharedPreferenceHelper build() {
            if (mode == -1 || name == null) {
                return new SharedPreferenceHelper(context);
            }
            return new SharedPreferenceHelper(context, name, mode);
        }
    }
}
