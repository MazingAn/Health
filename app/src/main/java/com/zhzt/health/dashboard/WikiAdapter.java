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

public class WikiAdapter extends RecyclerView.Adapter<WikiAdapter.WikiViewHolder> {

    ArrayList<WikiHelper> wikiLocations;

    public WikiAdapter(ArrayList<WikiHelper> wikiLocations) {
        this.wikiLocations = wikiLocations;
    }


    @NonNull
    @Override
    public WikiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wiki_card_design, parent, false);
        return new WikiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WikiViewHolder holder, int position) {
        WikiHelper wikiHelper = wikiLocations.get(position);
        holder.titleTextView.setText(wikiHelper.title);
        holder.imageView.setImageResource(wikiHelper.image);
        holder.descTextView.setText(wikiHelper.description);
    }

    @Override
    public int getItemCount() {
        return wikiLocations.size();
    }

    public static class WikiViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView, descTextView;
        public WikiViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.wiki_img);
            titleTextView = itemView.findViewById(R.id.wiki_title);
            descTextView = itemView.findViewById(R.id.wiki_desc);
        }
    }
}
