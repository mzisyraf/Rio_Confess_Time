package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;

public class BlockedUserPageNew implements Initializable {
    Stage stage;
    private Queue<Post> waitingList;
    Queue<Post> waiting;
    private String userID;

    public void setWaitingList(Queue<Post> waitingList) {
        this.waitingList = waitingList;
        waiting = this.waitingList;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @FXML
    private DatePicker blockedUserDateSearch;

    @FXML
    private Text blockedUserHome;

    @FXML
    private TextField blockedUserIdConfession;

    @FXML
    private TextField blockedUserIdReport;

    @FXML
    private TextField blockedUserIdSearch;

    @FXML
    private ImageView blockedUserInsertImage;

    @FXML
    private TextField blockedUserKeywordSearch;

    @FXML
    private Label noReportId;

    @FXML
    private Label noSearchResult;

    @FXML
    private Text blockedUserLogOut;

    @FXML
    private TextField blockedUserPostConfession;

    @FXML
    private Button blockedUserReport;

    @FXML
    private Button blockedUserSearch;

    @FXML
    private Button blockedUserSubmitConfession;

    @FXML
    void blockedUserHomeClicked(MouseEvent event) {

    }

    @FXML
    void blockedUserInsertImageClicked(MouseEvent event) {

    }

    @FXML
    void blockedUserLogOutClicked(MouseEvent event) {
        Parent root = null;
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("FrontPageNew.fxml"));
        try {
            root = Loader.load();
        } catch (Exception e) {
        }
        FrontPageNew frontPageNew = Loader.getController();
        frontPageNew.setWaitingList(waiting);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void blockedUserReportClicked(ActionEvent event) {

    }

    @FXML
    void blockedUserSearchClicked(ActionEvent event) {

    }

    @FXML
    void blockedUserSubmitConfessionClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);
    }
}
