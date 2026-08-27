package com.persy.learnandroid.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("result")
    private boolean result;
    @SerializedName("code")
    private String code;
    @SerializedName("httpCode")
    private int httpCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;

    public boolean isResult() { return result; }
    public String getCode() { return code; }
    public int getHttpCode() { return httpCode; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}