package com.example.flover1;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DetailedActivity extends AppCompatActivity {

    TextView description, flowerName, accessories, condition1, condition2, condition3, condition4;
    ImageView flowerImage;
    ImageView favButton1, editButton;
    Flower flower;
    DatabaseReference currentUserRef;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        description = findViewById(R.id.detailDesc);
        flowerImage = findViewById(R.id.detailImage);
        flowerName = findViewById(R.id.detailTitle);

        condition1 = findViewById(R.id.condition1);
        condition2 = findViewById(R.id.condition2);
        condition3 = findViewById(R.id.condition3);
        condition4 = findViewById(R.id.condition4);




        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            flower = bundle.getParcelable("flower");
            if (flower != null) {
                description.setText(flower.getDescription());
                flowerName.setText(flower.getName());
                condition1.setText(flower.getCondition1());
                condition2.setText(flower.getCondition2());
                condition3.setText(flower.getCondition3());
                condition4.setText(flower.getCondition4());
                Glide.with(this).load(flower.getImage()).into(flowerImage);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String currentUserId = firebaseAuth.getCurrentUser().getUid();
                currentUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);






            }
        }
        MaterialCardView materialCardView1 = findViewById(R.id.materialcardview1);
        MaterialCardView materialCardView2 = findViewById(R.id.materialcardview2);
        MaterialCardView materialCardView3 = findViewById(R.id.materialcardview3);
        MaterialCardView materialCardView4 = findViewById(R.id.materialcardview4);


        materialCardView1.setOnClickListener(v -> {

            Dialog dialog = new Dialog(DetailedActivity.this);
            dialog.setContentView(R.layout.dialogue_layout);
            TextView dialog1 = dialog.findViewById(R.id.dialog);
            TextView step1 = dialog.findViewById(R.id.steps);
            dialog1.setText(flower.getDialog1());
            step1.setText(flower.getStep1());
            dialog.setCancelable(true);
            dialog.show();
        });

        materialCardView2.setOnClickListener(v -> {

            Dialog dialog = new Dialog(DetailedActivity.this);
            dialog.setContentView(R.layout.dialogue_layout);
            TextView dialog1 = dialog.findViewById(R.id.dialog);
            TextView step1 = dialog.findViewById(R.id.steps);
            dialog1.setText(flower.getDialog2());
            step1.setText(flower.getStep2());
            dialog.setCancelable(true);
            dialog.show();
        });

        materialCardView3.setOnClickListener(v -> {

            Dialog dialog = new Dialog(DetailedActivity.this);
            dialog.setContentView(R.layout.dialogue_layout);
            TextView dialog1 = dialog.findViewById(R.id.dialog);
            TextView step1 = dialog.findViewById(R.id.steps);
            dialog1.setText(flower.getDialog3());
            step1.setText(flower.getStep3());
            dialog.setCancelable(true);
            dialog.show();
        });

        materialCardView4.setOnClickListener(v -> {

            Dialog dialog = new Dialog(DetailedActivity.this);
            dialog.setContentView(R.layout.dialogue_layout);
            TextView dialog1 = dialog.findViewById(R.id.dialog);
            TextView step1 = dialog.findViewById(R.id.steps);
            dialog1.setText(flower.getDialog4());
            step1.setText(flower.getStep4());
            dialog.setCancelable(true);
            dialog.show();
        });
    }


}
