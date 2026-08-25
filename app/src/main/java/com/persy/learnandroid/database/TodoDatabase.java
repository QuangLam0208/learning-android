package com.persy.learnandroid.database;

import android.content.Context;

import androidx.room3.ColumnTypeConverters;
import androidx.room3.Database;
import androidx.room3.Room;
import androidx.room3.RoomDatabase;

import com.persy.learnandroid.model.Todo;

@Database(entities = {Todo.class}, version = 1)
@ColumnTypeConverters(Converters.class)
public abstract class TodoDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "todo.db";
    private static TodoDatabase instance;

    public static synchronized TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TodoDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract TodoDAO todoDAO();

}
