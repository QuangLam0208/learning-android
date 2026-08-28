package com.persy.learnandroid.demo.retrofit.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.persy.learnandroid.R;
import com.persy.learnandroid.client.ApiRetrofitClient;
import com.persy.learnandroid.databinding.ActivityLoginDemoBinding;
import com.persy.learnandroid.service.ApiService;
import com.persy.learnandroid.model.LoginRequest;
import com.persy.learnandroid.model.LoginResponse;
import com.persy.learnandroid.utils.TokenManager;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "abc_client";
    private static final String CLIENT_SECRET = "abc123";

    private ActivityLoginDemoBinding binding;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_demo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tokenManager = new TokenManager(this);

        setupToolBar();
        setupLogin();

        if (tokenManager.isLoggedIn() && !tokenManager.isTokenExpired()) {
            openProfile();
        }
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle != null ? topicTitle : "Login Demo");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupLogin() {
        binding.edtUsername.setText("admin");
        binding.edtPassword.setText("admin123654");
        binding.btnLogin.setOnClickListener(v -> doLogin());
    }

    private void doLogin() {
        String username = binding.edtUsername.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }
        setLoading(true);

        String basicCredentials = Credentials.basic(CLIENT_ID, CLIENT_SECRET);
        LoginRequest request = new LoginRequest("password", username, password);

        ApiService apiService = ApiRetrofitClient.getRetrofit(this).create(ApiService.class);
        apiService.login(basicCredentials, request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse body = response.body();
                    tokenManager.saveTokens(body.getAccessToken(), body.getRefreshToken(), body.getExpiresIn());
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    openProfile();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Đăng nhập thất bại (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        binding.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.btnLogin.setEnabled(!loading);
    }

    private void openProfile() {
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
        intent.putExtra("EXTRA_TOPIC_TITLE", topicTitle != null ? topicTitle : "Profile");
        startActivity(intent);
        finish();
    }
}