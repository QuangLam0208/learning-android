package com.persy.learnandroid.demo.intent;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.persy.learnandroid.R;
import com.persy.learnandroid.databinding.ActivityIntentReturnDataBinding;

public class ReturnDataDemoActivity extends AppCompatActivity {

    private ActivityIntentReturnDataBinding binding;
    private ActivityResultLauncher<Intent> selectDataLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intent_return_data);
        binding.setActivity(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
        setupActivityResult();
    }

    public void openSelectData() {
        Intent intent = new Intent(ReturnDataDemoActivity.this, SelectDataActivity.class);
        selectDataLauncher.launch(intent);
    }

    private void setupActivityResult() {
        selectDataLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String selectedValue = data.getStringExtra("selected_value");
                        if (selectedValue != null) {
                            binding.setResultText(selectedValue);
                        }
                    }
                }
        );
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(view -> finish());
    }
}