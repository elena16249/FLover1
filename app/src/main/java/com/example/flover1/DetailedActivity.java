package com.example.flover1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;


public class  DetailedActivity extends AppCompatActivity {

    TextView description, flowerName, accessories, condition1,condition2,condition3,condition4;
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


    }
}