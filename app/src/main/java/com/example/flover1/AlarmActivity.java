package com.example.flover1;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AlarmActivity extends AppCompatActivity {

    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int notificationInterval = 1;
    private int notificationId = 1;

    private EditText alarmName;
    private boolean isAlarmAlreadyExists;

    private ArrayList<Button> dayButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        createNotificationChannel();

        Button selectTimeBtn = findViewById(R.id.selectTimeBtn);
        Button setAlarmBtn = findViewById(R.id.setAlarmBtn);
        Button cancelAlarmBtn = findViewById(R.id.cancelAlarmBtn);


        alarmName = findViewById(R.id.alarmName);

        // Initialize day buttons list
        dayButtons = new ArrayList<>();
        dayButtons.add(findViewById(R.id.sundayButton));
        dayButtons.add(findViewById(R.id.mondayButton));
        dayButtons.add(findViewById(R.id.tuesdayButton));
        dayButtons.add(findViewById(R.id.wednesdayButton));
        dayButtons.add(findViewById(R.id.thursdayButton));
        dayButtons.add(findViewById(R.id.fridayButton));
        dayButtons.add(findViewById(R.id.saturdayButton));


        alarmName = findViewById(R.id.alarmName);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            FlowerSaved flowerName = bundle.getParcelable("flowerName");
            if (flowerName != null) {
                alarmName.setText(flowerName.getName());
                Toast.makeText(this, flowerName.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        selectTimeBtn.setOnClickListener(v -> showTimePicker());

        setAlarmBtn.setOnClickListener(v -> setAlarm());

        cancelAlarmBtn.setOnClickListener(v -> cancelAlarm());

//        selectIntervalBtn.setOnClickListener(v -> showIntervalPicker());
    }
    public void onClickSunday(View view) {
        Button button = (Button) view;
        button.setSelected(!button.isSelected());
    }

    public void onClickMonday(View view) {
        Button button = (Button) view;
        button.setSelected(!button.isSelected());
    }

    public void onClickTuesday(View view) {
        Button button = (Button) view;
        button.setSelected(!button.isSelected());
    }

    public void onClickWednesday(View view) {
        Button button = (Button) view;
        button.setSelected(!button.isSelected());
    }

    public void onClickThursday(View view) {
        Button button = (Button) view;
        button.setSelected(!button.isSelected());
    }

    public void onClickFriday(View view) {
        Button button = (Button) view;
        button.setSelected(!button.isSelected());
    }

    public void onClickSaturday(View view) {
        Button button = (Button) view;
        button.setSelected(!button.isSelected());
    }

    private boolean isButtonSelected(int buttonId) {
        Button button = findViewById(buttonId);
        return button.isSelected();
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        startActivity(new Intent(this, ProfileActivity.class));
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        alarmName = findViewById(R.id.alarmName);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        String name = String.valueOf(alarmName.getText());
        checkIfAlarmExists(name, () -> {
            if (isAlarmAlreadyExists) {
                Toast.makeText(this, "Alarm with the same name already exists", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();

                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(currentUserId).child("notifications");
                String notificationItemId = databaseReference.push().getKey();

                // Retrieve the selected days from buttons
                boolean isSundaySelected = isButtonSelected(R.id.sundayButton);
                boolean isMondaySelected = isButtonSelected(R.id.mondayButton);
                boolean isTuesdaySelected = isButtonSelected(R.id.tuesdayButton);
                boolean isWednesdaySelected = isButtonSelected(R.id.wednesdayButton);
                boolean isThursdaySelected = isButtonSelected(R.id.thursdayButton);
                boolean isFridaySelected = isButtonSelected(R.id.fridayButton);
                boolean isSaturdaySelected = isButtonSelected(R.id.saturdayButton);

                // Create a map to store the selected days
                ArrayList<Day> selectedDaysMap = new ArrayList<>();
                selectedDaysMap.add(new Day("Sunday", isSundaySelected));
                selectedDaysMap.add(new Day("Monday", isMondaySelected));
                selectedDaysMap.add(new Day("Tuesday", isTuesdaySelected));
                selectedDaysMap.add(new Day("Wednesday", isWednesdaySelected));
                selectedDaysMap.add(new Day("Thursday", isThursdaySelected));
                selectedDaysMap.add(new Day("Friday", isFridaySelected));
                selectedDaysMap.add(new Day("Saturday", isSaturdaySelected));

                // Iterate over the selected days and set an alarm for each selected day
                for (Day day : selectedDaysMap) {
                    if (day.isOn()) {
                        // Calculate the time for the alarm based on the selected day
                        Calendar alarmTime = calculateAlarmTime(day.getName());

                        // Set the repeating alarm for the selected day
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                    }
                }

                // Create a notification item with the selected days
                NotificationItem notificationItem = new NotificationItem(notificationItemId, name, getTimeString(),
                        Integer.toString(notificationInterval), true, selectedDaysMap);

                databaseReference.child(notificationItemId).setValue(notificationItem);

                startActivity(new Intent(this, ProfileActivity.class));
            }
        });
    }

    private Calendar calculateAlarmTime(String dayOfWeek) {
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, picker.getHour());
        alarmTime.set(Calendar.MINUTE, picker.getMinute());
        alarmTime.set(Calendar.SECOND, 0);
        alarmTime.set(Calendar.MILLISECOND, 0);

        // Find the next occurrence of the selected day of the week
        while (alarmTime.get(Calendar.DAY_OF_WEEK) != getDayOfWeekValue(dayOfWeek)) {
            alarmTime.add(Calendar.DAY_OF_WEEK, 1);
        }

        return alarmTime;
    }

    private int getDayOfWeekValue(String dayOfWeek) {
        switch (dayOfWeek) {
            case "Sunday":
                return Calendar.SUNDAY;
            case "Monday":
                return Calendar.MONDAY;
            case "Tuesday":
                return Calendar.TUESDAY;
            case "Wednesday":
                return Calendar.WEDNESDAY;
            case "Thursday":
                return Calendar.THURSDAY;
            case "Friday":
                return Calendar.FRIDAY;
            case "Saturday":
                return Calendar.SATURDAY;
            default:
                return -1; // Invalid value
        }
    }

    private boolean isSelected(int buttonId) {
        Button button = findViewById(buttonId);
        return button.isSelected();
    }


    private void checkIfAlarmExists(String name, OnAlarmExistenceCheckedListener listener) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(currentUserId).child("notifications");

        Query query = databaseReference.orderByChild("name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    isAlarmAlreadyExists = true;
                } else {
                    isAlarmAlreadyExists = false;
                }
                listener.onAlarmExistenceChecked();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                isAlarmAlreadyExists = false;
                listener.onAlarmExistenceChecked();
            }
        });
    }

    private interface OnAlarmExistenceCheckedListener {
        void onAlarmExistenceChecked();
    }

    private String getTimeString() {
        int hour = picker.getHour();
        int minute = picker.getMinute();
        return String.format("%02d:%02d", hour, minute);
    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();

        picker.show(getSupportFragmentManager(), "foxandroid");

        picker.addOnPositiveButtonClickListener(v -> {
            String time = getTimeString();

            TextView selectedTimeTextView = findViewById(R.id.selectedTime);
            selectedTimeTextView.setText(time);

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
            calendar.set(Calendar.MINUTE, picker.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        });
    }

    private void showIntervalPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Notification Interval");

        final CharSequence[] items = {"Every day", "Twice a week", "Every week"};
        int checkedItem = 0; // Default: Every day

        builder.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which) {
                case 0:
                    notificationInterval = 1; // Every day
                    break;
                case 1:
                    notificationInterval = 2; // Twice a week
                    break;
                case 2:
                    notificationInterval = 7; // Every week
                    break;
            }

//            TextView selectedIntervalTextView = findViewById(R.id.selectInterval);
//            selectedIntervalTextView.setText(items[which]);

            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createNotificationChannel() {

        CharSequence name = "foxandroidReminderChannel";
        String description = "Channel For Alarm Manager";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("foxandroid", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }

    @Override
    public void onBackPressed() {
        // Ignored
    }
}