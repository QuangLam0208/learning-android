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
import com.persy.learnandroid.databinding.ActivityProfileDemoBinding;
import com.persy.learnandroid.model.ApiResponse;
import com.persy.learnandroid.model.UserProfile;
import com.persy.learnandroid.service.ApiService;
import com.persy.learnandroid.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileDemoBinding binding;
    private TokenManager tokenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_demo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tokenManager = new TokenManager(this);

        setupToolBar();

        if (!tokenManager.isLoggedIn() || tokenManager.isTokenExpired()) {
            handleSessionExpired();
            return;
        }

        binding.btnLogout.setOnClickListener(v -> logout());

        loadProfile();
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thông tin cá nhân");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadProfile() {
        binding.progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiRetrofitClient.getRetrofit(this).create(ApiService.class);
        apiService.getProfile().enqueue(new Callback<ApiResponse<UserProfile>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserProfile>> call, Response<ApiResponse<UserProfile>> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.code() == 401) {
                    handleSessionExpired();
                    return;
                }

                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    UserProfile userProfile = response.body().getData();
                    bindProfileToForm(userProfile);
                } else {
                    Toast.makeText(ProfileActivity.this,
                            "Không lấy được thông tin: " + (response.body() != null ? response.body().getMessage() : response.message()),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserProfile>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void bindProfileToForm(UserProfile profile) {
        binding.setProfile(profile);
        binding.setPermissionSummary(buildPermissionSummary(profile));
        binding.executePendingBindings();
    }

    private String buildPermissionSummary(UserProfile profile) {
        if (profile == null || profile.getGroup() == null) return "";
        int permissionCount = profile.getGroup().getPermissions() != null
                ? profile.getGroup().getPermissions().size() : 0;
        String desc = profile.getGroup().getDescription() != null ? profile.getGroup().getDescription() : "";
        return "Nhóm: " + desc + " (" + permissionCount + " quyền)";
    }

    private void handleSessionExpired() {
        Toast.makeText(this, "Phiên đăng nhập đã hết hạn", Toast.LENGTH_SHORT).show();
        tokenManager.clear();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
        if (topicTitle != null) {
            intent.putExtra("EXTRA_TOPIC_TITLE", topicTitle);
        }
        startActivity(intent);
        finish();
    }

    private void logout() {
        tokenManager.clear();
        Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
        if (topicTitle != null) {
            intent.putExtra("EXTRA_TOPIC_TITLE", topicTitle);
        }
        startActivity(intent);
        finish();
    }
}