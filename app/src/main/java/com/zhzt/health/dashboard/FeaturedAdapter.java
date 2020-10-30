package com.zhzt.health.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhzt.health.R;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeatureViewHolder> {

    ArrayList<FeaturedHelper> featuredLocations;

    public FeaturedAdapter(ArrayList<FeaturedHelper> featuredLocations) {
        this.featuredLocations = featuredLocations;
    }


    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);
        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        FeaturedHelper featuredHelper = featuredLocations.get(position);
        holder.titleTextView.setText(featuredHelper.title);
        holder.imageView.setImageResource(featuredHelper.image);
        holder.descTextView.setText(featuredHelper.description);
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }

    public static class FeatureViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView, descTextView;
        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.featured_img);
            titleTextView = itemView.findViewById(R.id.featured_title);
            descTextView = itemView.findViewById(R.id.featured_desc);
        }
    }
}
