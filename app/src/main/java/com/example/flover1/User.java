package com.example.flover1;

public class User {
        private String username;
        private String name;
        private String email;

        public User(String username, String name, String email, String password) {
            // Required empty public constructor
        }

        public User(String username, String name, String email) {
            this.username = username;
            this.name = name;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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
    }

