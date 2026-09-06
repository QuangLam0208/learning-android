package com.persy.learnandroid.di;

import android.content.Context;

import androidx.room3.Room;

import com.persy.learnandroid.database.TodoDAO;
import com.persy.learnandroid.database.TodoDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private static final String DATABASE_NAME = "todo.db";

    @Provides
    @Singleton
    public static TodoDatabase provideTodoDatabase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                TodoDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Provides
    @Singleton
    public static TodoDAO provideTodoDAO(TodoDatabase database) {
        return database.todoDAO();
    }
}