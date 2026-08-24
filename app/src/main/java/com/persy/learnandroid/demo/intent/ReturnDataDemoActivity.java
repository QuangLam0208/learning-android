package com.persy.learnandroid.demo.intent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.persy.learnandroid.R;

public class ReturnDataDemoActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private TextView tvExplanation, tvResult;

    private Button btnOpenSelect;

    private ActivityResultLauncher<Intent> selectDataLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intent_return_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewMapping();
        setupToolBar();
        setTextExplanation();
        setupActivityResult();
        setupDemo();
    }

    private void setupDemo() {
        btnOpenSelect.setOnClickListener(view -> {
            Intent intent = new Intent(ReturnDataDemoActivity.this, SelectDataActivity.class);
            selectDataLauncher.launch(intent);
        });
    }

    private void setupActivityResult() {
        selectDataLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String selectedValue = data.getStringExtra("selected_value");
                            if (selectedValue != null) {
                                tvResult.setText(selectedValue);
                            }
                        }
                    }
                }
        );
    }

    private void setTextExplanation() {
        tvExplanation.setText(
                Html.fromHtml(
                        getString(R.string.return_data_explanation),
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

    private void viewMapping() {
        toolbar = findViewById(R.id.toolbar);
        tvExplanation = findViewById(R.id.tvExplanation);
        tvResult = findViewById(R.id.tvResult);
        btnOpenSelect = findViewById(R.id.btnOpenSelect);
    }
}