package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PostOnly {

    @FXML
    private Label postConfession;

    @FXML
    private Label postId;

    @FXML
    private Label postDate;

    @FXML
    private Label postTime;

    @FXML
    private Label postId1;

    @FXML
    private Label postId2;

    @FXML
    private Text postReplyButton;

    private Post post;
    private Post temp;
    Stage stage;
    Parent root;

    @FXML
    void postReplyButtonClicked(MouseEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Stage newStage = new Stage();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO buffer VALUES(?)");
            PreparedStatement preparedStatement1 = con.prepareStatement("DELETE FROM buffer");
            preparedStatement.setInt(1, Integer.parseInt(postId.getText().trim()));
            preparedStatement1.execute();
            preparedStatement1.close();
            preparedStatement.execute();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Replies.fxml"));
            AnchorPane anchorPane = Loader.load();
            newStage.setScene(new Scene(anchorPane));
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(stage);
            newStage.showAndWait();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
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
