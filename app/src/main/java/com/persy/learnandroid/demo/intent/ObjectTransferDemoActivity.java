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
import com.persy.learnandroid.demo.model.Student;

public class ObjectTransferDemoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edtName, edtAge, edtClassName;
    private Button btnSend;
    private TextView tvExplanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intent_send_object);
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

    private void setupDemo() {
        btnSend.setOnClickListener(view -> {
            String name = edtName.getText().toString().trim();
            String ageText = edtAge.getText().toString().trim();
            int age = 0;
            if (!ageText.isEmpty()) {
                age = Integer.parseInt(ageText);
            }
            String className = edtClassName.getText().toString().trim();

            Student student = new Student(name, age, className);

            Intent intent = new Intent(ObjectTransferDemoActivity.this, ReceiveDataActivity.class);
            intent.putExtra("DATA_TYPE", "STUDENT");
            intent.putExtra("student", student);
            startActivity(intent);
        });
    }

    private void setTextExplanation() {
        tvExplanation.setText(
                Html.fromHtml(
                        getString(R.string.bundle_explanation),
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
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtClassName = findViewById(R.id.edtClassName);
        btnSend = findViewById(R.id.btnSend);
        tvExplanation = findViewById(R.id.tvExplanation);
    }
}