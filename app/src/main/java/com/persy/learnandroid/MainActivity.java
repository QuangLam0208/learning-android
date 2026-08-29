package com.persy.learnandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.persy.learnandroid.adapter.TopicAdapter;
import com.persy.learnandroid.data.TopicRepository;
import com.persy.learnandroid.databinding.ActivityMainBinding;
import com.persy.learnandroid.model.Topic;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
        setupRecyclerView();
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Android Learning Demo");
        }
    }

    private void setupRecyclerView() {
        binding.rvMainTopics.setLayoutManager(new LinearLayoutManager(this));
        List<Topic> mainTopics = TopicRepository.getMainTopics();

        TopicAdapter adapter = new TopicAdapter(mainTopics, topic -> {
            Intent intent = new Intent(MainActivity.this, ComponentListActivity.class);
            intent.putExtra("EXTRA_CATEGORY", topic.getId());
            startActivity(intent);
        });

        binding.rvMainTopics.setAdapter(adapter);
    }
}