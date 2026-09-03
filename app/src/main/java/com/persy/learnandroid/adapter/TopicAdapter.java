package com.persy.learnandroid.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persy.learnandroid.databinding.ItemTopicBinding;
import com.persy.learnandroid.model.Topic;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> implements BindableAdapter<List<Topic>>{
    private List<Topic> topicList;
    private final OnTopicClickListener listener;

    @Override
    public void setData(List<Topic> data) {
        this.topicList = data;
        notifyDataSetChanged();
    }

    public TopicAdapter(List<Topic> topicList, OnTopicClickListener listener) {
        this.topicList = topicList;
        this.listener = listener;
    }

    public interface OnTopicClickListener {
        void onTopicClick(Topic topic);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTopicBinding binding = ItemTopicBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic currentTopic = topicList.get(position);
        holder.binding.setTopic(currentTopic);
        holder.binding.setListener(listener);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return topicList != null ? topicList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemTopicBinding binding;

        public ViewHolder(@NonNull ItemTopicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
