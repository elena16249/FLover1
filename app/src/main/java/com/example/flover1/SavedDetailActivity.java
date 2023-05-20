package com.example.flover1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

public class SavedDetailActivity extends AppCompatActivity {
    TextView description, flowerName, condition1, condition2, condition3, condition4;
    ImageView flowerImage;
    Button savedFlowerAlarm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_detailed);

        description = findViewById(R.id.detailDescSaved);
        flowerImage = findViewById(R.id.detailImageSaved);
        flowerName = findViewById(R.id.detailTitleSaved);
        condition1 = findViewById(R.id.condition1Saved);
        condition2 = findViewById(R.id.condition2Saved);
        condition3 = findViewById(R.id.condition3Saved);
        condition4 = findViewById(R.id.condition4Saved);

        savedFlowerAlarm = findViewById(R.id.savedFlowerAlarm);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            FlowerSaved flower = bundle.getParcelable("flowerSaved");
            description.setText(flower.getDescription()); // name is description
            flowerName.setText(flower.getName());
            condition1.setText(flower.getCondition1());
            condition2.setText(flower.getCondition2());
            condition3.setText(flower.getCondition3());
            condition4.setText(flower.getCondition4());


            Glide.with(this)
                    .load(flower.getImage())
                    .into(flowerImage);


            savedFlowerAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SavedDetailActivity.this, AlarmActivity.class);
                    intent.putExtra("flowerName", flower);
                    startActivity(intent);
                }
            });

            MaterialCardView[] materialCardViews = new MaterialCardView[4];
            materialCardViews[0] = findViewById(R.id.materialcardview1);
            materialCardViews[1] = findViewById(R.id.materialcardview2);
            materialCardViews[2] = findViewById(R.id.materialcardview3);
            materialCardViews[3] = findViewById(R.id.materialcardview4);

            for (int i = 0; i < materialCardViews.length; i++) {
                int index = i;
                materialCardViews[i].setOnClickListener(v -> {
                    Dialog dialog = new Dialog(SavedDetailActivity.this);
                    dialog.setContentView(R.layout.dialogue_layout);
                    TextView dialogTextView = dialog.findViewById(R.id.dialog);
                    TextView stepsTextView = dialog.findViewById(R.id.steps);

                    switch (index) {
                        case 0:
                            dialogTextView.setText(flower.getDialog1());
                            stepsTextView.setText(flower.getStep1());
                            break;
                        case 1:
                            dialogTextView.setText(flower.getDialog2());
                            stepsTextView.setText(flower.getStep2());
                            break;
                        case 2:
                            dialogTextView.setText(flower.getDialog3());
                            stepsTextView.setText(flower.getStep3());
                            break;
                        case 3:
                            dialogTextView.setText(flower.getDialog4());
                            stepsTextView.setText(flower.getStep4());
                            break;
                    }

                    dialog.setCancelable(true);
                    dialog.show();
                });
            }

        }


    }


}
