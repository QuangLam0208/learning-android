package com.persy.learnandroid.api;

import androidx.lifecycle.LiveData;

import com.persy.learnandroid.database.TodoDAO;
import com.persy.learnandroid.model.Todo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoRepository {

    public interface ActionCallback {
        void onSuccess();
        void onError(String message);
    }

    private final TodoDAO todoDAO;
    private final TodoApiService todoApiService;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public TodoRepository(TodoDAO todoDAO, TodoApiService todoApiService) {
        this.todoDAO = todoDAO;
        this.todoApiService = todoApiService;
    }

    public LiveData<List<Todo>> getAllTodoLive() {
        return todoDAO.getAllTodoLive();
    }

    public void fetchTodosFromNetwork(ActionCallback callback) {
        todoApiService.getAllTodos().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Todo> serverTodos = response.body();
                    executor.execute(() -> {
                        todoDAO.syncServerData(serverTodos);
                        if (callback != null) callback.onSuccess();
                    });
                } else {
                    if (callback != null) callback.onError("Không thể lấy dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                if (callback != null) callback.onError("Không thể lấy dữ liệu");
            }
        });
    }

    public void insert(Todo todo, ActionCallback callback) {
        todoApiService.createTodo(todo).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Todo createdTodo = response.body();
                    executor.execute(() -> {
                        todoDAO.insert(createdTodo);
                        if (callback != null) callback.onSuccess();
                    });
                } else {
                    if (callback != null) callback.onError("Lỗi máy chủ khi thêm");
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                if (callback != null) callback.onError("Lỗi máy chủ");
            }
        });
    }

    public void update(Todo todo, ActionCallback callback) {
        todoApiService.updateTodo(todo.getId(), todo).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Todo updatedTodo = response.body();
                    executor.execute(() -> {
                        todoDAO.update(updatedTodo);
                        if (callback != null) callback.onSuccess();
                    });
                } else {
                    if (callback != null) callback.onError("Lỗi máy chủ khi cập nhật");
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                if (callback != null) callback.onError("Lỗi máy chủ");
            }
        });
    }

    public void delete(Todo todo, ActionCallback callback) {
        todoApiService.deleteTodo(todo.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> {
                        todoDAO.delete(todo);
                        if (callback != null) callback.onSuccess();
                    });
                } else {
                    if (callback != null) callback.onError("Lỗi máy chủ khi xóa");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (callback != null) callback.onError("Lỗi máy chủ");
            }
        });
    }
}