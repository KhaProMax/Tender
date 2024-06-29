package com.example.tender.models;

public class User {
    String username;
    String password;

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    String profileUri;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public User( String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User() {
    }
}
