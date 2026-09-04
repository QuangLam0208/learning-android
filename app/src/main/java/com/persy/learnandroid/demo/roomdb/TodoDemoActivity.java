package com.persy.learnandroid.demo.roomdb;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.persy.learnandroid.MyApplication;
import com.persy.learnandroid.R;
import com.persy.learnandroid.adapter.TodoAdapter;
import com.persy.learnandroid.database.TodoRepository;
import com.persy.learnandroid.databinding.ActivityTodoDemoBinding;
import com.persy.learnandroid.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class TodoDemoActivity extends AppCompatActivity {

    private ActivityTodoDemoBinding binding;

    @Inject
    TodoRepository todoRepository;

    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplication()).getAppComponent().todoComponentFactory().create().inject(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_demo);
        binding.setLifecycleOwner(this);
        binding.setActivity(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolBar();
        setupRecyclerView();
        observeTodoList();

        System.out.println("[TodoDemoActivity] TodoRepository: " + todoRepository);
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(topicTitle != null ? topicTitle : "Todo Demo");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void setupRecyclerView() {
        todoAdapter = new TodoAdapter(new ArrayList<>(), new TodoAdapter.OnTodoActionListener() {
            @Override
            public void onEditClick(Todo todo) {
                showEditDialog(todo);
            }

            @Override
            public void onDeleteClick(Todo todo) {
                confirmDelete(todo);
            }
        });

        binding.rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTodoList.setAdapter(todoAdapter);
    }

    public void onAddTodo() {
        String title = binding.edtTodoTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show();
            return;
        }

        todoRepository.insert(new Todo(title, new Date()));
        binding.edtTodoTitle.setText("");
        Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
    }

    private void observeTodoList() {
        todoRepository.getAllTodoLive().observe(this, updatedTodos -> {
            binding.setTodoList(updatedTodos);
        });
    }

    private void showEditDialog(Todo todo) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_todo, null);
        EditText edtEdit = dialogView.findViewById(R.id.edtDialogTodoTitle);
        edtEdit.setText(todo.getTitle());
        edtEdit.setSelection(edtEdit.getText().length());

        new AlertDialog.Builder(this)
                .setTitle("Sửa việc cần làm")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String newTitle = edtEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(newTitle)) {
                        Toast.makeText(this, "Nội dung không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    todo.setTitle(newTitle);
                    todoRepository.update(todo);
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void confirmDelete(Todo todo) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa việc cần làm")
                .setMessage("Bạn có chắc muốn xóa \"" + todo.getTitle() + "\"?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    todoRepository.delete(todo);
                    Toast.makeText(this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}