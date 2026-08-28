package com.persy.learnandroid.demo.controls;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.persy.learnandroid.R;
import com.persy.learnandroid.databinding.ActivityControlProgressbarBinding;

public class ProgressBarDemoActivity extends AppCompatActivity {

    private ActivityControlProgressbarBinding binding;
    private int currentProgress = 40;
    private static final int MAX_PROGRESS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_control_progressbar);
        binding.setActivity(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
        setupProgressData();
    }

    private void setupProgressData() {
        binding.setMaxBasic(MAX_PROGRESS);
        binding.setProgressBasic(currentProgress);
        binding.setMaxCustom(MAX_PROGRESS);
        binding.setProgressCustom(70);
    }

    public void onIncreaseProgress() {
        if (currentProgress < MAX_PROGRESS) {
            currentProgress += 10;
            binding.setProgressBasic(currentProgress);
            binding.setProgressCustom(currentProgress);
        }
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle != null ? topicTitle : "ProgressBar Demo");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(view -> finish());
    }
}