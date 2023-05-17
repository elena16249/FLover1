package com.example.flover1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<Flower> dataList;
    ListAdapter adapter;
    SearchView searchView;

    Button roseFilter, tulipFilter, pinkFilter, whiteFilter, yellowFilter;

    @SuppressLint("MissingInflatedId")

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new ListAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Flowers");



        roseFilter = findViewById(R.id.rose);
        tulipFilter = findViewById(R.id.tulip);
        pinkFilter = findViewById(R.id.pink);
        whiteFilter = findViewById(R.id.white);
        yellowFilter = findViewById(R.id.yellow);

        roseFilter.setOnClickListener(v -> {
            @SuppressLint("ResourceType")
            String text = roseFilter.getText().toString();
            roseFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green_700));
            tulipFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            pinkFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            whiteFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            yellowFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));

            ArrayList<Flower> searchList = new ArrayList<>();
            for (Flower dataClass : dataList) {
                if (dataClass.getName().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(dataClass);

                }
            }
            adapter.searchDataList(searchList);

        });
        tulipFilter.setOnClickListener(v -> {
            String text = tulipFilter.getText().toString();
            tulipFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green_700));
            roseFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            pinkFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            whiteFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            yellowFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));

            ArrayList<Flower> searchList = new ArrayList<>();
            for (Flower dataClass : dataList) {
                if (dataClass.getName().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(dataClass);
                }
            }

            // Update the adapter with the filtered data
            adapter.searchDataList(searchList);


        });


        pinkFilter.setOnClickListener(v -> {
            @SuppressLint("ResourceType")
            String text = pinkFilter.getText().toString();
            pinkFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green_700));
            roseFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            tulipFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            whiteFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            yellowFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));


            ArrayList<Flower> searchList = new ArrayList<>();
            for (Flower dataClass : dataList) {
                if (dataClass.getName().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(dataClass);

                }
            }
            adapter.searchDataList(searchList);

        });
        whiteFilter.setOnClickListener(v -> {
            @SuppressLint("ResourceType")
            String text = whiteFilter.getText().toString();
            whiteFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green_700));
            roseFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            pinkFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            tulipFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            yellowFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            ArrayList<Flower> searchList = new ArrayList<>();
            for (Flower dataClass : dataList) {
                if (dataClass.getName().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(dataClass);

                }
            }
            adapter.searchDataList(searchList);

        });
        yellowFilter.setOnClickListener(v -> {
            @SuppressLint("ResourceType")
            String text = yellowFilter.getText().toString();
            yellowFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green_700));
            roseFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            pinkFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            whiteFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));
            tulipFilter.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.light_gray));


            ArrayList<Flower> searchList = new ArrayList<>();
            for (Flower dataClass : dataList) {
                if (dataClass.getName().toLowerCase().contains(text.toLowerCase())) {

                    searchList.add(dataClass);

                }
            }
            adapter.searchDataList(searchList);

        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_saved:
                    startActivity(new Intent(getApplicationContext(), SavedActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot flowerSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot itemSnapshot : flowerSnapshot.getChildren()) {
                        Flower flower = itemSnapshot.getValue(Flower.class);
                        dataList.add(flower);
                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
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
        ArrayList<Flower> searchList = new ArrayList<>();
        for (Flower flower : dataList) {
            if (flower.getName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(flower);
            }
        }
        adapter.searchDataList(searchList);

    }
}


