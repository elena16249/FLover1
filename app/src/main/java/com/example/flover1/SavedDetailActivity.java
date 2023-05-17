package com.example.flover1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
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

import java.net.CookieHandler;

public class SavedDetailActivity extends AppCompatActivity {
    TextView description, flowerName, accessories, condition1, condition2, condition3, condition4;
    ImageView flowerImage;
    ImageButton deleteButton, editButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_detailed);

        description = findViewById(R.id.detailDescSaved);
        flowerImage = findViewById(R.id.detailImageSaved);
        flowerName = findViewById(R.id.detailTitleSaved);
        accessories = findViewById(R.id.detailLangSaved);
        condition1 = findViewById(R.id.condition1Saved);
        condition2 = findViewById(R.id.condition2Saved);
        condition3 = findViewById(R.id.condition3Saved);
        condition4 = findViewById(R.id.condition4Saved);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Flower flower = bundle.getParcelable("flower");
            description.setText(flower.getDescription());
            flowerName.setText(flower.getName());
            condition1.setText(flower.getCondition1());
            condition2.setText(flower.getCondition2());
            condition3.setText(flower.getCondition3());
            condition4.setText(flower.getCondition4());

            Glide.with(this).load(flower.getImage()).into(flowerImage);


            MaterialCardView materialCardView1 = findViewById(R.id.materialcardview1);
            MaterialCardView materialCardView2 = findViewById(R.id.materialcardview2);
            MaterialCardView materialCardView3 = findViewById(R.id.materialcardview3);
            MaterialCardView materialCardView4 = findViewById(R.id.materialcardview4);


            materialCardView1.setOnClickListener(v -> {

                Dialog dialog = new Dialog(SavedDetailActivity.this);
                dialog.setContentView(R.layout.dialogue_layout);
                TextView dialog1 = dialog.findViewById(R.id.dialog);
                TextView step1 = dialog.findViewById(R.id.steps);
                dialog1.setText(flower.getDialog1());
                step1.setText(flower.getStep1());
                dialog.setCancelable(true);
                dialog.show();
            });

            materialCardView2.setOnClickListener(v -> {

                Dialog dialog = new Dialog(SavedDetailActivity.this);
                dialog.setContentView(R.layout.dialogue_layout);
                TextView dialog1 = dialog.findViewById(R.id.dialog);
                TextView step1 = dialog.findViewById(R.id.steps);
                dialog1.setText(flower.getDialog2());
                step1.setText(flower.getStep2());
                dialog.setCancelable(true);
                dialog.show();
            });

            materialCardView3.setOnClickListener(v -> {

                Dialog dialog = new Dialog(SavedDetailActivity.this);
                dialog.setContentView(R.layout.dialogue_layout);
                TextView dialog1 = dialog.findViewById(R.id.dialog);
                TextView step1 = dialog.findViewById(R.id.steps);
                dialog1.setText(flower.getDialog3());
                step1.setText(flower.getStep3());
                dialog.setCancelable(true);
                dialog.show();
            });

            materialCardView4.setOnClickListener(v -> {

                Dialog dialog = new Dialog(SavedDetailActivity.this);
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
}