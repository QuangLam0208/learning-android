package com.persy.learnandroid.database;

import androidx.lifecycle.LiveData;

import com.persy.learnandroid.model.Todo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TodoRepository {

    private final TodoDAO todoDAO;
    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public TodoRepository(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    public LiveData<List<Todo>> getAllTodoLive() {
        return todoDAO.getAllTodoLive();
    }

    public void insert(Todo todo) {
        executor.execute(() -> {
            todoDAO.insert(todo);
        });
    }

    public void update(Todo todo) {
        executor.execute(() -> {
            todoDAO.update(todo);
        });
    }

    public void delete(Todo todo) {
        executor.execute(() -> {
            todoDAO.delete(todo);
        });
    }

    public void deleteAll() {
        executor.execute(() -> {
            todoDAO.deleteAll();
        });
    }
}