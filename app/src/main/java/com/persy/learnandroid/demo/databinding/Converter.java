package com.persy.learnandroid.demo.databinding;

import androidx.databinding.InverseMethod;

public class Converter {

    @InverseMethod("stringToInt")
    public static String intToString(int value) {
        return value == 0 ? "" : String.valueOf(value);
    }

    public static int stringToInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}