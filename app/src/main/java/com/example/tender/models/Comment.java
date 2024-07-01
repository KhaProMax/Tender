package com.example.tender.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Comment {


    private  String username;
    private String message;
    private Date timestamp;

    public Comment(){}

    public Comment(String username, String message, Date timestamp) {
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("username", username);
        commentData.put("message", message);
        commentData.put("timestamp", timestamp);
        return commentData;
    }
}