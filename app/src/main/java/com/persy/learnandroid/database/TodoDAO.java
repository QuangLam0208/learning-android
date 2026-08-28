package com.persy.learnandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room3.Dao;
import androidx.room3.DaoReturnTypeConverters;
import androidx.room3.Delete;
import androidx.room3.Insert;
import androidx.room3.OnConflictStrategy;
import androidx.room3.Query;
import androidx.room3.Transaction;
import androidx.room3.Update;
import androidx.room3.livedata.LiveDataDaoReturnTypeConverter;

import com.persy.learnandroid.model.Todo;

import java.util.List;

@Dao
@DaoReturnTypeConverters(LiveDataDaoReturnTypeConverter.class)
public interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM todo")
    void deleteAll();

    @Query("SELECT * FROM todo ORDER BY createAt DESC")
    LiveData<List<Todo>> getAllTodoLive();
}
