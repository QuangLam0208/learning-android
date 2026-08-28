package com.persy.learnandroid.demo.roomdb;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.R;
import com.persy.learnandroid.adapter.TodoAdapter;
import com.persy.learnandroid.database.TodoDAO;
import com.persy.learnandroid.database.TodoDatabase;
import com.persy.learnandroid.database.TodoRepository;
import com.persy.learnandroid.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoDemoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edtTodoTitle;
    private AppCompatButton btnAddTodo;
    private RecyclerView rvTodoList;
    private TextView tvEmpty;

    private TodoRepository todoRepository;
    private TodoAdapter todoAdapter;
    private List<Todo> todoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todo_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TodoDAO todoDAO = TodoDatabase.getInstance(this).todoDAO();
        todoRepository = new TodoRepository(todoDAO);

        viewMapping();
        setupToolBar();
        setupRecyclerView();
        setupAddButton();
        observeTodoList();
    }

    private void viewMapping() {
        toolbar = findViewById(R.id.toolbar);
        edtTodoTitle = findViewById(R.id.edtTodoTitle);
        btnAddTodo = findViewById(R.id.btnAddTodo);
        rvTodoList = findViewById(R.id.rvTodoList);
        tvEmpty = findViewById(R.id.tvEmpty);
    }
    private void setupToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            String topicTitle = getIntent().getStringExtra("EXTRA_TOPIC_TITLE");
            getSupportActionBar().setTitle(
                    topicTitle != null ? topicTitle : "Todo Demo"
            );
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());
    }
    private void setupRecyclerView() {
        todoAdapter = new TodoAdapter(todoList, new TodoAdapter.OnTodoActionListener() {
            @Override
            public void onEditClick(Todo todo) {
                showEditDialog(todo);
            }

            @Override
            public void onDeleteClick(Todo todo) {
                confirmDelete(todo);
            }
        });

        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        rvTodoList.setAdapter(todoAdapter);
    }

    private void setupAddButton() {
        btnAddTodo.setOnClickListener(view -> {
            String title = edtTodoTitle.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show();
                return;
            }

            todoRepository.insert(new Todo(title, new Date()));
            edtTodoTitle.setText("");
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        });
    }

    private void observeTodoList() {
        todoRepository.getAllTodoLive().observe(this, updatedTodos -> {
            todoList.clear();
            if (updatedTodos != null) {
                todoList.addAll(updatedTodos);
            }
            todoAdapter.updateData(todoList);

            boolean isEmpty = todoList.isEmpty();
            tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            rvTodoList.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
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