package com.persy.learnandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_EXPIRES_AT = "expires_at";

    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveTokens(String accessToken, String refreshToken, long expiresIn) {
        long expiresAt = System.currentTimeMillis() + expiresIn * 1000L;
        prefs.edit()
                .putString(KEY_ACCESS_TOKEN, accessToken)
                .putString(KEY_REFRESH_TOKEN, refreshToken)
                .putLong(KEY_EXPIRES_AT, expiresAt)
                .apply();
    }

    public String getAccessToken() {
        return prefs.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getRefreshToken() {
        return prefs.getString(KEY_REFRESH_TOKEN, null);
    }

    public long getExpiresAt() {
        return prefs.getLong(KEY_EXPIRES_AT, 0);
    }

    public boolean isLoggedIn() {
        return getAccessToken() != null;
    }

    public boolean isTokenExpired() {
        long expiresAt = getExpiresAt();
        if (expiresAt == 0) {
            return true;
        }
        return System.currentTimeMillis() >= expiresAt;
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}