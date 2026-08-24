package com.persy.learnandroid.demo.controls;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.persy.learnandroid.R;

public class EditTextDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_control_edit_text);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvExplanation = findViewById(R.id.tvExplanation);
        tvExplanation.setText(
                Html.fromHtml(
                        getString(R.string.edit_text_explanation),
                        Html.FROM_HTML_MODE_LEGACY
                )
        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        TextInputLayout tilEmail = findViewById(R.id.tilEmail);
        TextInputEditText edtEmail = findViewById(R.id.edtEmail);

        edtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && edtEmail.getText().toString().trim().isEmpty()) {
                tilEmail.setError("Email không được để trống");
            } else {
                tilEmail.setError(null);
            }
        });
    }
}