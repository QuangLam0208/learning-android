package com.persy.learnandroid.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserGroup {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("kind")
    private int kind;

    @SerializedName("subKind")
    private int subKind;

    @SerializedName("status")
    private int status;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("modifiedDate")
    private String modifiedDate;

    @SerializedName("isSystemRole")
    private boolean isSystemRole;

    @SerializedName("permissions")
    private List<Permission> permissions;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Permission> getPermissions() { return permissions; }
    public boolean isSystemRole() { return isSystemRole; }
}