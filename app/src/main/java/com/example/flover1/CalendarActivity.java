package com.example.flover1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private Map<String, Boolean> selectedDaysMap;

    private CalendarAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        selectedDate = LocalDate.now();
        selectedDaysMap = new HashMap<>(); // Initialize the selectedDaysMap
        setMonthView();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_calendar);
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
                    startActivity(new Intent(getApplicationContext(), SavedActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_calendar:
                    return true;
            }
            return false;
        });
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        // Convert the selectedDaysMap to ArrayList<Boolean>
        ArrayList<Boolean> selectedWeekdays = new ArrayList<>();
        selectedWeekdays.add(selectedDaysMap.get("Sunday"));
        selectedWeekdays.add(selectedDaysMap.get("Monday"));
        selectedWeekdays.add(selectedDaysMap.get("Tuesday"));
        selectedWeekdays.add(selectedDaysMap.get("Wednesday"));
        selectedWeekdays.add(selectedDaysMap.get("Thursday"));
        selectedWeekdays.add(selectedDaysMap.get("Friday"));
        selectedWeekdays.add(selectedDaysMap.get("Saturday"));

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(currentUserId).child("notifications");
        String notificationItemId = databaseReference.push().getKey();
        calendarAdapter = new CalendarAdapter(daysInMonth, selectedWeekdays, notificationItemId, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private void updateCalendar(){
        calendarAdapter.cleanup();
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
                daysInMonthArray.add("");
            } else {
                LocalDate currentDate = date.withDayOfMonth(i - dayOfWeek);
                String dayOfWeekString = currentDate.getDayOfWeek().toString();
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
                daysInMonthArray.add(String.valueOf(dayOfWeekString));
            }
        }
        return daysInMonthArray;
    }

    private ArrayList<String> daysOfWeekInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        String dayOfWeek = firstOfMonth.getDayOfWeek().toString();
        daysInMonthArray.add(dayOfWeek);
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        updateCalendar();
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        updateCalendar();
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}