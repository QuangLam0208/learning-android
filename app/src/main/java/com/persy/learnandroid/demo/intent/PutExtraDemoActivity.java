package com.persy.learnandroid.demo.intent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.persy.learnandroid.R;

public class PutExtraDemoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edtName, edtAge;
    private Switch switchStudent;
    private Button btnSend;
    private TextView tvExplanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intent_send_extra);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewMapping();
        setupToolBar();
        setTextExplanation();
        setupDemo();
    }

    private void viewMapping() {
        toolbar = findViewById(R.id.toolbar);
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        switchStudent = findViewById(R.id.switchStudent);
        btnSend = findViewById(R.id.btnSend);
        tvExplanation = findViewById(R.id.tvExplanation);
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

    private void setupDemo() {
        btnSend.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String ageText = edtAge.getText().toString().trim();
            int age = 0;
            if (!ageText.isEmpty()) {
                age = Integer.parseInt(ageText);
            }
            boolean isStudent = switchStudent.isChecked();

            Intent intent = new Intent(PutExtraDemoActivity.this, ReceiveDataActivity.class);
            intent.putExtra("DATA_TYPE", "PUT_EXTRA");
            intent.putExtra("name", name);
            intent.putExtra("age", age);
            intent.putExtra("isStudent", isStudent);
            startActivity(intent);
        });
    }

    private void setTextExplanation() {
        tvExplanation.setText(
                Html.fromHtml(
                        getString(R.string.put_extra_explanation),
                        Html.FROM_HTML_MODE_LEGACY
                )
        );
    }
}