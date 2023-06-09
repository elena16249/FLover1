package com.example.flover1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {


    SearchView searchView;

    RecyclerView recyclerView;
    List<FlowerSaved> dataList;
    ValueEventListener eventListener;
    SavedAdapter adapter;
    DatabaseReference databaseReference;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        recyclerView = findViewById(R.id.savedRecyclerView);

        searchView = findViewById(R.id.search2);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(SavedActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(SavedActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();

        dataList = new ArrayList<>();
        adapter = new SavedAdapter(SavedActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("favoriteFlowers");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_saved);
        // Получение ColorStateList с использованием ContextCompat
        ColorStateList iconColorStateList = ContextCompat.getColorStateList(this, R.drawable.icon_selector);

// Установка ColorStateList для значков
        bottomNavigationView.setItemIconTintList(iconColorStateList);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_saved:
                    // Already on the saved activity, no need to do anything
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_calendar:
                    startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });




        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FlowerSaved> favoriteFlowers = new ArrayList<>();

                for (DataSnapshot flowerSnapshot : dataSnapshot.getChildren()) {
                    FlowerSaved favoriteFlower = flowerSnapshot.getValue(FlowerSaved.class);

                    if (favoriteFlower != null) {
                        favoriteFlowers.add(favoriteFlower);
                    }
                }

                dataList.clear();
                dataList.addAll(favoriteFlowers);
                adapter.notifyDataSetChanged();
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the database operation
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

    }
    public void searchList(String text) {
        ArrayList<FlowerSaved> searchList = new ArrayList<>();
        for (FlowerSaved flower : dataList) {
            if (flower.getName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(flower);
            }
        }
        adapter.searchDataList(searchList);

    }

    @Override

    public void onBackPressed() {
        //ignored
    }



}