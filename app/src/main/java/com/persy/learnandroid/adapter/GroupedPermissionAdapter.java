package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.R;
import com.persy.learnandroid.model.Permission;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GroupedPermissionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    public static class ListItem {
        public final int type;
        public final String groupName;
        public final int groupCount;
        public final Permission permission;

        public ListItem(String groupName, int groupCount) {
            this.type = TYPE_HEADER;
            this.groupName = groupName;
            this.groupCount = groupCount;
            this.permission = null;
        }

        public ListItem(Permission permission) {
            this.type = TYPE_ITEM;
            this.groupName = null;
            this.groupCount = 0;
            this.permission = permission;
        }
    }

    private final List<ListItem> displayList = new ArrayList<>();

    public void setPermissions(List<Permission> permissions) {
        displayList.clear();
        if (permissions != null && !permissions.isEmpty()) {
            // Group by nameGroup
            Map<String, List<Permission>> grouped = new LinkedHashMap<>();
            for (Permission p : permissions) {
                String group = (p.getNameGroup() != null && !p.getNameGroup().trim().isEmpty())
                        ? p.getNameGroup().trim() : "Khác";
                if (!grouped.containsKey(group)) {
                    grouped.put(group, new ArrayList<>());
                }
                grouped.get(group).add(p);
            }

            // Flatten into displayList
            for (Map.Entry<String, List<Permission>> entry : grouped.entrySet()) {
                String groupName = entry.getKey();
                List<Permission> list = entry.getValue();
                displayList.add(new ListItem(groupName, list.size()));
                for (Permission item : list) {
                    displayList.add(new ListItem(item));
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return displayList.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.item_permission_group_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_permission_row, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItem item = displayList.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(item);
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(item.permission);
        }
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvGroupName;
        TextView tvGroupCount;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvGroupCount = itemView.findViewById(R.id.tvGroupCount);
        }

        public void bind(ListItem item) {
            tvGroupName.setText("Nhóm: " + item.groupName);
            tvGroupCount.setText(item.groupCount + " quyền");
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvPermissionName;
        TextView tvPcode;
        TextView tvAction;
        TextView tvShowMenu;
        TextView tvDescription;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPermissionName = itemView.findViewById(R.id.tvPermissionName);
            tvPcode = itemView.findViewById(R.id.tvPcode);
            tvAction = itemView.findViewById(R.id.tvAction);
            tvShowMenu = itemView.findViewById(R.id.tvShowMenu);
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

            tvAction.setText(permission.getAction() != null ? permission.getAction() : "-");

            if (permission.isShowMenu()) {
                tvShowMenu.setText("👁️ Menu: Có");
                tvShowMenu.setBackgroundResource(R.drawable.bg_badge_menu_yes);
                tvShowMenu.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.badge_menu_yes_text));
            } else {
                tvShowMenu.setText("🚫 Menu: Không");
                tvShowMenu.setBackgroundResource(R.drawable.bg_badge_menu_no);
                tvShowMenu.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.badge_menu_no_text));
            }

            if (permission.getDescription() != null && !permission.getDescription().trim().isEmpty()) {
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText("📝 " + permission.getDescription().trim());
            } else {
                tvDescription.setVisibility(View.GONE);
            }
        }
    }
}
