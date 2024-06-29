package com.example.tender.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {
    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = username;
    }

    private  String username;
    private String title;
    private StringBuilder message;
    private String imageUrl;
    private Date timestamp;

    public Post(String username, String title, StringBuilder message, String imageUrl, Date timestamp) {
        this.username = username;
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

    public Map<String, Object> toJson() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("username", username);
        postData.put("title", title);
        postData.put("message", message.toString());
        postData.put("imageUrl", imageUrl);
        postData.put("timestamp", timestamp);
        return postData;
    }
}