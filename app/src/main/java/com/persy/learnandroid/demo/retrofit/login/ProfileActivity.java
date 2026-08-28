package com.persy.learnandroid.demo.retrofit.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.persy.learnandroid.R;
import com.persy.learnandroid.adapter.PermissionAdapter;
import com.persy.learnandroid.client.ApiRetrofitClient;
import com.persy.learnandroid.model.ApiResponse;
import com.persy.learnandroid.model.UserGroup;
import com.persy.learnandroid.model.UserProfile;
import com.persy.learnandroid.service.ProfileApiService;
import com.persy.learnandroid.utils.TokenManager;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView tvAvatarLetter;
    private TextView tvHeaderFullName;
    private LinearLayout layoutSuperAdminBadge;
    private EditText edtUserId, edtUsername, edtFullName, edtEmail, edtGroup;
    private LinearLayout rowViewPermissions;
    private TextView tvPermissionActionText;
    private AppCompatButton btnLogout;
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

        tvAvatarLetter = findViewById(R.id.tvAvatarLetter);
        tvHeaderFullName = findViewById(R.id.tvHeaderFullName);
        layoutSuperAdminBadge = findViewById(R.id.layoutSuperAdminBadge);

        edtUserId = findViewById(R.id.edtUserId);
        edtUsername = findViewById(R.id.edtUsername);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtGroup = findViewById(R.id.edtGroup);

        rowViewPermissions = findViewById(R.id.rowViewPermissions);
        tvPermissionActionText = findViewById(R.id.tvPermissionActionText);

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

        String displayName = profile.getFullName();
        if (displayName == null || displayName.trim().isEmpty()) {
            displayName = profile.getUsername();
        }
        if (displayName != null && !displayName.trim().isEmpty()) {
            tvAvatarLetter.setText(displayName.trim().substring(0, 1).toUpperCase(Locale.getDefault()));
        } else {
            tvAvatarLetter.setText("U");
        }

        tvHeaderFullName.setText(profile.getFullName() != null ? profile.getFullName() : (profile.getUsername() != null ? profile.getUsername() : "Chưa có tên"));

        if (profile.isSuperAdmin()) {
            layoutSuperAdminBadge.setVisibility(View.VISIBLE);
        } else {
            layoutSuperAdminBadge.setVisibility(View.GONE);
        }

        edtUserId.setText(profile.getId() != null ? String.valueOf(profile.getId()) : "");
        edtUsername.setText(profile.getUsername() != null ? profile.getUsername() : "");
        edtFullName.setText(profile.getFullName() != null ? profile.getFullName() : "");
        edtEmail.setText(profile.getEmail() != null ? profile.getEmail() : "");

        UserGroup group = profile.getGroup();
        if (group != null) {
            edtGroup.setText(group.getName() != null ? group.getName() : "");
            int permissionCount = group.getPermissions() != null ? group.getPermissions().size() : 0;
            tvPermissionActionText.setText("Xem chi tiết " + permissionCount + " quyền");

            rowViewPermissions.setEnabled(true);
            rowViewPermissions.setVisibility(View.VISIBLE);
            rowViewPermissions.setOnClickListener(v -> showPermissionsBottomSheet(group));
        } else {
            edtGroup.setText("Chưa phân nhóm");
            rowViewPermissions.setVisibility(View.GONE);
            rowViewPermissions.setEnabled(false);
            rowViewPermissions.setOnClickListener(null);
        }
    }

    private void showPermissionsBottomSheet(UserGroup group) {
        if (group == null) return;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_permissions, null);
        bottomSheetDialog.setContentView(sheetView);

        TextView tvSheetSubtitle = sheetView.findViewById(R.id.tvSheetSubtitle);
        ImageButton btnCloseSheet = sheetView.findViewById(R.id.btnCloseSheet);
        RecyclerView rvPermissions = sheetView.findViewById(R.id.rvPermissions);
        TextView tvEmptyPermissions = sheetView.findViewById(R.id.tvEmptyPermissions);

        int count = group.getPermissions() != null ? group.getPermissions().size() : 0;
        String groupName = group.getName() != null ? group.getName() : "Nhóm quyền";
        tvSheetSubtitle.setText("Nhóm: " + groupName + " • " + count + " quyền");

        PermissionAdapter adapter = new PermissionAdapter();
        rvPermissions.setAdapter(adapter);

        if (group.getPermissions() != null && !group.getPermissions().isEmpty()) {
            adapter.setPermissions(group.getPermissions());
            tvEmptyPermissions.setVisibility(View.GONE);
            rvPermissions.setVisibility(View.VISIBLE);
        } else {
            tvEmptyPermissions.setVisibility(View.VISIBLE);
            rvPermissions.setVisibility(View.GONE);
        }

        btnCloseSheet.setOnClickListener(v -> bottomSheetDialog.dismiss());

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