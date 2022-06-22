package com.example.demo;

public class Post {
    private int PostID;
    private String content;
    private String userID;
    private int reply_id;
    private String date, time;
    private String imageURL;
    private Byte image;

    public Post(int reply_id, String content, String imageURL, String date, String time, String userID ) {
        this.content = content;
        this.userID = userID;
        this.date = date;
        this.time = time;
        this.reply_id = reply_id;
        this.imageURL = imageURL;
    }

    public Post(int reply_id, int postID, String content, String userID,  String date, String time, Byte image) {
        PostID = postID;
        this.content = content;
        this.userID = userID;
        this.reply_id = reply_id;
        this.date = date;
        this.time = time;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public String getImageURL() {
        return imageURL;
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

