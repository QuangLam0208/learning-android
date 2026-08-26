package com.persy.learnandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room3.Dao;
import androidx.room3.Delete;
import androidx.room3.Insert;
import androidx.room3.OnConflictStrategy;
import androidx.room3.Query;
import androidx.room3.Update;

import com.persy.learnandroid.model.Todo;

import java.util.List;

@Dao
public interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo todo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Todo> todos);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM todo")
    void deleteAll();

    @Query("SELECT * FROM todo ORDER BY createAt DESC")
    List<Todo> getAllTodo();

    @Query("SELECT * FROM todo ORDER BY createAt DESC")
    LiveData<List<Todo>> getAllTodoLive();

    @Query("SELECT * FROM todo WHERE id = :id LIMIT 1")
    Todo getTodoById(int id);
}
