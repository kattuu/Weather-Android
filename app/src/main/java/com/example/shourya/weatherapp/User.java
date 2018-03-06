package com.example.shourya.weatherapp;

public class User {

    private String name,password,email,mobile;
    public String uid;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String password, String email, String mobile) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

}
