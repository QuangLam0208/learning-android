package com.persy.learnandroid.database;

import android.content.Context;

import androidx.room3.ColumnTypeConverters;
import androidx.room3.DaoReturnTypeConverters;
import androidx.room3.Database;
import androidx.room3.Room;
import androidx.room3.RoomDatabase;
import androidx.room3.livedata.LiveDataDaoReturnTypeConverter;

import com.persy.learnandroid.model.Todo;

@Database(entities = {Todo.class}, version = 1)
@ColumnTypeConverters(Converters.class)
@DaoReturnTypeConverters(LiveDataDaoReturnTypeConverter.class)
public abstract class TodoDatabase extends RoomDatabase {

    public abstract TodoDAO todoDAO();

}
