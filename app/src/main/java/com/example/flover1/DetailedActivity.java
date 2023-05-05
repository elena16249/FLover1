package com.example.flover1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DetailedActivity extends AppCompatActivity {


    TextView description, flowerName, accessories, condition1, condition2, condition3, condition4;
    ImageView flowerImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        description = findViewById(R.id.detailDesc);
        flowerImage = findViewById(R.id.detailImage);
        flowerName = findViewById(R.id.detailTitle);
        accessories = findViewById(R.id.detailLang);
        condition1 = findViewById(R.id.condition1);
        condition2 = findViewById(R.id.condition2);
        condition3 = findViewById(R.id.condition3);
        condition4 = findViewById(R.id.condition4);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            description.setText(bundle.getString("Description"));
            flowerName.setText(bundle.getString("FlowerName"));
            accessories.setText(bundle.getString("Accessories"));
            condition1.setText(bundle.getString("Condition1"));
            condition2.setText(bundle.getString("Condition2"));
            condition3.setText(bundle.getString("Condition3"));
            condition4.setText(bundle.getString("Condition4"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");

            Glide.with(this).load(bundle.getString("Image")).into(flowerImage);
        }

        MaterialCardView materialCardView1 = findViewById(R.id.materialcardview1);
        MaterialCardView materialCardView2 = findViewById(R.id.materialcardview2);
        MaterialCardView materialCardView3 = findViewById(R.id.materialcardview3);
        MaterialCardView materialCardView4 = findViewById(R.id.materialcardview4);


        materialCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Flowers");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot flowerSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot itemSnapshot : flowerSnapshot.getChildren()) {
                                String dialog11 = itemSnapshot.child("dialog1").getValue(String.class);
                                String step = itemSnapshot.child("step1").getValue(String.class);
                                Dialog dialog = new Dialog(DetailedActivity.this);
                                dialog.setContentView(R.layout.dialogue_layout);
                                TextView dialog1 = dialog.findViewById(R.id.dialog);
                                TextView step1 = dialog.findViewById(R.id.steps);
                                dialog1.setText(dialog11);
                                step1.setText(step);
                                dialog.setCancelable(true);
                                dialog.show();

                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Log.e("TAG", "Failed to retrieve value.", databaseError.toException());
                    }
                });
            }
        });

        materialCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Flowers");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot flowerSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot itemSnapshot : flowerSnapshot.getChildren()) {
                                String dialog11 = itemSnapshot.child("dialog2").getValue(String.class);
                                String step = itemSnapshot.child("step2").getValue(String.class);
                                Dialog dialog = new Dialog(DetailedActivity.this);
                                dialog.setContentView(R.layout.dialogue_layout);
                                TextView dialog1 = dialog.findViewById(R.id.dialog);
                                TextView step1 = dialog.findViewById(R.id.steps);
                                dialog1.setText(dialog11);
                                step1.setText(step);
                                dialog.setCancelable(true);
                                dialog.show();

                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that may occur
                        Log.e("TAG", "Failed to retrieve value.", databaseError.toException());
                    }
                });
            }
        });
        materialCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Flowers");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot flowerSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot itemSnapshot : flowerSnapshot.getChildren()) {
                                String dialog11 = itemSnapshot.child("dialog3").getValue(String.class);
                                String step = itemSnapshot.child("step3").getValue(String.class);
                                Dialog dialog = new Dialog(DetailedActivity.this);
                                dialog.setContentView(R.layout.dialogue_layout);
                                TextView dialog1 = dialog.findViewById(R.id.dialog);
                                TextView step1 = dialog.findViewById(R.id.steps);
                                dialog1.setText(dialog11);
                                step1.setText(step);
                                dialog.setCancelable(true);
                                dialog.show();

                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that may occur
                        Log.e("TAG", "Failed to retrieve value.", databaseError.toException());
                    }
                });
            }
        });

        materialCardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Flowers");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot flowerSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot itemSnapshot : flowerSnapshot.getChildren()) {
                                String dialog11 = itemSnapshot.child("dialog4").getValue(String.class);
                                String step = itemSnapshot.child("step4").getValue(String.class);
                                Dialog dialog = new Dialog(DetailedActivity.this);
                                dialog.setContentView(R.layout.dialogue_layout);
                                TextView dialog1 = dialog.findViewById(R.id.dialog);
                                TextView step1 = dialog.findViewById(R.id.steps);
                                dialog1.setText(dialog11);
                                step1.setText(step);
                                dialog.setCancelable(true);
                                dialog.show();

                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that may occur
                        Log.e("TAG", "Failed to retrieve value.", databaseError.toException());
                    }
                });
            }
        });

    }
}