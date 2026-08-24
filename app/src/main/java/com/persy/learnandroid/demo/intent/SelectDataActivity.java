package com.persy.learnandroid.demo.intent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.persy.learnandroid.R;

public class SelectDataActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private RadioGroup radioGroup;

    private RadioButton radioAndroid, radioJava, radioKotlin;

    private Button btnConfirm, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intent_select_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewMapping();
        setupToolBar();
        setupDemo();
    }

    private void setupDemo() {
        btnConfirm.setOnClickListener(view -> returnSelectData());
        btnCancel.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void returnSelectData() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) return;

        RadioButton selectedRadio = findViewById(selectedId);
        String selectedValue = selectedRadio.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("selected_value", selectedValue);
        setResult(RESULT_OK, resultIntent);
        finish();
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
        radioGroup = findViewById(R.id.radioGroup);
        radioAndroid = findViewById(R.id.radioAndroid);
        radioJava = findViewById(R.id.radioJava);
        radioKotlin = findViewById(R.id.radioKotlin);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);
    }

}