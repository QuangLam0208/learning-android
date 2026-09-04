package com.persy.learnandroid.demo.databinding;

import androidx.databinding.ObservableField;

public class User {
    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> fullName = new ObservableField<>("");
    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> phone = new ObservableField<>("");
    public final ObservableField<String> address = new ObservableField<>("");

}
