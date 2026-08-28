package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.R;
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_permission_row, parent, false);
        return new PermissionViewHolder(view);
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
        TextView tvPermissionName;
        TextView tvPcode;
        TextView tvDescription;

        public PermissionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPermissionName = itemView.findViewById(R.id.tvPermissionName);
            tvPcode = itemView.findViewById(R.id.tvPcode);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Permission permission) {
            if (permission == null) return;

            tvPermissionName.setText(permission.getName() != null ? permission.getName() : "Không tên");

            if (permission.getPcode() != null && !permission.getPcode().trim().isEmpty()) {
                tvPcode.setVisibility(View.VISIBLE);
                tvPcode.setText(permission.getPcode());
            } else {
                tvPcode.setVisibility(View.GONE);
            }

            if (permission.getDescription() != null && !permission.getDescription().trim().isEmpty()) {
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(permission.getDescription().trim());
            } else {
                tvDescription.setVisibility(View.GONE);
            }
        }
    }
}
