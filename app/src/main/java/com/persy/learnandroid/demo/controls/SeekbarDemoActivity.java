package com.persy.learnandroid.demo.controls;

import android.os.Bundle;
import android.text.Html;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.persy.learnandroid.R;

public class SeekbarDemoActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView tvSeekbarValue, tvSeekbarStatus, tvSeekbarMax, tvExplanation;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_control_seekbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        seekBar = findViewById(R.id.seekBar);
        tvSeekbarValue = findViewById(R.id.tvSeekbarValue);
        tvSeekbarStatus = findViewById(R.id.tvSeekbarStatus);
        tvSeekbarMax = findViewById(R.id.tvSeekbarMax);
        tvExplanation = findViewById(R.id.tvExplanation);
        toolbar = findViewById(R.id.toolbar);

        setupToolBar();
        setTextExplanation();
        setupSeekBar();
    }

    private void setupSeekBar() {
        seekBar.setMax(100);
        seekBar.setProgress(50);
        tvSeekbarMax.setText(
                "Max: " + seekBar.getMax()
        );
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        tvSeekbarValue.setText(String.valueOf(i));
                        if (b) {
                            tvSeekbarStatus.setText("Đang điều chỉnh...");
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        tvSeekbarStatus.setText("Bắt đầu kéo");
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        tvSeekbarStatus.setText("Đã dừng kéo");
                    }
                }
        );
    }

    private void setTextExplanation() {
        tvExplanation.setText(
                Html.fromHtml(
                        getString(R.string.seekbar_explanation),
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