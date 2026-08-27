package com.persy.learnandroid.demo.ui;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.persy.learnandroid.R;
import com.persy.learnandroid.adapter.TodoAdapter;
import com.persy.learnandroid.api.TodoRetrofitClient;
import com.persy.learnandroid.api.TodoApiService;
import com.persy.learnandroid.database.TodoDAO;
import com.persy.learnandroid.database.TodoDatabase;
import com.persy.learnandroid.api.TodoRepository;
import com.persy.learnandroid.model.Todo;
import com.persy.learnandroid.utils.NetworkUtils;

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
    private SwipeRefreshLayout swipeRefreshLayout;


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
        TodoApiService  todoApiService = TodoRetrofitClient.getTodoApiService();
        todoRepository = new TodoRepository(todoDAO, todoApiService);

        viewMapping();
        setupToolBar();
        setupRecyclerView();
        setupAddButton();
        setupSwipeRefresh();
        observeTodoList();

        refreshData();
    }

    private void viewMapping() {
        toolbar = findViewById(R.id.toolbar);
        edtTodoTitle = findViewById(R.id.edtTodoTitle);
        btnAddTodo = findViewById(R.id.btnAddTodo);
        rvTodoList = findViewById(R.id.rvTodoList);
        tvEmpty = findViewById(R.id.tvEmpty);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        );

        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
    }

    private void refreshData() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        swipeRefreshLayout.setRefreshing(true);

        todoRepository.fetchTodosFromNetwork(new TodoRepository.ActionCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    swipeRefreshLayout.setRefreshing(false);
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(TodoDemoActivity.this, message, Toast.LENGTH_SHORT).show();
                });
            }
        });
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
                if (!NetworkUtils.isNetworkAvailable(TodoDemoActivity.this)) {
                    Toast.makeText(TodoDemoActivity.this, "Bạn đang ngoại tuyến, không thể sửa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showEditDialog(todo);
            }
            @Override
            public void onDeleteClick(Todo todo) {
                if (!NetworkUtils.isNetworkAvailable(TodoDemoActivity.this)) {
                    Toast.makeText(TodoDemoActivity.this, "Bạn đang ngoại tuyến, không thể xóa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                confirmDelete(todo);
            }
        });

        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        rvTodoList.setAdapter(todoAdapter);
    }

    private void setupAddButton() {
        btnAddTodo.setOnClickListener(view -> {
            if (!NetworkUtils.isNetworkAvailable(this)) {
                Toast.makeText(this, "Bạn đang ngoại tuyến, không thể thêm mới!", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = edtTodoTitle.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show();
                return;
            }
            todoRepository.insert(new Todo(title, new Date()), new TodoRepository.ActionCallback() {
                @Override
                public void onSuccess() {
                    runOnUiThread(() -> {
                        edtTodoTitle.setText("");
                        Toast.makeText(TodoDemoActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    });
                }
                @Override
                public void onError(String message) {
                    runOnUiThread(() -> Toast.makeText(TodoDemoActivity.this, message, Toast.LENGTH_SHORT).show());
                }
            });
            edtTodoTitle.setText("");
        });
    }

    private void observeTodoList() {
        todoRepository.getAllTodoLive().observe(this, updatedTodos -> {
            todoList.clear();
            todoList.addAll(updatedTodos);
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
                    todoRepository.update(todo, new TodoRepository.ActionCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> Toast.makeText(TodoDemoActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show());
                        }
                        @Override
                        public void onError(String message) {
                            runOnUiThread(() -> Toast.makeText(TodoDemoActivity.this, message, Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void confirmDelete(Todo todo) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa việc cần làm")
                .setMessage("Bạn có chắc muốn xóa \"" + todo.getTitle() + "\"?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    todoRepository.delete(todo, new TodoRepository.ActionCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> Toast.makeText(TodoDemoActivity.this, "Đã xóa!", Toast.LENGTH_SHORT).show());
                        }
                        @Override
                        public void onError(String message) {
                            runOnUiThread(() -> Toast.makeText(TodoDemoActivity.this, message, Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }


}