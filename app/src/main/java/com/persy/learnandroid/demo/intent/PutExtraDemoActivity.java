package com.persy.learnandroid.demo.intent;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.persy.learnandroid.R;
import com.persy.learnandroid.databinding.ActivityIntentSendExtraBinding;

public class PutExtraDemoActivity extends AppCompatActivity {

    private ActivityIntentSendExtraBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intent_send_extra);
        binding.setActivity(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
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

    public void onSendClick() {
        String name = binding.edtName.getText().toString().trim();
        String ageText = binding.edtAge.getText().toString().trim();
        int age = 0;
        if (!ageText.isEmpty()) {
            age = Integer.parseInt(ageText);
        }
        boolean isStudent = binding.switchStudent.isChecked();

        Intent intent = new Intent(PutExtraDemoActivity.this, ReceiveDataActivity.class);
        intent.putExtra("DATA_TYPE", "PUT_EXTRA");
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        intent.putExtra("isStudent", isStudent);
        startActivity(intent);
    }
}