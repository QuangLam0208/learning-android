package com.persy.learnandroid.service;

import com.persy.learnandroid.model.LoginRequest;
import com.persy.learnandroid.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("api/token")
    Call<LoginResponse> login(
            @Header("Authorization") String basicAuthCredentials,
            @Body LoginRequest loginRequest
    );
}