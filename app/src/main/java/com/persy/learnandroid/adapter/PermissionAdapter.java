package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.databinding.ItemPermissionRowBinding;
import com.persy.learnandroid.model.Permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.PermissionViewHolder> implements BindableAdapter<List<Permission>>{

    private final List<Permission> permissionList = new ArrayList<>();

    @Override
    public void setData(List<Permission> data) {
        permissionList.clear();
        if (data != null) {
            permissionList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PermissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPermissionRowBinding binding = ItemPermissionRowBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new PermissionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionViewHolder holder, int position) {
        Permission permission = permissionList.get(position);
        holder.binding.setPermission(permission);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return permissionList.size();
    }

    static class PermissionViewHolder extends RecyclerView.ViewHolder {
        private final ItemPermissionRowBinding binding;

        public PermissionViewHolder(ItemPermissionRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
