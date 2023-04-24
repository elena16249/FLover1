package com.example.flover1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;


public class DetailedActivity extends AppCompatActivity {

    TextView description, flowerName, accessories;
    ImageView flowerImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        description = findViewById(R.id.detailDesc);
        flowerImage = findViewById(R.id.detailImage);
        flowerName = findViewById(R.id.detailTitle);
        accessories = findViewById(R.id.detailLang);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            description.setText(bundle.getString("Description"));
            flowerName.setText(bundle.getString("FlowerName"));
            accessories.setText(bundle.getString("Accessories"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(flowerImage);
        }


    }
}