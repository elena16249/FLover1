package com.example.flover1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final ArrayList<Boolean> selectedWeekdays;
    private final OnItemListener onItemListener;
    private DatabaseReference databaseRef;
    private ValueEventListener valueEventListener;

    private String notificationItemId;


    public CalendarAdapter(ArrayList<String> daysOfMonth, ArrayList<Boolean> selectedWeekdays, String notificationItemId, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.selectedWeekdays = selectedWeekdays;
        this.onItemListener = onItemListener;
        this.notificationItemId = notificationItemId;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("Firebase", "eka onbindviewholder");
        Log.d("hello", daysOfMonth.toString());

        int startIndex = 0;
        for (String s : daysOfMonth) {
            if (s.equals("")) {
                startIndex++;
            } else {
                break;
            }
        }
        int tempPosition = startIndex % 2 == 0 ? position * 2 : position * 2 + 1;
        holder.dayOfMonth.setText(daysOfMonth.get(tempPosition));

        // Get the current user ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            // Get a reference to the "selectedDays" node for the current user
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

            // Fetch the selected days data from the database
            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve the selected days map from the snapshot
                        List<NotificationItem> notificationItems = new ArrayList<>();

                        for (DataSnapshot child : snapshot.child("Users")
                                .child(currentUserId)
                                .child("notifications").getChildren()) {

                            String id = child.getKey();
                            Log.e("Firebase", currentUserId + " " + id + " ");
                            notificationItems.add(child.getValue(NotificationItem.class));
                        }

                        for (NotificationItem item : notificationItems) {
                            assert item != null;
                            Log.e("Firebase", " " + item.getName() + " ");
                            ArrayList<Day> selectedDaysMap = item.getSelectedDays();

                            if (selectedDaysMap != null) {
                                Log.e("Firebase", "==========================" + selectedDaysMap.toString());

                                // Clear the selectedWeekdays list before populating it
                                selectedWeekdays.clear();

                                // Iterate over the daysOfMonth list
                                for (int i = 0; i < daysOfMonth.size(); i += 2) {
                                    String dayOfWeek = daysOfMonth.get(i + 1);

                                    // Check if the dayOfWeek exists in the selectedDaysMap and is selected (true)
                                    Boolean selected = false;

                                    for (Day day : selectedDaysMap) {
                                        if (dayOfWeek.toLowerCase().equals(day.getName().toLowerCase())) {
                                            selected = day.isOn();
                                            break;
                                        }
                                    }

                                    selectedWeekdays.add(selected);
                                }

                                // Check if the position is valid within the selectedWeekdays list
                                Log.d("'Firebase", selectedWeekdays.toString());
                                if (position < selectedWeekdays.size()) {
                                    Boolean selected = selectedWeekdays.get(position);

                                    if (selected) {
                                        holder.imageOfDay.setImageResource(R.drawable.flowercell);
                                    } else {
                                        holder.dayOfMonth.setTextColor(Color.BLACK);
                                    }
                                } else {
                                    holder.dayOfMonth.setTextColor(Color.BLACK);
                                }
                            } else {
                                Log.e("Firebase", "Selected days map is null");
                            }
                        }
                    } else {
                        // Handle the case when the snapshot does not exist
                        Log.e("Firebase", "Snapshot does not exist");

                        // Assuming you want to set the text color to a default color when the snapshot is missing
                        holder.dayOfMonth.setTextColor(Color.BLACK);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle any errors that occur during data retrieval
                    Log.e("Firebase", "Database error: " + error.getMessage());
                }
            });
        }
    }





    @Override
    public int getItemCount() {
        return daysOfMonth.size()/2;
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }

    // Call this method in your activity's onDestroy() method to remove the value event listener
    public void cleanup() {
        if (databaseRef != null && valueEventListener != null) {
            databaseRef.removeEventListener(valueEventListener);
        }
    }
}
