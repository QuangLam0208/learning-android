package com.persy.learnandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
import com.persy.learnandroid.demo.controls.ButtonDemoActivity;
import com.persy.learnandroid.demo.controls.EditTextDemoActivity;
import com.persy.learnandroid.demo.controls.ProgressBarDemoActivity;
import com.persy.learnandroid.demo.controls.SeekbarDemoActivity;
import com.persy.learnandroid.demo.controls.SpinnerDemoActivity;
import com.persy.learnandroid.demo.controls.TextViewDemoActivity;
import com.persy.learnandroid.demo.intent.BundleDemoActivity;
import com.persy.learnandroid.demo.intent.ObjectTransferDemoActivity;
import com.persy.learnandroid.demo.intent.PutExtraDemoActivity;
import com.persy.learnandroid.demo.intent.ReturnDataDemoActivity;
import com.persy.learnandroid.demo.layouts.ConstraintLayoutDemoActivity;
import com.persy.learnandroid.demo.layouts.FrameLayoutDemoActivity;
import com.persy.learnandroid.demo.layouts.GridLayoutDemoActivity;
import com.persy.learnandroid.demo.layouts.LinearLayoutDemoActivity;
import com.persy.learnandroid.demo.layouts.RelativeLayoutDemoActivity;
import com.persy.learnandroid.demo.layouts.TableLayoutDemoActivity;
import com.persy.learnandroid.demo.retrofit.RetrofitApiInterfaceDemoActivity;
import com.persy.learnandroid.demo.retrofit.RetrofitAuthDemoActivity;
import com.persy.learnandroid.demo.retrofit.RetrofitClientDemoActivity;
import com.persy.learnandroid.demo.retrofit.RetrofitOverviewDemoActivity;
import com.persy.learnandroid.demo.retrofit.RetrofitPojoDemoActivity;
import com.persy.learnandroid.demo.retrofit.login.LoginActivity;
import com.persy.learnandroid.demo.roomdb.OverviewDemoActivity;
import com.persy.learnandroid.demo.roomdb.RoomDaoDemoActivity;
import com.persy.learnandroid.demo.roomdb.RoomEntityDemoActivity;
import com.persy.learnandroid.demo.ui.TodoDemoActivity;
import com.persy.learnandroid.model.ETopicCategory;
import com.persy.learnandroid.model.Topic;

import java.util.List;

public class ComponentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_component_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String topicCateKey = getIntent().getStringExtra("EXTRA_CATEGORY");
        if (topicCateKey == null) {
            finish();
            return;
        }

        ETopicCategory cate = ETopicCategory.valueOf(topicCateKey);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(cate.getValue());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView rvComponentTopics = findViewById(R.id.rvComponentTopics);
        rvComponentTopics.setLayoutManager(new LinearLayoutManager(this));

        List<Topic> subTopics = TopicRepository.getTopicsForCategory(cate);

        TopicAdapter adapter = new TopicAdapter(subTopics, topic -> {
            Class<?> targetActivityClass = getActivityClass(topic.getTargetActivityKey());

            if (targetActivityClass != null) {
                Intent intent = new Intent(ComponentListActivity.this, targetActivityClass);
                intent.putExtra("EXTRA_TOPIC_TITLE", topic.getTitle());
                startActivity(intent);
            } else {
                Toast.makeText(ComponentListActivity.this,
                        "Tính năng " + topic.getTitle() + " đang được phát triển!", Toast.LENGTH_SHORT).show();
            }
        });

        rvComponentTopics.setAdapter(adapter);
    }

    private Class<?> getActivityClass(String key) {
        if (key == null) return null;

        switch (key) {
            case "KEY_LINEAR_LAYOUT":
                return LinearLayoutDemoActivity.class;
            case "KEY_CONSTRAINT_LAYOUT":
                return ConstraintLayoutDemoActivity.class;
            case "KEY_RELATIVE_LAYOUT":
                return RelativeLayoutDemoActivity.class;
            case "KEY_FRAME_LAYOUT":
                return FrameLayoutDemoActivity.class;
            case "KEY_TABLE_LAYOUT":
                return TableLayoutDemoActivity.class;
            case "KEY_GRID_LAYOUT":
                return GridLayoutDemoActivity.class;

            case "KEY_BUTTON":
                return ButtonDemoActivity.class;
            case "KEY_EDIT_TEXT":
                return EditTextDemoActivity.class;
            case "KEY_TEXT_VIEW":
                return TextViewDemoActivity.class;
            case "KEY_SPINNER":
                return SpinnerDemoActivity.class;
            case "KEY_SEEK_BAR":
                return SeekbarDemoActivity.class;
            case "KEY_PROGRESS_BAR":
                return ProgressBarDemoActivity.class;

            case "KEY_SEND_EXTRA":
                return PutExtraDemoActivity.class;
            case "KEY_SEND_BUNDLE":
                return BundleDemoActivity.class;
            case "KEY_SEND_OBJECT":
                return ObjectTransferDemoActivity.class;
            case "KEY_RETURN_DATA":
                return ReturnDataDemoActivity.class;

            case "KEY_ROOM_OVERVIEW":
                return OverviewDemoActivity.class;
            case "KEY_ROOM_ENTITY":
                return RoomEntityDemoActivity.class;
            case "KEY_ROOM_DAO":
                return RoomDaoDemoActivity.class;
            case "KEY_ROOM_DEMO":
                return TodoDemoActivity.class;

            case "KEY_RETROFIT_OVERVIEW":
                return RetrofitOverviewDemoActivity.class;
            case "KEY_RETROFIT_POJO":
                return RetrofitPojoDemoActivity.class;
            case "KEY_RETROFIT_API_INTERFACE":
                return RetrofitApiInterfaceDemoActivity.class;
            case "KEY_RETROFIT_CLIENT":
                return RetrofitClientDemoActivity.class;
            case "KEY_RETROFIT_AUTH":
                return RetrofitAuthDemoActivity.class;
            case "KEY_RETROFIT_LOGIN":
                return LoginActivity.class;

            default:
                return null;
        }
    }
}