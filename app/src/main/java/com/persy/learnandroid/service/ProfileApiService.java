package com.persy.learnandroid.service;

import com.persy.learnandroid.model.ApiResponse;
import com.persy.learnandroid.model.UserProfile;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProfileApiService {
    @GET("v1/account/profile")
    Call<ApiResponse<UserProfile>> getProfile();
}