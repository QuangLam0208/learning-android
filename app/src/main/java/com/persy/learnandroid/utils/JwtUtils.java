package com.persy.learnandroid.utils;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JwtUtils {

    public static long getExpirationMillis(String jwtToken) {
        try {
            String[] parts = jwtToken.split("\\.");
            if (parts.length < 2) return 0;

            byte[] decodedBytes = Base64.decode(
                    parts[1], Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
            String payload = new String(decodedBytes, StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(payload);
            long expSeconds = json.optLong("exp", 0);
            return expSeconds * 1000L;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isExpired(String jwtToken) {
        long expMillis = getExpirationMillis(jwtToken);
        if (expMillis == 0) return true;
        return System.currentTimeMillis() >= expMillis;
    }
}