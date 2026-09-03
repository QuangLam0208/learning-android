package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.databinding.ItemButtonBinding;
import com.persy.learnandroid.model.ButtonItem;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> implements BindableAdapter<List<ButtonItem>>{

    private List<ButtonItem> lstBtnItem;

    @Override
    public void setData(List<ButtonItem> data) {
        this.lstBtnItem = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemButtonBinding binding = ItemButtonBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setItem(lstBtnItem.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return lstBtnItem != null ? lstBtnItem.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemButtonBinding binding;

        public ViewHolder(@NonNull ItemButtonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
