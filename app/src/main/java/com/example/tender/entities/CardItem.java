package com.example.tender.entities;

public class CardItem {
    private String title;
    private String description;
    private int profileImageId;
    public CardItem(String title, String description, int profileImageId) {
        this.title = title;
        this.description = description;
        this.profileImageId = profileImageId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getProfileImageId() {
        return profileImageId;
    }
}
