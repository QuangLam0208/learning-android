package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.R;
import com.persy.learnandroid.model.ButtonItem;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder>{

    private List<ButtonItem> lstBtnItem;

    public ButtonAdapter(List<ButtonItem> lstBtnItem) {
        this.lstBtnItem = lstBtnItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ButtonItem item = lstBtnItem.get(position);

        holder.tvName.setText(item.getName());
        holder.tvDesc.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return lstBtnItem != null ? lstBtnItem.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvButtonName);
            tvDesc = itemView.findViewById(R.id.tvButtonDesc);
        }
    }
}
