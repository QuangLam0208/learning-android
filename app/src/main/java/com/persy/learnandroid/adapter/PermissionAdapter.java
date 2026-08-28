package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.databinding.ItemPermissionRowBinding;
import com.persy.learnandroid.model.Permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.PermissionViewHolder> {

    private final List<Permission> permissionList = new ArrayList<>();

    public void setPermissions(List<Permission> permissions) {
        permissionList.clear();
        if (permissions != null) {
            permissionList.addAll(permissions);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PermissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPermissionRowBinding binding = ItemPermissionRowBinding.inflate(inflater, parent, false);
        return new PermissionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionViewHolder holder, int position) {
        Permission permission = permissionList.get(position);
        holder.bind(permission);
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

        public void bind(Permission permission) {
            binding.setPermission(permission);
            binding.executePendingBindings();
        }
    }
}
