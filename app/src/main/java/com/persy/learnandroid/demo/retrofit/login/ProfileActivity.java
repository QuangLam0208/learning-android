package com.persy.learnandroid.demo.retrofit.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.persy.learnandroid.R;
import com.persy.learnandroid.client.ApiRetrofitClient;
import com.persy.learnandroid.model.ApiResponse;
import com.persy.learnandroid.model.UserProfile;
import com.persy.learnandroid.service.ProfileApiService;
import com.persy.learnandroid.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private EditText edtUserId, edtUsername, edtFullName, edtEmail, edtGroup;
    private TextView tvPermissionSummary;
    private SwitchMaterial switchSuperAdmin;
    private Button btnLogout;

    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tokenManager = new TokenManager(this);

        viewMapping();
        setupToolBar();

        if (!tokenManager.isLoggedIn() || tokenManager.isTokenExpired()) {
            handleSessionExpired();
            return;
        }

        btnLogout.setOnClickListener(v -> logout());

        loadProfile();
    }

    private void viewMapping() {
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        edtUserId = findViewById(R.id.edtUserId);
        edtUsername = findViewById(R.id.edtUsername);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtGroup = findViewById(R.id.edtGroup);
        tvPermissionSummary = findViewById(R.id.tvPermissionSummary);
        switchSuperAdmin = findViewById(R.id.switchSuperAdmin);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thông tin cá nhân");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadProfile() {
        progressBar.setVisibility(View.VISIBLE);

        ProfileApiService profileApiService = ApiRetrofitClient.getRetrofit(this).create(ProfileApiService.class);
        profileApiService.getProfile().enqueue(new Callback<ApiResponse<UserProfile>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserProfile>> call, Response<ApiResponse<UserProfile>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.code() == 401) {
                    handleSessionExpired();
                    return;
                }

                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    UserProfile userProfile = response.body().getData();
                    renderProfileToForm(userProfile);
                } else {
                    Toast.makeText(ProfileActivity.this,
                            "Không lấy được thông tin: " + (response.body() != null ? response.body().getMessage() : response.message()),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserProfile>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void renderProfileToForm(UserProfile profile) {
        if (profile == null) return;

        edtUserId.setText(String.valueOf(profile.getId()));
        edtUsername.setText(profile.getUsername() != null ? profile.getUsername() : "");
        edtFullName.setText(profile.getFullName() != null ? profile.getFullName() : "");
        edtEmail.setText(profile.getEmail() != null ? profile.getEmail() : "");

        if (profile.getGroup() != null) {
            edtGroup.setText(profile.getGroup().getName() != null ? profile.getGroup().getName() : "");
            int permissionCount = profile.getGroup().getPermissions() != null
                    ? profile.getGroup().getPermissions().size() : 0;
            String desc = profile.getGroup().getDescription() != null ? profile.getGroup().getDescription() : "";
            tvPermissionSummary.setText("Nhóm: " + desc + " (" + permissionCount + " quyền)");
        } else {
            edtGroup.setText("Chưa phân nhóm");
            tvPermissionSummary.setText("");
        }

        switchSuperAdmin.setChecked(profile.isSuperAdmin());
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