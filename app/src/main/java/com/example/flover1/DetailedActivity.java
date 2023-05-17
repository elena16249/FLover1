package com.example.flover1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class DetailedActivity extends AppCompatActivity {

    TextView description, flowerName, accessories, condition1, condition2, condition3, condition4;
    ImageView flowerImage;
    ImageView favButton1, editButton;
    Flower favoriteFlower;
    DatabaseReference currentUserRef;
    private boolean isFavorite = false;

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
            favoriteFlower = bundle.getParcelable("flower");
            if (favoriteFlower != null) {
                description.setText(favoriteFlower.getDescription());
                flowerName.setText(favoriteFlower.getName());
                condition1.setText(favoriteFlower.getCondition1());
                condition2.setText(favoriteFlower.getCondition2());
                condition3.setText(favoriteFlower.getCondition3());
                condition4.setText(favoriteFlower.getCondition4());
                Glide.with(this).load(favoriteFlower.getImage()).into(flowerImage);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String currentUserId = firebaseAuth.getCurrentUser().getUid();
                currentUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                favButton1 = findViewById(R.id.favButton1);

                queryCurrentUserRef();

                favButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Query query = currentUserRef.child("favoriteFlowers").orderByChild("flowerId").equalTo(favoriteFlower.getFlowerId1());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // The flower is already a favorite, remove it from the favoriteFlowers node
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        snapshot.getRef().removeValue();
                                    }
                                    isFavorite = false;
                                    favButton1.setImageResource(R.drawable.baseline_favorite_border_24);
                                } else {
                                    // The flower is not a favorite, add it to the favoriteFlowers node
                                    currentUserRef.child("favoriteFlowers").push().setValue(favoriteFlower);
                                    isFavorite = true;
                                    favButton1.setImageResource(R.drawable.baseline_favorite1_24);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle the error
                            }
                        });

                    }
                });

            }
        }
    }



    private void queryCurrentUserRef() {
        if (favoriteFlower != null) {
            Query query = currentUserRef.child("favoriteFlowers").orderByChild("flowerId").equalTo(favoriteFlower.getFlowerId1());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    isFavorite = dataSnapshot.exists();
                    if (isFavorite) {
                        favButton1.setImageResource(R.drawable.baseline_favorite1_24);
                    } else {
                        favButton1.setImageResource(R.drawable.baseline_favorite_border_24);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
                }
            });
        }
    }
}
