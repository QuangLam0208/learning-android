package com.persy.learnandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class TokenManager {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_EXPIRES_AT = "expires_at";

    private final SharedPrefsManager prefsManager;

    @Inject
    public TokenManager(SharedPrefsManager prefsManager) {
        this.prefsManager = prefsManager;
    }

    public void saveTokens(String accessToken, String refreshToken, long expiresIn) {
        long expiresAt = System.currentTimeMillis() + expiresIn * 1000L;
        prefsManager.put(KEY_ACCESS_TOKEN, accessToken);
        prefsManager.put(KEY_REFRESH_TOKEN, refreshToken);
        prefsManager.put(KEY_EXPIRES_AT, expiresAt);
    }

    public String getAccessToken() {
        return prefsManager.getString(KEY_ACCESS_TOKEN);
    }

    public String getRefreshToken() {
        return prefsManager.getString(KEY_REFRESH_TOKEN);
    }

    public long getExpiresAt() {
        return prefsManager.getLong(KEY_EXPIRES_AT, 0L);
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
        prefsManager.remove(KEY_ACCESS_TOKEN);
        prefsManager.remove(KEY_REFRESH_TOKEN);
        prefsManager.remove(KEY_EXPIRES_AT);
    }
}