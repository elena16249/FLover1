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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.MyViewHolder> {
    private List<FlowerSaved> dataList;
    private Context context;

    Intent intent;
    public SavedAdapter(Context context, List<FlowerSaved> dataList) {
        this.context = context;
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
        Glide.with(context).load(dataList.get(position).getImage()).into(holder.flowerImage);
        holder.flowerNameTextView.setText(dataList.get(position).getName()); // Set the title or flower name

        final int clickedPosition = position; // Create a final variable with the position

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    deleteItemFromDatabase(clickedPosition);
                }
            }
        });
        holder.saveRecCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SavedDetailActivity.class);
                FlowerSaved selectedFlower = dataList.get(clickedPosition); // Use FlowerSaved object
                intent.putExtra("flowerSaved", selectedFlower);
                context.startActivity(intent);
            }
        });
    }
    private void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }
    private void deleteItemFromDatabase(int position) {
        FlowerSaved flower = dataList.get(position);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(currentUserId)
                .child("favoriteFlowers");

        Query query = currentUserRef.orderByChild("flowerId").equalTo(flower.getFlowerId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot flowerSnapshot : dataSnapshot.getChildren()) {
                    flowerSnapshot.getRef().removeValue();
                    break; // Stop after deleting the first matching item
                }

                removeItem(position); // Remove the item from the RecyclerView adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the database operation
            }
        });
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
        RelativeLayout saveRecCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            flowerNameTextView = itemView.findViewById(R.id.saveFlowerNameTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            flowerImage = itemView.findViewById(R.id.saveImage);
            saveRecCard = itemView.findViewById(R.id.saveRecCard);
        }

    }


}