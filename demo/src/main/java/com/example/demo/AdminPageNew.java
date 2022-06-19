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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Queue;

public class AdminPageNew {
    Stage stage;
    private Queue<Post> waitingList;

    public void setWaitingList(Queue waitingList) {
        this.waitingList = waitingList;
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

    @FXML
    void adminReportsClicked(MouseEvent event) {

    }

    @FXML
    void adminReviewClicked(MouseEvent event) {

    }

    @FXML
    void adminSearchClicked(ActionEvent event) {

    }

}
