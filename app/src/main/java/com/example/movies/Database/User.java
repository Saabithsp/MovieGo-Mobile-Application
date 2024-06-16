package com.example.movies.Database;

public class User {
    private String dob;
    private String email;
    private String gender;
    private String name;
    private String password;

    // Empty constructor needed for Firestore
    public User() {}

    public User( String email, String name, String password) {
//        this.dob = dob;
//        this.gender = gender;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // Getters and setters
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
