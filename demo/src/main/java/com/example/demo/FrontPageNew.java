package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Queue;

public class FrontPageNew {
    Stage stage;
    private Queue<Post> waitingList;

    public void setWaitingList(Queue waitingList) {
        this.waitingList = waitingList;
    }

    @FXML
    private Button toLoginInterface;

    @FXML
    private Button toRegisterInterface;

    @FXML
    void toLoginInterfaceClicked(ActionEvent event) {
        Parent root = null;
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("LoginPageNew.fxml"));
        try {
            root = Loader.load(getClass().getResource("LoginPageNew.fxml"));
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        LoginPageNew loginPageNew = Loader.getController();
        loginPageNew.setWaitingList(waitingList);
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void toRegisterInterfaceClicked(ActionEvent event) {
        Parent root = null;
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("RegisterPageNew.fxml"));
        try {
            root = Loader.load(getClass().getResource("RegisterPageNew.fxml"));
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        RegisterPageNew registerPageNew = Loader.getController();

        stage.setTitle("Register Page");
        stage.show();
    }

}
