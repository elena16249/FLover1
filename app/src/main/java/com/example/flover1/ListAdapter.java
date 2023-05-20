package com.example.flover1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Flower> dataList;

    public ListAdapter(Context context, List<Flower> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(dataList.get(position).getImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getName());


        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flower favoriteFlower = dataList.get(position);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String currentUserId = firebaseAuth.getCurrentUser().getUid();
                DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

                // Check if the flower already exists in the favoriteFlowers node
                Query query = currentUserRef.child("favoriteFlowers").orderByChild("name").equalTo(favoriteFlower.getName());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean isFavorite = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Flower flower = snapshot.getValue(Flower.class);
                            if (flower != null && flower.getName().equals(favoriteFlower.getName())) {
                                isFavorite = true;
                                snapshot.getRef().removeValue();
                                break;
                            }
                        }

                        if (isFavorite) {
                            holder.favButton.setImageResource(R.drawable.baseline_favorite_border_24);
                        } else {
                            DatabaseReference newFavoriteRef = currentUserRef.child("favoriteFlowers").push();
                            newFavoriteRef.setValue(favoriteFlower);
                            holder.favButton.setImageResource(R.drawable.baseline_favorite1_24);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });
            }
        });


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);


        currentUserRef.child("favoriteFlowers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isFavorite = false;
                String currentFlowerId = dataList.get(holder.getAdapterPosition()).getName();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Flower flower = snapshot.getValue(Flower.class);
                    if (flower != null && flower.getName() != null && currentFlowerId != null && flower.getName().equals(currentFlowerId)) {
                        isFavorite = true;
                        break;
                    }
                }

                if (isFavorite) {
                    holder.favButton.setImageResource(R.drawable.baseline_favorite1_24);
                } else {
                    holder.favButton.setImageResource(R.drawable.baseline_favorite_border_24);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
            }
        });


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("flower", dataList.get(position));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<Flower> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView recImage;
    TextView recTitle, recDesc, recLang, condition1, condition2, condition3, condition4, dialog1;
    CardView recCard;

    ImageButton favButton;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);

        recTitle = itemView.findViewById(R.id.recTitle);
        condition1 = itemView.findViewById(R.id.condition1);
        condition2 = itemView.findViewById(R.id.condition2);
        condition3 = itemView.findViewById(R.id.condition3);
        condition4 = itemView.findViewById(R.id.condition4);
        dialog1 = itemView.findViewById(R.id.dialog);

        favButton = itemView.findViewById(R.id.favButton);
    }

}