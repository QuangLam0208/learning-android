package com.persy.learnandroid.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("id")
    private Long id;

    @SerializedName("kind")
    private int kind;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("group")
    private UserGroup group;

    @SerializedName("isSuperAdmin")
    private boolean isSuperAdmin;

    public Long getId() { return id; }
    public int getKind() { return kind; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public UserGroup getGroup() { return group; }
    public boolean isSuperAdmin() { return isSuperAdmin; }
}