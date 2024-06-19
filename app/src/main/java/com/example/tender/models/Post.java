package com.example.tender.models;

import android.net.Uri;

import java.util.Date;

public class Post {
    private String title;
    private StringBuilder message;
    private String imageUrl;
    private Date timestamp;

    public Post(String title, StringBuilder message, String imageUrl, Date timestamp) {
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StringBuilder getMessage() {
        return message;
    }

    public void setMessage(StringBuilder message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}