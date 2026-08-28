package com.persy.learnandroid.demo.intent;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.persy.learnandroid.R;
import com.persy.learnandroid.databinding.ActivityIntentReceiveDataBinding;
import com.persy.learnandroid.model.Student;

public class ReceiveDataActivity extends AppCompatActivity {

    private ActivityIntentReceiveDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intent_receive_data);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
        receiveData();
    }

    private void receiveData() {
        String dataType = getIntent().getStringExtra("DATA_TYPE");
        binding.setDataType(dataType);

        if (dataType == null) {
            binding.setResult("Không có dữ liệu.");
            return;
        }

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
                binding.setResult("Không xác định được loại dữ liệu.");
                break;
        }
    }

    private void receivePutExtra() {
        String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0);
        boolean isStudent = getIntent().getBooleanExtra("isStudent", false);
        binding.setResult("Name: " + name + "\nAge: " + age + "\nIs student: " + isStudent);
    }

    private void receiveBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            binding.setResult("Không có Bundle được truyền.");
            return;
        }
        String name = bundle.getString("name");
        int age = bundle.getInt("age");
        String email = bundle.getString("email");
        boolean isStudent = bundle.getBoolean("isStudent");
        binding.setResult("Name: " + name + "\nAge: " + age + "\nEmail: " + email + "\nIs student: " + isStudent);
    }

    private void receiveStudent() {
        Student student = getIntent().getParcelableExtra("student", Student.class);
        if (student == null) {
            binding.setResult("Không nhận được Student Object.");
            return;
        }
        binding.setResult("Student Object\n\nName: " + student.getName() + "\nAge: " + student.getAge() + "\nClass: " + student.getClassName());
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle != null ? topicTitle : "Receive Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(view -> finish());
    }
}