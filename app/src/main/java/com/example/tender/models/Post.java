package com.example.tender.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {


    private  String username;
    private String title;
    private String message;
    private String imageUrl;
    private Date timestamp;

    private Long likecount;

    public Post(){}

    public Post(String username, String title, String message, String imageUrl, Date timestamp) {
        this.username = username;
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
    }

    public Post(String username, String title, String message, String imageUrl, Date timestamp, Long likecount) {
        this.username = username;
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
        this.likecount=likecount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = username;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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

    public Long getLikecount() {
        return likecount;
    }

    public void setLikecount(Long likecount) {
        this.likecount = likecount;
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