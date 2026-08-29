package com.persy.learnandroid.api;

import com.persy.learnandroid.model.ApiResponse;
import com.persy.learnandroid.model.LoginRequest;
import com.persy.learnandroid.model.LoginResponse;
import com.persy.learnandroid.model.UserProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/token")
    Call<LoginResponse> login(
            @Header("Authorization") String basicAuthCredentials,
            @Body LoginRequest loginRequest
    );

    @GET("v1/account/profile")
    Call<ApiResponse<UserProfile>> getProfile();
}