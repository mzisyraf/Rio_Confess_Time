package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Timer;

public class RegisterPageNew implements Initializable {
    Stage stage;

    private Queue<Post> waitingList;
    Queue<Post> waiting;

    public void setWaitingList(Queue<Post> waitingList) {
        this.waitingList = waitingList;
        waiting = this.waitingList;
    }

    @FXML
    private ImageView backToFrontPage;

    @FXML
    private TextField idRegister;

    @FXML
    private Label registerIdTaken;

    @FXML
    private TextField passwordRegister;

    @FXML
    private Button registerButton;

    @FXML
    void registerButtonClicked(ActionEvent event) {
        //get data from text fields
        String userID = idRegister.getText().trim();
        String password = passwordRegister.getText().trim();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st = con.createStatement();
            //check if email has already been registered
            ResultSet res = st.executeQuery("select * from user where userID = '"+ userID + "'");
            if (res.next()){
                //if yes then prompt that the email has been registered before and reprompt the NewRegister UI
                registerIdTaken.setOpacity(1);
                idRegister.setText("");
                passwordRegister.setText("");
            }
            else{
                //if no then insert the data into the user database table and prompt the successful registration and transfer to CustomerMain UI
                int executeUpdate = st.executeUpdate("INSERT INTO user (userID, password, class) VALUES ('" + userID + "','" + password +"', 1)");
                //System.out.println("Account successfully registered!");
                Stage stage;
                Parent root = null;

                stage = new Stage();
                try {
                    root = FXMLLoader.load(getClass().getResource("PopUpRegister.fxml"));
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //go to login page
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("LoginPageNew.fxml"));
                try {
                    root = Loader.load();
                } catch (Exception e) {
                }
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                LoginPageNew loginPageNew = Loader.getController();
                loginPageNew.setWaitingList(waiting);
                Scene scene = new Scene(root, 1454, 841);
                stage.setResizable(true);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.setTitle("Login Page");
                stage.show();
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error 1");
            System.out.println(e);
        }
        catch (SQLException e){
            System.out.println("Error 2");
            System.out.println(e);
        }
    }

    @FXML
    void backToFrontPageClicked(MouseEvent event) {
        Parent root = null;
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("FrontPageNew.fxml"));
        try {
            root = Loader.load();
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FrontPageNew frontPageNew = Loader.getController();
        frontPageNew.setWaitingList(waiting);
        Scene scene = new Scene(root, 1454, 841);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Login Page");
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
