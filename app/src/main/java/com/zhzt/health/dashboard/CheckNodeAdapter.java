package com.zhzt.health.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhzt.health.R;
import com.zhzt.health.utils.DateFormatter;

import java.util.List;

public class CheckNodeAdapter extends RecyclerView.Adapter<CheckNodeAdapter.CheckNodeViewHolder> {

    private List<CheckNodeHelper> checkNodesLocation;

    public CheckNodeAdapter(List<CheckNodeHelper> checkNodesLocation) {
        this.checkNodesLocation = checkNodesLocation;
    }

    @NonNull
    @Override
    public CheckNodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_self_card_design, parent, false);
        return new CheckNodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckNodeViewHolder holder, int position) {
        CheckNodeHelper node = this.checkNodesLocation.get(position);
        holder.titleTextView.setText(node.getTitle());
        holder.imageView.setImageResource(node.getImg());
    }

    @Override
    public int getItemCount() {
        return checkNodesLocation.size();
    }

    public static class CheckNodeViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView imageView;
        public CheckNodeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.check_node_title);
            imageView = itemView.findViewById(R.id.check_node_img);
        }
    }
}
