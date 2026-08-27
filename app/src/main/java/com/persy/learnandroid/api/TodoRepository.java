package com.persy.learnandroid.database;

import androidx.lifecycle.LiveData;

import com.persy.learnandroid.api.TodoApiService;
import com.persy.learnandroid.model.Todo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoRepository {

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

    public void fetchTodosFromNetwork() {
        todoApiService.getAllTodos().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Todo> serverTodos = response.body();
                    executor.execute(() -> {
                        for (Todo todo : serverTodos) {
                            todo.setSyncStatus(1);
                        }
                        todoDAO.insertAll(serverTodos);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
            }
        });
    }

    public void insert(Todo todo) {
        executor.execute(() -> {
            todo.setSyncStatus(0);
            long localId = todoDAO.insert(todo);
            todo.setId((int) localId);

            todoApiService.createTodo(todo).enqueue(new Callback<Todo>() {
                @Override
                public void onResponse(Call<Todo> call, Response<Todo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Todo serverTodo = response.body();
                        executor.execute(() -> {
                            serverTodo.setSyncStatus(1);
                            todoDAO.insert(serverTodo);
                        });
                    }
                }

                @Override
                public void onFailure(Call<Todo> call, Throwable t) {
                }
            });
        });
    }

    public void update(Todo todo) {
        executor.execute(() -> {
            todo.setSyncStatus(2);
            todoDAO.update(todo);

            todoApiService.updateTodo(todo.getId(), todo).enqueue(new Callback<Todo>() {
                @Override
                public void onResponse(Call<Todo> call, Response<Todo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        executor.execute(() -> {
                            todo.setSyncStatus(1);
                            todoDAO.update(todo);
                        });
                    }
                }

                @Override
                public void onFailure(Call<Todo> call, Throwable t) {
                }
            });
        });
    }

    public void delete(Todo todo) {
        executor.execute(() -> {
            todo.setSyncStatus(3);
            todoDAO.update(todo);

            todoApiService.deleteTodo(todo.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        executor.execute(() -> {
                            todoDAO.delete(todo);
                        });
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        });
    }

    public void syncPendingData() {
        executor.execute(() -> {
            List<Todo> pendingTodos = todoDAO.getUnsyncedTodos();
            for (Todo todo : pendingTodos) {
                if (todo.getSyncStatus() == 0) {
                    insert(todo);
                } else if (todo.getSyncStatus() == 2) {
                    update(todo);
                } else if (todo.getSyncStatus() == 3) {
                    delete(todo);
                }
            }
        });
    }
}