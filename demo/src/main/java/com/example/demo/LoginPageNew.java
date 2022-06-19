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

public class LoginPageNew {
    Stage stage;
    private Queue<Post> waitingList;

    public void setWaitingList(Queue waitingList) {
        this.waitingList = waitingList;
    }

    @FXML
    private Button adminLoginButton;

    @FXML
    private ImageView backToFrontPage;

    @FXML
    private TextField idLogin;

    @FXML
    private TextField passwordLogin;

    @FXML
    private Button userLoginButton;

    @FXML
    void adminLoginButtonClicked(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AdminPageNew.fxml"));
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("Admin Page");
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
        stage.setTitle("Admin Page");
        stage.show();
    }

    @FXML
    void userLoginButtonClicked(ActionEvent event) {
        Parent root = null;
        FXMLLoader Loader = new FXMLLoader();
        try {
            root = Loader.load(getClass().getResource("UserPageNew.fxml"));
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        UserPageNew userPageNew = Loader.getController();
        userPageNew.setUserID(String.valueOf(this.idLogin));
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("User Page");
        stage.show();
    }

}
