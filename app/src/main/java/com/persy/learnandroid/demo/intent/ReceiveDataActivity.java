package com.persy.learnandroid.demo.intent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.persy.learnandroid.R;
import com.persy.learnandroid.demo.model.Student;

public class ReceiveDataActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvDataType, tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intent_receive_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewMapping();
        setupToolBar();
        receiveData();
    }

    private void receiveData() {
        String dataType = getIntent().getStringExtra("DATA_TYPE");

        if (dataType == null) {
            tvDataType.setText("Data type: Unknown");
            tvResult.setText("Không có dữ liệu.");
            return;
        }

        tvDataType.setText("Data type: " + dataType);

        switch (dataType) {
            case "PUT_EXTRA":
                receivePutExtra();
                break;
            case "BUNDLE":
                receiveBundle();
                break;
            case "STUDENT":
                receiveStudent();
                break;
            default:
                tvResult.setText("Không xác định được loại dữ liệu.");
                break;
        }
    }

    private void receivePutExtra() {
        String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0);
        boolean isStudent = getIntent().getBooleanExtra("isStudent", false);
        String result = "Name: " + name + "\nAge: " + age + "\nIs student: " + isStudent;
        tvResult.setText(result);
    }
    private void receiveBundle() {
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            tvResult.setText("Không có Bundle được truyền.");
            return;
        }

        String name = bundle.getString("name");
        int age = bundle.getInt("age");
        String email = bundle.getString("email");
        boolean isStudent = bundle.getBoolean("isStudent");

        String result = "Name: " + name + "\nAge: " + age + "\nEmail: " + email + "\nIs student: " + isStudent;
        tvResult.setText(result);
    }

    private void receiveStudent() {
        Student student = (Student) getIntent().getSerializableExtra("student");
        if (student == null) {
            tvResult.setText("Không nhận được Student Object.");
            return;
        }
        String result =
                "Student Object" + "\n\nName: " + student.getName() + "\nAge: " + student.getAge() + "\nClass: " + student.getClassName();
        tvResult.setText(result);
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
        tvDataType = findViewById(R.id.tvDataType);
        tvResult = findViewById(R.id.tvResult);
    }

}