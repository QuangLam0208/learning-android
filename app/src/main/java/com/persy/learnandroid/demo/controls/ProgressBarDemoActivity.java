package com.persy.learnandroid.demo.controls;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.persy.learnandroid.R;

public class ProgressBarDemoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvExplanation, tvProgressBasic, tvProgressCustom;
    private ProgressBar progressBarBasic, progressBarCustom;
    private Button btnIncreaseProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_control_progressbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewMapping();
        setupToolBar();
        setTextExplanation();
        setUpProgressBar();
    }

    private void setUpProgressBar() {
        progressBarBasic.setMax(100);
        progressBarBasic.setProgress(40);
        progressBarCustom.setMax(100);
        progressBarCustom.setProgress(70);

        updateBasicProgressText();
        updateCustomProgressText();

        btnIncreaseProgress.setOnClickListener(v -> {
            int current = progressBarBasic.getProgress();
            if (current < progressBarBasic.getMax()) {
                progressBarBasic.setProgress(current + 10);
                updateBasicProgressText();
            }
        });
    }

    private void updateBasicProgressText() {
        tvProgressBasic.setText(
                "Progress: " + progressBarBasic.getProgress() + " / " + progressBarBasic.getMax()
        );
    }

    private void updateCustomProgressText() {
        tvProgressCustom.setText(
                "Progress: " + progressBarCustom.getProgress() + " / " + progressBarCustom.getMax()
        );
    }

    private void viewMapping() {
        toolbar = findViewById(R.id.toolbar);
        tvExplanation = findViewById(R.id.tvExplanation);
        tvProgressBasic = findViewById(R.id.tvProgressBasic);
        tvProgressCustom = findViewById(R.id.tvProgressCustom);
        progressBarBasic = findViewById(R.id.progressBarBasic);
        progressBarCustom = findViewById(R.id.progressBarCustom);
        btnIncreaseProgress = findViewById(R.id.btnIncreaseProgress);
    }

    private void setTextExplanation() {
        tvExplanation.setText(
                Html.fromHtml(
                        getString(R.string.progressbar_explanation),
                        Html.FROM_HTML_MODE_LEGACY
                )
        );
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());
    }
}