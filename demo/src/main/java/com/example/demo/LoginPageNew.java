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

import java.sql.*;
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
                FXMLLoader Loader = new FXMLLoader(getClass().getResource("AdminPageNew.fxml"));
                try {
                    root = FXMLLoader.load(getClass().getResource("AdminPageNew.fxml"));
                } catch (Exception e) {
                }
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1360, 695);
                stage.setResizable(true);
                stage.setMaximized(true);
                stage.setScene(scene);
                AdminPageNew adminPageNew = Loader.getController();
                adminPageNew.setWaitingList(waitingList);
                stage.setTitle("Admin Page");
                stage.show();
            }
            else {
                /**
                 * display invalid credentials
                 */
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
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("FrontPageNew.fxml"));
        try {
            root = FXMLLoader.load(getClass().getResource("FrontPageNew.fxml"));
        } catch (Exception e) {
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1920, 1080);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        FrontPageNew frontPageNew = Loader.getController();
        frontPageNew.setWaitingList(waitingList);
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void userLoginButtonClicked(ActionEvent event) {
        //check for user credentials
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT class FROM user WHERE userID = ? AND password = ?");
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
                    FXMLLoader Loader = new FXMLLoader(getClass().getResource("UserPageNew.fxml"));
                    try {
                        root = Loader.load(getClass().getResource("UserPageNew.fxml"));
                    } catch (Exception e) {
                    }
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1360, 695);
                    stage.setResizable(true);
                    stage.setMaximized(true);
                    stage.setScene(scene);
                    UserPageNew userPageNew = Loader.getController();
                    userPageNew.setUserID(String.valueOf(this.idLogin));
                    stage.setTitle("User Page");
                    stage.show();
                }
                else if(res.getInt(3) == 2){
                    /**
                     * enter  blocked user page
                     */
                    Parent root = null;
                    FXMLLoader Loader = new FXMLLoader(getClass().getResource("UserPageNew.fxml"));
                    try {
                        root = Loader.load(getClass().getResource("BlockedUserPageNew.fxml"));
                    } catch (Exception e) {
                    }
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1360, 695);
                    stage.setResizable(true);
                    stage.setMaximized(true);
                    stage.setScene(scene);
                    UserPageNew userPageNew = Loader.getController();
                    userPageNew.setUserID(String.valueOf(this.idLogin));
                    stage.setTitle("User Page");
                    stage.show();
                }

            }
            else {
                /**
                 * display invalid credentials
                 */
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

}
