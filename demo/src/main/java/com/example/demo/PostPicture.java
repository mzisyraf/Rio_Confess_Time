package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PostPicture {
    private Post post;

    @FXML
    private Label postConfession;

    @FXML
    private Label postId1;

    @FXML
    private Label postId2;

    @FXML
    private Label postId;

    @FXML
    private Label postDate;

    @FXML
    private Label postTime;

    @FXML
    private ImageView postImage;

    @FXML
    private ImageView postReplyButton;

    @FXML
    void postReplyButtonClicked(MouseEvent event) {

    }

    void setData(Post post){
        this.post = post;
        postConfession.setText(post.getContent());
        postId.setText(String.valueOf(post.getPostID()));
        if (!(post.getReply_id()==0)){
            postId1.setOpacity(1);
            postId2.setText(String.valueOf(post.getReply_id()));
            postId2.setOpacity(1);
        }
        postDate.setText(post.getDate());
        postTime.setText(post.getTime());

    }
}
