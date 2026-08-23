package com.persy.learnandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.adapter.TopicAdapter;
import com.persy.learnandroid.data.TopicRepository;
import com.persy.learnandroid.model.Topic;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Android Learning Demo");
        }

        RecyclerView rvMainTopics = findViewById(R.id.rvMainTopics);
        rvMainTopics.setLayoutManager(new LinearLayoutManager(this));

        List<Topic> mainTopics = TopicRepository.getMainTopics();

        TopicAdapter adapter = new TopicAdapter(mainTopics, new TopicAdapter.OnTopicClickListener() {
            @Override
            public void onTopicClick(Topic topic) {
                Intent intent = new Intent(MainActivity.this, ComponentListActivity.class);
                intent.putExtra("EXTRA_CATEGORY", topic.getId());
                startActivity(intent);
            }
        });

        rvMainTopics.setAdapter(adapter);
    }
}