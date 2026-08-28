package com.persy.learnandroid.demo.intent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.persy.learnandroid.R;
import com.persy.learnandroid.databinding.ActivityIntentSelectDataBinding;

public class SelectDataActivity extends AppCompatActivity {

    private ActivityIntentSelectDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intent_select_data);
        binding.setActivity(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
    }

    public void onConfirm() {
        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) return;

        RadioButton selectedRadio = findViewById(selectedId);
        String selectedValue = selectedRadio.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("selected_value", selectedValue);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onCancel() {
        setResult(RESULT_CANCELED);
        finish();
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