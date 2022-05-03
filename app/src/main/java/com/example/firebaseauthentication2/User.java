package com.example.firebaseauthentication2;

public class User {
    public String firstname;
    public String lastName;
    public String email;

    public User(){

    }

    public User(String firstname, String lastName, String email) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
    }
}
