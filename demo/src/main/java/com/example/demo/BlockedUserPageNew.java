package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Queue;

public class BlockedUserPageNew {
    Stage stage;
    private Queue waitingList;

    public void setWaitingList(Queue waitingList) {
        this.waitingList = waitingList;
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
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("FrontPageNew.fxml"));
        try {
            root = FXMLLoader.load(getClass().getResource("FrontPageNew.fxml"));
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setMaximized(true);
        FrontPageNew frontPageNew = Loader.getController();
        frontPageNew.setWaitingList(waitingList);
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

}
