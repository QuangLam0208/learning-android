package com.persy.learnandroid;

import android.app.Application;

import com.persy.learnandroid.di.AppComponent;
import com.persy.learnandroid.di.AppModule;
import com.persy.learnandroid.di.DaggerAppComponent;

public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.factory().create(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}