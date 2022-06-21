package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;

public class FrontPageNew implements Initializable {
    Stage stage;
    private Queue<Post> waitingList;

    @FXML
    private Button toLoginInterface;

    @FXML
    private Button toRegisterInterface;

    Queue<Post> waiting;

    public void setWaitingList(Queue<Post> waitingList) {
        this.waitingList = waitingList;
        waiting = this.waitingList;
    }



    @FXML
    void toLoginInterfaceClicked(ActionEvent event) {
        Parent root = null;
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("LoginPageNew.fxml"));
        try {
            root = Loader.load();
        } catch (Exception e) {
        }
        LoginPageNew loginPageNew = Loader.getController();
        loginPageNew.setWaitingList(waiting);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void toRegisterInterfaceClicked(ActionEvent event) {
        Parent root = null;
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("RegisterPageNew.fxml"));
        try {
            Loader.load();
        } catch (Exception e) {
        }
        RegisterPageNew registerPageNew = Loader.getController();
        registerPageNew.setWaitingList(waiting);
        root=Loader.getRoot();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Register Page");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);
    }
}
