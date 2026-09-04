package com.persy.learnandroid.di;

import android.app.Application;
import android.content.Context;

import com.persy.learnandroid.utils.TokenManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    TokenManager provideTokenManager(Context context) {
        return new TokenManager(context);
    }
}