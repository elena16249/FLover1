package com.example.flover1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getName());
        holder.recDesc.setText(dataList.get(position).getAccessories());
//        holder.recLang.setText(dataList.get(position).getDescription());



        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SavedActivity.class);
                intent.putExtra("FlowerName1", dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("FlowerImage1", dataList.get(holder.getAdapterPosition()).getImage());
                context.startActivity(intent);
            }
        });

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("FlowerName", dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Accessories", dataList.get(holder.getAdapterPosition()).getAccessories());
                intent.putExtra("Condition1", dataList.get(holder.getAdapterPosition()).getCondition1());
                intent.putExtra("Condition2", dataList.get(holder.getAdapterPosition()).getCondition2());
                intent.putExtra("Condition3", dataList.get(holder.getAdapterPosition()).getCondition3());
                intent.putExtra("Condition4", dataList.get(holder.getAdapterPosition()).getCondition4());
                intent.putExtra("Dialog1", dataList.get(holder.getAdapterPosition()).getDialog1());
                intent.putExtra("Key", holder.getAdapterPosition());
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
        recDesc = itemView.findViewById(R.id.recDesc);

        recTitle = itemView.findViewById(R.id.recTitle);
        condition1 = itemView.findViewById(R.id.condition1);
        condition2 = itemView.findViewById(R.id.condition2);
        condition3 = itemView.findViewById(R.id.condition3);
        condition4 = itemView.findViewById(R.id.condition4);
        dialog1 = itemView.findViewById(R.id.dialog);

        favButton = itemView.findViewById(R.id.favButton);
    }

}