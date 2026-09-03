package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.databinding.ItemTodoBinding;
import com.persy.learnandroid.model.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> implements BindableAdapter<List<Todo>> {
    private List<Todo> todoList;
    private final OnTodoActionListener listener;

    @Override
    public void setData(List<Todo> data) {
        this.todoList = data;
        notifyDataSetChanged();
    }

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
        ItemTodoBinding binding = ItemTodoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.binding.setTodo(todo);
        holder.binding.setListener(listener);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return todoList != null ? todoList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemTodoBinding binding;

        public ViewHolder(@NonNull ItemTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
