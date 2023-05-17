package com.example.flover1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FlowerSaved implements Parcelable {
    private String name;


    private String flowerId;
    private String description;
    private String image;
    private String condition1;
    private String condition2;
    private String condition3;
    private String condition4;
    private String dialog1;
    private String dialog2;
    private String dialog3;
    private String dialog4;
    private String step1;
    private String step2;
    private String step3;
    private String step4;


    public FlowerSaved() {
    }


    protected FlowerSaved(Parcel in) {
        flowerId = in.readString();
        name = in.readString();
        description = in.readString();
        image = in.readString();
        condition1 = in.readString();
        condition2 = in.readString();
        condition3 = in.readString();
        condition4 = in.readString();
        dialog1 = in.readString();
        dialog2 = in.readString();
        dialog3 = in.readString();
        dialog4 = in.readString();
        step1 = in.readString();
        step2 = in.readString();
        step3 = in.readString();
        step4 = in.readString();
    }

    public static final Creator<Flower> CREATOR = new Creator<Flower>() {
        @Override
        public Flower createFromParcel(Parcel in) {
            return new Flower(in);
        }

        @Override
        public Flower[] newArray(int size) {
            return new Flower[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(condition1);
        dest.writeString(condition2);
        dest.writeString(condition3);
        dest.writeString(condition4);
        dest.writeString(dialog1);
        dest.writeString(dialog2);
        dest.writeString(dialog3);
        dest.writeString(dialog4);
        dest.writeString(step1);
        dest.writeString(step2);
        dest.writeString(step3);
        dest.writeString(step4);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(String flowerId) {
        this.flowerId = flowerId;
    }

    public String getImage() {
        return image;
    }

    public String getCondition1() {
        return condition1;
    }

    public String getCondition2() {
        return condition2;
    }

    public String getCondition3() {
        return condition3;
    }

    public String getCondition4() {
        return condition4;
    }

    public String getDialog1() {
        return dialog1;
    }

    public String getDialog2() {
        return dialog2;
    }

    public String getDialog3() {
        return dialog3;
    }

    public String getDialog4() {
        return dialog4;
    }

    public String getStep1() {
        return step1;
    }

    public String getStep2() {
        return step2;
    }

    public String getStep3() {
        return step3;
    }

    public String getStep4() {
        return step4;
    }

    public FlowerSaved(String name, String description, String image, String condition1, String condition2, String condition3, String condition4, String dialog1, String dialog2, String dialog3, String dialog4, String step1, String step2, String step3, String step4) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.condition1 = condition1;
        this.condition2 = condition2;
        this.condition3 = condition3;
        this.condition4 = condition4;
        this.dialog1 = dialog1;
        this.dialog2 = dialog2;
        this.dialog3 = dialog3;
        this.dialog4 = dialog4;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
    }

}