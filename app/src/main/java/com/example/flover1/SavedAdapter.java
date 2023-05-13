package com.example.flover1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.MyViewHolder> {
    private List<FlowerSaved> dataList;
    private Context context;

    Intent intent;
    public SavedAdapter(List<FlowerSaved> dataList) {
        this.dataList = dataList;

    }

    // Create and initialize the view holder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_recycler_item, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FlowerSaved flowerSaved = dataList.get(position);
        String flowerName = flowerSaved.getSavedFlowername();
        holder.flowerNameTextView.setText(flowerName);
        String imageUrl = flowerSaved.getSavedImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.flowerImage);


        holder.deleteButton.setOnClickListener(v -> {
            ((SavedActivity) v.getContext()).deleteItem(holder.getAdapterPosition());
        });


    }
    private void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Define the view holder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView flowerNameTextView;
        ImageView deleteButton;
        ImageView flowerImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            flowerNameTextView = itemView.findViewById(R.id.saveFlowerNameTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            flowerImage = itemView.findViewById(R.id.saveImage);
        }
    }

}