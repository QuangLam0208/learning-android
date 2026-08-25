package com.persy.learnandroid.database;


import androidx.room3.ColumnTypeConverter;

import java.util.Date;

public class Converters {
    @ColumnTypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @ColumnTypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
