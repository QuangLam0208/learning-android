package com.persy.learnandroid.api;

import android.content.Context;

import com.persy.learnandroid.utils.TokenManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @deprecated Legacy manual singleton client. Use Dagger DI {@link com.persy.learnandroid.di.NetworkModule} instead.
 */
@Deprecated
public class ApiRetrofitClient {

    private static final String BASE_URL = "https://swirl-almighty-detector.ngrok-free.dev/";
    private static Retrofit retrofit;
    private static TokenManager tokenManager;
    private static OkHttpClient okHttpClient;

    public static Retrofit getRetrofit(Context context) {
        if (retrofit == null) {

            tokenManager = new TokenManager(context);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);

            okHttpClient = new OkHttpClient.Builder()
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

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}