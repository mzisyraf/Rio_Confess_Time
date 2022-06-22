package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

public class Replies implements Initializable{

    @FXML
    private VBox reply;

    private Queue<Post> waiting;
    private Post post;

    public void setPost(Post post) {
        this.post = post;
    }

    private int postID;

    private List<Post> posts = new ArrayList<>();

    void setPostID(int postID){
        this.postID = postID;
    }

    private ArrayList<Post> getReplies(){
        ArrayList<Post> replies = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission WHERE id_reply = ?");
            preparedStatement.setInt(1, postID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                replies.add(new Post(resultSet.getInt(2), resultSet.getInt(1), resultSet.getString(3), resultSet.getString(7), resultSet.getString(5),resultSet.getString(6), null ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return replies;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM buffer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                this.postID = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.posts.addAll(replyList());

        try{
            for(int i = 0; i < posts.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("PostOnly.fxml"));
                VBox vBox =  fxmlLoader.load();
                PostOnly postOnly = fxmlLoader.getController();
                postOnly.setData(posts.get(i));
                reply.getChildren().add(vBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Post> replyList(){
        ArrayList<Post> postReplies = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission where id_reply = ?");
            preparedStatement.setInt(1,this.postID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                postReplies.add(new Post(resultSet.getInt(2),resultSet.getInt(1),resultSet.getString(3),resultSet.getString(7),resultSet.getString(5),resultSet.getString(6),null));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error 1");
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println("Error 2");
            System.out.println(e);
        }
        return postReplies;
    }
}
