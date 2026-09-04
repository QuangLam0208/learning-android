package com.persy.learnandroid.demo.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.persy.learnandroid.MyApplication;
import com.persy.learnandroid.R;
import com.persy.learnandroid.api.ApiService;
import com.persy.learnandroid.databinding.ActivityLoginDemoBinding;
import com.persy.learnandroid.model.LoginRequest;
import com.persy.learnandroid.model.LoginResponse;
import com.persy.learnandroid.utils.TokenManager;

import javax.inject.Inject;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "abc_client";
    private static final String CLIENT_SECRET = "abc123";

    private ActivityLoginDemoBinding binding;

    @Inject
    TokenManager tokenManager;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplication()).getAppComponent().inject(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_demo);
        binding.setActivity(this);
        binding.setIsLoading(false);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
        binding.edtUsername.setText("admin");
        binding.edtPassword.setText("admin123654");

        if (tokenManager.isLoggedIn() && !tokenManager.isTokenExpired()) {
            openProfile();
        }

        System.out.println("[LoginActivity] TokenManager: " + tokenManager);
        System.out.println("[LoginActivity] ApiService: " + apiService);
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

    public void doLogin() {
        String username = binding.edtUsername.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }
        binding.setIsLoading(true);

        String basicCredentials = Credentials.basic(CLIENT_ID, CLIENT_SECRET);
        LoginRequest request = new LoginRequest("password", username, password);

        apiService.login(basicCredentials, request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                binding.setIsLoading(false);
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
                binding.setIsLoading(false);
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openProfile() {
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
        intent.putExtra("EXTRA_TOPIC_TITLE", topicTitle != null ? topicTitle : "Profile");
        startActivity(intent);
        finish();
    }
}