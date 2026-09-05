package com.persy.learnandroid.di;


import android.app.Application;

import com.persy.learnandroid.MainActivity;
import com.persy.learnandroid.demo.retrofit.LoginActivity;
import com.persy.learnandroid.demo.retrofit.ProfileActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        DatabaseModule.class
})
public interface AppComponent {

    void inject(MainActivity activity);
    void inject(LoginActivity activity);
    void inject(ProfileActivity activity);

    TodoComponent.Factory todoComponentFactory();

    @Component.Factory
    interface Factory {
        AppComponent create(@BindsInstance Application application);
    }
}