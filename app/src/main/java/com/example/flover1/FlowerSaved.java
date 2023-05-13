package com.example.flover1;

import android.os.Parcel;
import android.os.Parcelable;

public class FlowerSaved implements Parcelable {
    private String savedFlowername;
    private String savedImage;

    public FlowerSaved(String savedFlowername, String savedImage) {
        this.savedFlowername = savedFlowername;
        this.savedImage = savedImage;
    }

    protected FlowerSaved(Parcel in) {
        savedFlowername = in.readString();
        savedImage = in.readString();
    }

    public static final Creator<FlowerSaved> CREATOR = new Creator<FlowerSaved>() {
        @Override
        public FlowerSaved createFromParcel(Parcel in) {
            return new FlowerSaved(in);
        }

        @Override
        public FlowerSaved[] newArray(int size) {
            return new FlowerSaved[size];
        }
    };

    public String getSavedFlowername() {
        return savedFlowername;
    }

    public String getSavedImage() {
        return savedImage;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(savedFlowername);
        dest.writeString(savedImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}