package com.example.flover1;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Onboarding extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if onboarding is completed
        sharedPreferences = getSharedPreferences("onboarding", MODE_PRIVATE);
        boolean isOnboardingCompleted = sharedPreferences.getBoolean("is_completed", false);

        if (isOnboardingCompleted) {
            // Onboarding completed, go to the main activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            // Onboarding not completed, show onboarding screens
            setContentView(R.layout.onboarding);

            layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
            buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);
            setupOnboardingItems();

            ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
            onboardingViewPager.setAdapter(onboardingAdapter);

            setupOnboardingIndicators();
            setCurrentOnboardingIndicator(0);

            onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrentOnboardingIndicator(position);
                }
            });
            buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                        onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                    } else {
                        // Mark onboarding as completed
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("is_completed", true);
                        editor.apply();

                        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                        finish();
                    }
                }
            });
        }
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem welcome1 = new OnboardingItem();
        welcome1.setTitle("Welcome!");
        welcome1.setDescription("Life is short, buy plants");
        welcome1.setImage(R.drawable.background1);

        OnboardingItem welcome2 = new OnboardingItem();
        welcome2.setTitle("Take care of your FLovers with us...!");
        welcome2.setDescription("We'll help you not to forget about caring for flowers");
        welcome2.setImage(R.drawable.background2);

        OnboardingItem welcome3 = new OnboardingItem();
        welcome3.setTitle("Nourish the flower with water...");
        welcome3.setImage(R.drawable.background3);

        onboardingItems.add(welcome1);
        onboardingItems.add(welcome2);
        onboardingItems.add(welcome3);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("START");
        } else {
            buttonOnboardingAction.setText("NEXT");
        }
    }
}
