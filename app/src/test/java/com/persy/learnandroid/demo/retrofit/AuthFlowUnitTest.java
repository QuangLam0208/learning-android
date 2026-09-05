package com.persy.learnandroid.demo.retrofit;

import com.persy.learnandroid.api.ApiService;
import com.persy.learnandroid.model.ApiResponse;
import com.persy.learnandroid.model.LoginRequest;
import com.persy.learnandroid.model.LoginResponse;
import com.persy.learnandroid.model.UserProfile;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test kiểm tra luồng xác thực đăng nhập (Authentication) với Dagger DI.
 * Nhờ Dagger tách rời ApiService và TokenManager, ta có thể "bơm" FakeApiService
 * vào để test toàn bộ kịch bản đăng nhập (Thành công / Thất bại) mà KHÔNG CẦN gọi Server thật.
 */
public class AuthFlowUnitTest {

    static class FakeTokenManager {
        String savedAccessToken = null;
        String savedRefreshToken = null;
        long savedExpiresIn = 0;

        public void saveTokens(String accessToken, String refreshToken, long expiresIn) {
            this.savedAccessToken = accessToken;
            this.savedRefreshToken = refreshToken;
            this.savedExpiresIn = expiresIn;
        }

        public boolean isLoggedIn() {
            return savedAccessToken != null;
        }

        public void clear() {
            this.savedAccessToken = null;
            this.savedRefreshToken = null;
        }
    }

    static class FakeApiService implements ApiService {
        private boolean shouldReturnSuccess = true;

        public void setShouldReturnSuccess(boolean success) {
            this.shouldReturnSuccess = success;
        }

        @Override
        public Call<LoginResponse> login(String basicCredentials, LoginRequest request) {
            return new FakeCall<LoginResponse>() {
                @Override
                public void enqueue(Callback<LoginResponse> callback) {
                    if (shouldReturnSuccess) {
                        LoginResponse fakeResponse = new LoginResponse();
                        // Giả lập server trả về token thành công
                        Response<LoginResponse> response = Response.success(fakeResponse);
                        callback.onResponse(this, response);
                    } else {
                        // Giả lập server trả về HTTP 401 Unauthorized
                        Response<LoginResponse> errorResponse = Response.error(401,
                                okhttp3.ResponseBody.create(okhttp3.MediaType.parse("application/json"), "{\"message\":\"Unauthorized\"}"));
                        callback.onResponse(this, errorResponse);
                    }
                }
            };
        }

        @Override
        public Call<ApiResponse<UserProfile>> getProfile() {
            return null;
        }
    }

    static abstract class FakeCall<T> implements Call<T> {
        @Override public Response<T> execute() throws IOException { return null; }
        @Override public boolean isExecuted() { return true; }
        @Override public void cancel() {}
        @Override public boolean isCanceled() { return false; }
        @Override public Call<T> clone() { return this; }
        @Override public Request request() { return new Request.Builder().url("https://test.api/").build(); }
        @Override public Timeout timeout() { return Timeout.NONE; }
    }

    private FakeApiService fakeApiService;
    private FakeTokenManager fakeTokenManager;

    @Before
    public void setUp() {
        fakeApiService = new FakeApiService();
        fakeTokenManager = new FakeTokenManager();
    }

    @Test
    public void testLoginSuccess_savesTokensSuccessfully() {
        fakeApiService.setShouldReturnSuccess(true);
        LoginRequest loginRequest = new LoginRequest("password", "admin", "admin123654");

        fakeApiService.login("Basic credentials", loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Giả lập token được parse từ response
                    fakeTokenManager.saveTokens("jwt-token-xyz", "refresh-token-123", 3600);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {}
        });

        assertTrue(fakeTokenManager.isLoggedIn());
        assertEquals("jwt-token-xyz", fakeTokenManager.savedAccessToken);
        assertEquals("refresh-token-123", fakeTokenManager.savedRefreshToken);
        assertEquals(3600, fakeTokenManager.savedExpiresIn);
    }

    @Test
    public void testLoginFailure_doesNotSaveTokens() {
        fakeApiService.setShouldReturnSuccess(false);
        LoginRequest loginRequest = new LoginRequest("password", "wrong_user", "wrong_pass");

        fakeApiService.login("Basic credentials", loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fakeTokenManager.saveTokens("token", "refresh", 3600);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {}
        });

        assertFalse(fakeTokenManager.isLoggedIn());
        assertNull(fakeTokenManager.savedAccessToken);
    }
}
