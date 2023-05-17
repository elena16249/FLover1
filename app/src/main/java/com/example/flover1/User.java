package com.example.flover1;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private ArrayList<Flower> favoriteFlowers;

    public User() {
    }

    public User(String name, String email, ArrayList<Flower> favoriteFlowers) {
        this.name = name;
        this.email = email;
        this.favoriteFlowers = favoriteFlowers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Flower> getFavoriteFlowers() {
        return favoriteFlowers;
    }
}

