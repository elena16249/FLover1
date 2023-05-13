package com.example.flover1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {
    private RecyclerView savedRecyclerView;
    private SavedAdapter savedAdapter;
    private List<FlowerSaved> savedItemList;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_KEY = "saved_items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_saved);

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
            }
            return false;
        });

        sharedPreferences = getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        // Restoring saved items from shared preferences
        savedItemList = getSavedItemListFromPreferences();

        // Set up RecyclerView and adapter
        savedRecyclerView = findViewById(R.id.savedRecyclerView);
        savedAdapter = new SavedAdapter(savedItemList);
        savedRecyclerView.setAdapter(savedAdapter);
        savedRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve the FlowerName from the intent
        Intent intent = getIntent();
        String flowerName = intent.getStringExtra("FlowerName1");
        String savedImage = intent.getStringExtra("FlowerImage1");
        if (flowerName != null && !isFlowerNameAlreadyAdded(flowerName)) {
            FlowerSaved newFlowerSaved = new FlowerSaved(flowerName, savedImage);
            savedItemList.add(newFlowerSaved);
            savedAdapter.notifyItemInserted(savedItemList.size() - 1);
            saveItemListToPreferences(savedItemList);
        }
    }

    private List<FlowerSaved> getSavedItemListFromPreferences() {
        String savedItemsJson = sharedPreferences.getString(SHARED_PREFS_KEY, "");
        if (!savedItemsJson.isEmpty()) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FlowerSaved>>() {}.getType();
            return gson.fromJson(savedItemsJson, listType);
        }
        return new ArrayList<>();
    }

    private void saveItemListToPreferences(List<FlowerSaved> itemList) {
        Gson gson = new Gson();
        String savedItemsJson = gson.toJson(itemList);
        sharedPreferences.edit().putString(SHARED_PREFS_KEY, savedItemsJson).apply();
    }

    private boolean isFlowerNameAlreadyAdded(String flowerName) {
        for (FlowerSaved flower : savedItemList) {
            if (flower.getSavedFlowername().equals(flowerName)) {
                return true; // Flower name already exists
            }
        }
        return false; // Flower name doesn't exist
    }
    public void deleteItem(int position) {
        savedItemList.remove(position);
        savedAdapter.notifyItemRemoved(position);
        saveItemListToPreferences(savedItemList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        savedAdapter.notifyDataSetChanged();
    }
}