package com.persy.learnandroid.di;

import android.app.Application;
import android.content.Context;

import com.persy.learnandroid.utils.SharedPrefsManager;
import com.persy.learnandroid.utils.TokenManager;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Binds
    @Singleton
    abstract Context bindContext(Application application);
}