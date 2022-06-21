package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FrontPageNew.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1660, 750);
        stage.setTitle("CONFESS TIME");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.show();

    }

    public static void main(String[] args) {
        Queue waitingList = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waitingList); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000); // Create Repetitively task for every 1 secs

        launch(args);
    }
}