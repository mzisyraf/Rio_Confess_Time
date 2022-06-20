package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Queue;
import java.util.Timer;

public class HelloApplication extends Application {

    private Queue<Post> waitingList;

    @Override
    public void start(Stage stage) throws IOException {
        Queue waitingList = this.waitingList;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FrontPageNew.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1660, 750);
        stage.setTitle("CONFESS TIME");
        stage.setScene(scene);
        stage.setMaximized(true);
        FrontPageNew frontPageNew = fxmlLoader.getController();
        frontPageNew.setWaitingList(waitingList);
        stage.show();

    }

    public static void main(String[] args) {

        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000); // Create Repetitively task for every 1 secs

        launch();
    }
}