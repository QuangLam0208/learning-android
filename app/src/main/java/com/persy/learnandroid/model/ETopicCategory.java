package com.persy.learnandroid.model;

public enum ETopicCategory {

    LAYOUTS("Layouts"),
    CONTROLS("Controls"),
    INTENT_BUNDLE("Intent & Bundle"),
    RECYCLERVIEW("RecyclerView"),
    ROOM_DATABASE("SQLite & RoomDatabase"),
    RETROFIT("Retrofit"),
    DATA_BINDING("Data Binding & Two-way Data Binding"),
    DAGGER("Dagger - Dependency Injection");

    private final String value;
    ETopicCategory(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
