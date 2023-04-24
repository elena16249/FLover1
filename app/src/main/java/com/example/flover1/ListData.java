package com.example.flover1;

public class ListData {
    String name;
    int accessories, desc;
    int image;

    public ListData(String name, int accessories, int desc, int image) {
        this.name = name;
        this.accessories = accessories;
        this.desc = desc;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccessories() {
        return accessories;
    }

    public void setAccessories(int accessories) {
        this.accessories = accessories;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}