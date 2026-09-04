package com.persy.learnandroid.demo.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.persy.learnandroid.MyApplication;
import com.persy.learnandroid.R;
import com.persy.learnandroid.adapter.PermissionAdapter;
import com.persy.learnandroid.api.ApiService;
import com.persy.learnandroid.databinding.ActivityProfileDemoBinding;
import com.persy.learnandroid.databinding.BottomSheetPermissionsBinding;
import com.persy.learnandroid.model.ApiResponse;
import com.persy.learnandroid.model.UserGroup;
import com.persy.learnandroid.model.UserProfile;
import com.persy.learnandroid.utils.TokenManager;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileDemoBinding binding;

    @Inject
    TokenManager tokenManager;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplication()).getAppComponent().inject(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_demo);
        binding.setActivity(this);
        binding.setLifecycleOwner(this);
        binding.setIsLoading(false);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();

        if (!tokenManager.isLoggedIn() || tokenManager.isTokenExpired()) {
            handleSessionExpired();
            return;
        }

        loadProfile();

        System.out.println("[ProfileActivity] TokenManager: " + tokenManager);
        System.out.println("[ProfileActivity] ApiService: " + apiService);
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle != null ? topicTitle : "Thông tin cá nhân");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadProfile() {
        binding.setIsLoading(true);

        apiService.getProfile().enqueue(new Callback<ApiResponse<UserProfile>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserProfile>> call, Response<ApiResponse<UserProfile>> response) {
                binding.setIsLoading(false);

                if (response.code() == 401) {
                    handleSessionExpired();
                    return;
                }

                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    UserProfile userProfile = response.body().getData();
                    binding.setUser(userProfile);
                } else {
                    Toast.makeText(ProfileActivity.this,
                            "Không lấy được thông tin: " + (response.body() != null ? response.body().getMessage() : response.message()),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserProfile>> call, Throwable t) {
                binding.setIsLoading(false);
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showPermissionsBottomSheet(UserGroup group) {
        if (group == null) return;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        BottomSheetPermissionsBinding sheetBinding = BottomSheetPermissionsBinding.inflate(LayoutInflater.from(this));

        sheetBinding.rvPermissions.setAdapter(new PermissionAdapter());

        sheetBinding.setGroup(group);
        sheetBinding.setDialog(bottomSheetDialog);

        bottomSheetDialog.setContentView(sheetBinding.getRoot());
        bottomSheetDialog.show();
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

    public void logout() {
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