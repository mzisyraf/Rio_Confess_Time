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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;

public class AdminPageNew implements Initializable {
    Stage stage;
    private Queue<Post> waitingList;
    Queue<Post> waiting;

    public void setWaitingList(Queue<Post> waitingList) {
        this.waitingList = waitingList;
        waiting = this.waitingList;
    }

    @FXML
    private DatePicker adminDateSearch;

    @FXML
    private Button adminDelete;

    @FXML
    private Button adminDeleteBan;

    @FXML
    private Text adminHome;

    @FXML
    private Label noDeleteId;

    @FXML
    private Label noSearchResult;

    @FXML
    private TextField adminIdDelete;

    @FXML
    private TextField adminIdSearch;

    @FXML
    private TextField adminKeywordSearch;

    @FXML
    private Text adminLogOut;

    @FXML
    private Text adminReports;

    @FXML
    private Text adminReview;

    @FXML
    private Button adminSearch;

    @FXML
    void adminDelete(ActionEvent event) {

    }

    @FXML
    void adminDeleteBanClicked(ActionEvent event) {

    }

    @FXML
    void adminHomeClicked(MouseEvent event) {

    }

    @FXML
    void adminLogOutClicked(MouseEvent event) {
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
    void adminReportsClicked(MouseEvent event) {

    }

    @FXML
    void adminReviewClicked(MouseEvent event) {

    }

    @FXML
    void adminSearchClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);
    }
}
