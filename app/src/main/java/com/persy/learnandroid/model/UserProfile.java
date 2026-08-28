package com.persy.learnandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

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

    public String getDisplayName() {
        if (fullName != null && !fullName.trim().isEmpty()) return fullName;
        if (username != null && !username.trim().isEmpty()) return username;
        return "Chưa có tên";
    }

    public String getAvatarLetter() {
        String name = getDisplayName();
        return (name != null && !name.trim().isEmpty())
                ? name.trim().substring(0, 1).toUpperCase(Locale.getDefault())
                : "U";
    }
}