package com.persy.learnandroid.model;

public enum ETopicCategory {

    LAYOUTS("Layouts"),
    CONTROLS("Controls"),
    INTENT_BUNDLE("Intent & Bundle"),
    RECYCLERVIEW("RecyclerView");

    private final String value;

    ETopicCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
