package com.persy.learnandroid.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("grant_type")
    private String grantType;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public LoginRequest(String grantType, String username, String password) {
        this.grantType = grantType;
        this.username = username;
        this.password = password;
    }
}