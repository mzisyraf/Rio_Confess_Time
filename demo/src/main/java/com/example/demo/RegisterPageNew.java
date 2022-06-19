package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Queue;

public class RegisterPageNew {
    Stage stage;

    private Queue<Post> waitingList;

    public void setWaitingList(Queue waitingList) {
        this.waitingList = waitingList;
    }

    @FXML
    private ImageView backToFrontPage;

    @FXML
    private TextField idRegister;

    @FXML
    private TextField passwordRegister;

    @FXML
    private Button registerButton;

    @FXML
    void registerButtonClicked(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LoginPageNew.fxml"));
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void backToFrontPageClicked(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("FrontPageNew.fxml"));
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.show();
    }

}
