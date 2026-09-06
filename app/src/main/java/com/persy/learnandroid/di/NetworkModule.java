package com.persy.learnandroid.di;

import com.persy.learnandroid.BuildConfig;
import com.persy.learnandroid.api.ApiService;
import com.persy.learnandroid.utils.TokenManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String AUTH_BASE_URL = "https://ai-project-api.devflux.io.vn/";
    private static final String AI_BASE_URL = "https://ai-api.example.com/";

    @Provides
    @Singleton
    public static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

    @Provides
    @Singleton
    public static GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(
            TokenManager tokenManager,
            HttpLoggingInterceptor logging
    ) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder();

                    String path = originalRequest.url().encodedPath();
                    if (!path.equals("/api/token")) {
                        String token = tokenManager.getAccessToken();
                        if (token != null) {
                            requestBuilder.header("Authorization", "Bearer " + token);
                        }
                    }
                    return chain.proceed(requestBuilder.build());
                })
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @AuthNetwork
    public static Retrofit provideRetrofit(
            OkHttpClient okHttpClient,
            GsonConverterFactory converterFactory
    ) {
        return new Retrofit.Builder()
                .baseUrl(AUTH_BASE_URL)
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @AuthNetwork
    public static ApiService provideAuthApiService(@AuthNetwork Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    @AiNetwork
    public static Retrofit provideAiRetrofit(
            OkHttpClient okHttpClient,
            GsonConverterFactory converterFactory
    ) {
        return new Retrofit.Builder()
                .baseUrl(AI_BASE_URL)
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @AiNetwork
    public static ApiService provideAiApiService(@AiNetwork Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}