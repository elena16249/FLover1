package com.example.flover1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationItem> notificationItems;
    private DatabaseReference databaseReference;
    private AlarmManager alarmManager;
    private Context context;

    public NotificationAdapter(List<NotificationItem> notificationItems, DatabaseReference databaseReference, AlarmManager alarmManager, Context context) {
        this.notificationItems = notificationItems;
        this.databaseReference = databaseReference;
        this.alarmManager = alarmManager != null ? alarmManager : (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create the ViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the data to the ViewHolder
        NotificationItem notificationItem = notificationItems.get(position);
        holder.notificationName.setText(notificationItem.getName());
        holder.notificationTime.setText(notificationItem.getTime());

        // Set the checked state of the switch
        holder.setSwitchState(notificationItem.isAlarmOn());


        holder.deleteButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete reminder");
            builder.setMessage("You are sure that you want to delete this reminder?");

            builder.setPositiveButton("Да", (dialog, which) -> {
                // Пользователь подтвердил удаление - выполните необходимые действия

                // Удалите элемент из списка
                NotificationItem itemToRemove = notificationItems.get(position);
                notificationItems.remove(position);
                notifyDataSetChanged();

                // Отмените связанное с элементом напоминание
                cancelAlarm(itemToRemove);

                // Удалите элемент из базы данных
                databaseReference.child(itemToRemove.getId()).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            // Элемент успешно удален из базы данных
                        })
                        .addOnFailureListener(e -> {
                            // Произошла ошибка при удалении элемента из базы данных
                        });
            });

            builder.setNegativeButton("Нет", (dialog, which) -> {
                // Пользователь отменил удаление - ничего не делать
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });


        // Handle the Switch state change
        holder.alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update the alarm state in the NotificationItem
            notificationItem.setAlarmOn(isChecked);

            // Update the alarm state in the database
            databaseReference.child(notificationItem.getId()).child("alarmOn").setValue(isChecked);

            // Cancel or set the alarm based on the new state
            if (!isChecked) {
                cancelAlarm(notificationItem);
            } else {
                setAlarm(notificationItem);
            }
        });
    }


    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notificationName;
        TextView notificationTime;
        ImageButton deleteButton;
        Switch alarmSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationName = itemView.findViewById(R.id.noteFlower);
            notificationTime = itemView.findViewById(R.id.timeTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);
        }

        public void setSwitchState(boolean isChecked) {
            alarmSwitch.setChecked(isChecked);
        }
    }

    // Method to cancel the alarm associated with the item
// Method to cancel the alarm associated with the item
    private void cancelAlarm(NotificationItem item) {
        // Convert the id to an integer request code
        int requestCode = item.getId().hashCode();

        // Create the PendingIntent for the alarm
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Check for the presence of the permission
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        // Get the AlarmManager instance
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Cancel the alarm using the PendingIntent
        alarmManager.cancel(pendingIntent);
    }



    private void updateAlarmState(NotificationItem item, boolean isChecked) {
        item.setAlarmOn(isChecked);

        // Save the updated notification status to the Firebase Realtime Database
        databaseReference.child(item.getId()).child("alarmOn").setValue(isChecked);

        // Cancel or set the alarm based on the new state
        if (!isChecked) {
            cancelAlarm(item);
        } else {
            setAlarm(item);
        }
    }

    private void setAlarm(NotificationItem item) {
        String timeString = item.getTime();

        // Parse the time string into a Date object
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date alarmTime;
        try {
            alarmTime = sdf.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return; // Handle the error accordingly
        }

        // Get the time in milliseconds
        long alarmTimeInMillis = alarmTime.getTime();

        // Create an explicit intent for the AlarmReceiver class
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("notification_id", item.getId());
        intent.putExtra("notification_name", item.getName());
        // Add any other necessary extras

        // Create a unique PendingIntent using the item's ID as the request code
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Check for the presence of the permission
            pendingIntent = PendingIntent.getBroadcast(context, item.getId().hashCode(), intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, item.getId().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }
}