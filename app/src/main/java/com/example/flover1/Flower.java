package com.example.flover1;

public class Flower {
    private String name;
    private String accessories;
    private String description;
    private String image;

    private  String condition1;
    private  String condition2;
    private  String condition3;
    private  String condition4;
    private String key;


    public Flower() {}

    public Flower(String name, String accessories, String description, String image, String condition1, String condition2, String condition3, String condition4) {
        this.name = name;
        this.accessories = accessories;
        this.description = description;
        this.image = image;
        this.condition1 = condition1;
        this.condition2 = condition2;
        this.condition3 = condition3;
        this.condition4 = condition4;
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
}
