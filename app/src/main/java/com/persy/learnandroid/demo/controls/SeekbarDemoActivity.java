package com.persy.learnandroid.demo.controls;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.persy.learnandroid.R;
import com.persy.learnandroid.databinding.ActivityControlSeekbarBinding;

public class SeekbarDemoActivity extends AppCompatActivity {

    private ActivityControlSeekbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_control_seekbar);
        binding.setActivity(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
        initData();
    }

    private void initData() {
        binding.setMax(100);
        binding.setProgress(50);
        binding.setStatus("Chưa thao tác");
    }

    public void onProgressChanged(int progress, boolean fromUser) {
        binding.setProgress(progress);
        if (fromUser) {
            binding.setStatus("Đang điều chỉnh...");
        }
    }

    public void onStartTrackingTouch() {
        binding.setStatus("Bắt đầu kéo");
    }

    public void onStopTrackingTouch() {
        binding.setStatus("Đã dừng kéo");
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle != null ? topicTitle : "SeekBar Demo");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(view -> finish());
    }
}