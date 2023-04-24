package com.example.flover1;

public class Flower {
    private String name;
    private String accessories;
    private String description;
    private String image;

    private String key;


    public Flower() {}

    public Flower(String name, String accessories, String description, String image) {
        this.name = name;
        this.accessories = accessories;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getAccessories() {
        return accessories;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setKey(String key) {
        this.key = this.key;
    }

    public String getKey() {
        return key;
    }
}
