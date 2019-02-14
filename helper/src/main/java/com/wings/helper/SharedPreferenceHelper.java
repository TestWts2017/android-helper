package com.wings.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.Set;

/**
 * Purpose: Easy to manage Shared Preferences
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 13, 2019
 */

public class SharedPreferenceHelper {
    private static SharedPreferenceHelper singleton = null;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public SharedPreferenceHelper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public SharedPreferenceHelper(Context context, String name, int mode) {
        preferences = context.getSharedPreferences(name, mode);
        editor = preferences.edit();
    }

    public static SharedPreferenceHelper with(Context context) {
        if (singleton == null) {
            singleton = new Builder(context, null, -1).build();
        }
        return singleton;
    }

    public static SharedPreferenceHelper with(Context context, String name, int mode) {
        if (singleton == null) {
            singleton = new Builder(context, name, mode).build();
        }
        return singleton;
    }

    public void save(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public void save(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void save(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public void save(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public void save(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public void save(String key, Set<String> value) {
        editor.putStringSet(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void remove(String key) {
        editor.remove(key).apply();
    }

    public void removeAll() {
        editor.clear().apply();
    }

    public boolean contains(String key) {
        return preferences.contains(key);
    }

    private static class Builder {

        private final Context context;
        private final int mode;
        private final String name;

        public Builder(Context context, String name, int mode) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
            this.name = name;
            this.mode = mode;
        }

        /**
         * Method that creates an instance of Prefs
         * @return an instance of Prefs
         */
        public SharedPreferenceHelper build() {
            if (mode == -1 || name == null) {
                return new SharedPreferenceHelper(context);
            }
            return new SharedPreferenceHelper(context, name, mode);
        }
    }
}
