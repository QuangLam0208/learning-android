package com.persy.learnandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPrefsManager {
    private static final String PREF_NAME = "app_prefs";
    private static volatile SharedPrefsManager instance;
    private final SharedPreferences prefs;
    @Inject
    public SharedPrefsManager(Context context) {
        this.prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefsManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPrefsManager.class) {
                if (instance == null) {
                    instance = new SharedPrefsManager(context);
                }
            }
        }
        return instance;
    }

    public void put(String key, Object value) {
        SharedPreferences.Editor editor = prefs.edit();
        if (value == null) {
            editor.remove(key);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else {
            throw new IllegalArgumentException("Type " + value.getClass().getSimpleName() + " is not supported in SharedPreferences directly.");
        }
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public int getInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    public boolean contains(String key) {
        return prefs.contains(key);
    }

    public void remove(String key) {
        prefs.edit().remove(key).apply();
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}