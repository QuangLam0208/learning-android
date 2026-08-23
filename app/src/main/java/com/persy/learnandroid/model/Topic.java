package com.persy.learnandroid.model;

public class Topic {
    private String id;
    private String title;
    private String description;
    private String targetActivityKey;
    private boolean hasChildren;

    public Topic(String id, String title, String description, String targetActivityKey, boolean hasChildren) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.targetActivityKey = targetActivityKey;
        this.hasChildren = hasChildren;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTargetActivityKey() { return targetActivityKey; }
    public boolean isHasChildren() { return hasChildren; }

    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setTargetActivityKey(String targetActivityKey) { this.targetActivityKey = targetActivityKey; }
    public void setHasChildren(boolean hasChildren) { this.hasChildren = hasChildren; }
}
