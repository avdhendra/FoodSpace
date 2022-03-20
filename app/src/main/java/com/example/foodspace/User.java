package com.example.foodspace;

public class User {
    String id;
    String firstname;
    String email;
    String image;
    Integer mobile;
    Integer profileCompleted;

    public User(String id, String firstname, String email, String image, Integer mobile, Integer profileCompleted) {
        this.id = id;
        this.firstname = firstname;
        this.email = email;
        this.image = image;
        this.mobile = mobile;
        this.profileCompleted = profileCompleted;
    }

}
