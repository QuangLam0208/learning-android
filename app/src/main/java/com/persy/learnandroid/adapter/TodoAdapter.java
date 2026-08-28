package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.R;
import com.persy.learnandroid.model.Todo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{
    private List<Todo> todoList;
    private final OnTodoActionListener listener;
    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public TodoAdapter(List<Todo> todoList, OnTodoActionListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    public interface OnTodoActionListener {
        void onEditClick(Todo todo);
        void onDeleteClick(Todo todo);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.tvTitle.setText(todo.getTitle());
        holder.tvDate.setText(
                todo.getCreateAt() != null ? dateFormat.format(todo.getCreateAt()) : ""
        );

        holder.btnEdit.setOnClickListener(view -> {
            if (listener != null) listener.onEditClick(todo);
        });

        holder.btnDelete.setOnClickListener(view -> {
            if (listener != null) listener.onDeleteClick(todo);
        });
    }

    @Override
    public int getItemCount() {
        return todoList != null ? todoList.size() : 0;
    }

    public void updateData(List<Todo> newTodos) {
        this.todoList = newTodos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;
        ImageButton btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTodoTitle);
            tvDate = itemView.findViewById(R.id.tvTodoDate);
            btnEdit = itemView.findViewById(R.id.btnEditTodo);
            btnDelete = itemView.findViewById(R.id.btnDeleteTodo);
        }
    }
}
