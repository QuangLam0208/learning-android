package com.persy.learnandroid.di;

import android.content.Context;

import com.persy.learnandroid.database.TodoDAO;
import com.persy.learnandroid.database.TodoDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public static TodoDatabase provideTodoDatabase(Context context) {
        return TodoDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public static TodoDAO provideTodoDAO(TodoDatabase database) {
        return database.todoDAO();
    }
}