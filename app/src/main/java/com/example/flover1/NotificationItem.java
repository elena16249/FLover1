package com.example.flover1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NotificationItem implements Parcelable {
    private String id;
    private String name;
    private String time;
    private String interval;
    private boolean isAlarmOn;
    private ArrayList selectedDays;

    public NotificationItem() {
        // Required for Firebase deserialization
    }

    public NotificationItem(String id, String name, String time, String interval, boolean isAlarmOn,
                            ArrayList<Day> selectedDays) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.interval = interval;
        this.isAlarmOn = isAlarmOn;
        this.selectedDays = selectedDays;
    }

    protected NotificationItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        time = in.readString();
        interval = in.readString();
        isAlarmOn = in.readByte() != 0;
        selectedDays = in.readArrayList(getClass().getClassLoader());
    }

    public static final Creator<NotificationItem> CREATOR = new Creator<NotificationItem>() {
        @Override
        public NotificationItem createFromParcel(Parcel in) {
            return new NotificationItem(in);
        }

        @Override
        public NotificationItem[] newArray(int size) {
            return new NotificationItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(interval);
        dest.writeByte((byte) (isAlarmOn ? 1 : 0));
        dest.writeList(selectedDays);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getInterval() {
        return interval;
    }

    public boolean isAlarmOn() {
        return isAlarmOn;
    }

    public ArrayList<Day> getSelectedDays() {
        return selectedDays;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setAlarmOn(boolean alarmOn) {
        isAlarmOn = alarmOn;
    }

    public void setSelectedDays(ArrayList<Day> selectedDays) {
        this.selectedDays = selectedDays;
    }
}
