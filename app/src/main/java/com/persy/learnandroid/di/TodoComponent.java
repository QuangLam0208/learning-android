package com.persy.learnandroid.di;

import com.persy.learnandroid.demo.roomdb.TodoDemoActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface TodoComponent {

    @Subcomponent.Factory
    interface Factory {
        TodoComponent create();
    }

    void inject(TodoDemoActivity activity);
}
