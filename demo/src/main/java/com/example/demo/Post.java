package com.example.demo;

import javafx.scene.control.TextField;

public class Post {
    private int PostID;
    private String content;
    private String userID;
    private int reply_id;
    private String time;
    private String imageURL;

    //submission with
    public Post(String content, String userID, String time,int reply_id, String imageURL) {
        this.content = content;
        this.userID = userID;
        this.time = time;
        this.reply_id = reply_id;
        this.imageURL = imageURL;
    }

    public Post(String content, String userID, String time, int reply_id) {
        this.content = content;
        this.userID = userID;
        this.time = time;
        this.reply_id = reply_id;
    }

    public String getContent() {
        return content;
    }

    public String getUserID() {
        return userID;
    }

    public int getReply_id() {
        return reply_id;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        this.PostID = postID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

