package com.persy.learnandroid.model;

import com.google.gson.annotations.SerializedName;

public class Permission {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("action")
    private String action;
    @SerializedName("showMenu")
    private boolean showMenu;
    @SerializedName("description")
    private String description;
    @SerializedName("nameGroup")
    private String nameGroup;
    @SerializedName("pcode")
    private String pcode;

    public long getId() { return id; }
    public String getName() { return name; }
    public String getAction() { return action; }
    public boolean isShowMenu() { return showMenu; }
    public String getDescription() { return description; }
    public String getNameGroup() { return nameGroup; }
    public String getPcode() { return pcode; }
}