package com.persy.learnandroid.model;

import androidx.room3.Entity;
import androidx.room3.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todo")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private  String title;
    private Date createAt;

    public Todo(String title, Date createAt) {
        this.title = title;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
