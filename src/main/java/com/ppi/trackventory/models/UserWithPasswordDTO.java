package com.ppi.trackventory.models;

public class UserWithPasswordDTO {
    private User user;
    private String password;

    // Getters y Setters
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}