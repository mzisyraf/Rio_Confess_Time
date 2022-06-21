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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;

public class LoginPageNew implements Initializable {
    Stage stage;
    private Queue<Post> waitingList;
    Queue<Post> waiting;

    public void setWaitingList(Queue<Post> waitingList) {
        this.waitingList = waitingList;
        waiting = this.waitingList;
    }

    @FXML
    private Button adminLoginButton;

    @FXML
    private Label loginErrorBanner;

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
        //check for admin credentials
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st = con.createStatement();
            //check if credentials are valid
            ResultSet res = st.executeQuery("SELECT * FROM user where userID='"
                            +String.valueOf(this.idLogin)+"' AND password = '"
                            +String.valueOf(this.passwordLogin)+"' AND class = 0");
            if (res.next()){
                /**
                 * enter admin page
                 */
                Parent root = null;
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("AdminPageNew.fxml"));
                try {
                    root = Loader.load();
                } catch (Exception e) {
                }
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                AdminPageNew adminPageNew = Loader.getController();
                adminPageNew.setWaitingList(waiting);
                Scene scene = new Scene(root, 1360, 695);
                stage.setResizable(true);
                stage.setMaximized(true);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.setTitle("Admin Page");
                stage.show();
            }
            else {
                loginErrorBanner.setOpacity(1);
                idLogin.setText("");
                passwordLogin.setText("");
            }
            res.close();;
            st.close();
            con.close();
        }

        catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println(e);
        }

        catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception");
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
        Scene scene = new Scene(root, 1360, 695);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void userLoginButtonClicked(ActionEvent event) {
        //check for user credentials
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user WHERE userID = ? AND password = ?");
            preparedStatement.setString(1, idLogin.getText().trim());
            preparedStatement.setString(2, passwordLogin.getText().trim());
            //check if credentials are valid
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()){
                //Assume size of rs is 1.
                if(res.getInt(3) == 1){
                    /**
                     * enter  normal user page
                     */
                    Parent root = null;
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("UserPageNew.fxml"));
                    try {
                        root = Loader.load();
                    } catch (Exception e) {
                    }
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    UserPageNew userPageNew = Loader.getController();
                    userPageNew.setUserID(String.valueOf(this.idLogin));
                    userPageNew.setWaitingList(waiting);
                    Scene scene = new Scene(root, 1360, 695);
                    stage.setResizable(true);
                    stage.setMaximized(true);
                    stage.setFullScreen(true);
                    stage.setScene(scene);
                    stage.setTitle("User Page");
                    stage.show();
                }
                else if(res.getInt(3) == 2){
                    /**
                     * enter  blocked user page
                     */
                    Parent root = null;
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("UserPageNew.fxml"));
                    try {
                        root = Loader.load();
                    } catch (Exception e) {
                    }
                    BlockedUserPageNew blockedUserPageNew = Loader.getController();
                    blockedUserPageNew.setUserID(String.valueOf(idLogin));
                    blockedUserPageNew.setWaitingList(waiting);
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1360, 695);
                    stage.setResizable(true);
                    stage.setMaximized(true);
                    stage.setFullScreen(true);
                    stage.setScene(scene);
                    stage.setTitle("User Page");
                    stage.show();
                }

            }
            else {
                idLogin.setText("");
                passwordLogin.setText("");
                loginErrorBanner.setOpacity(1);
            }
            res.close();;
            preparedStatement.close();
            con.close();
        }

        catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println(e);
        }

        catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception");
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);
    }
}
