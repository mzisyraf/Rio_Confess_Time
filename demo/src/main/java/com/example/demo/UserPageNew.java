package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserPageNew implements Initializable {
    private String userID, imageURL = "0";
    private Queue<Post> waitingList;
    Queue<Post> waiting;

    Stage stage;

    @FXML
    private VBox approvedConfessions;

    @FXML
    private Label noReportId;

    @FXML
    private Label noSearchResult;

    @FXML
    private Button imageButton;

    @FXML
    private ScrollPane approvedConfessionsScroll;

    @FXML
    private Text userPosts;

    @FXML
    private DatePicker userDateSearch;

    @FXML
    private Text userHome;

    @FXML
    private TextField userIdReport;

    @FXML
    private TextField userIdSearch;

    @FXML
    private ImageView userInsertImage;

    @FXML
    private TextField userKeywordSearch;

    @FXML
    private Text userLogOut;

    @FXML
    private TextField userPostConfession;

    @FXML
    private Button userReport;

    @FXML
    private Button userSearch;

    @FXML
    private Button userSubmitConfession;

    @FXML
    private TextField replyID;

    public void setWaitingList(Queue waitingList) {
        this.waitingList = waitingList;
        waiting = this.waitingList;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    @FXML
    void imageButtonClicked(ActionEvent event) {
        imageButton.setOnAction(addImageButtonListener);
    }
    @FXML
    void userHomeClicked(MouseEvent event) {
        approvedConfessions.getChildren().clear();
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission");
            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
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
    void userPostsClicked(MouseEvent event) {
        approvedConfessions.getChildren().clear();
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where user = '"+userID+"'");
            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
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
    void userLogOutClicked(MouseEvent event) {
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

    @FXML
    void userReportClicked(ActionEvent event) {
        String id_rep = this.userIdReport.getText();
        int id_report = Integer.parseInt(id_rep);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();

            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission WHERE id_sub = ?");
            preparedStatement.setInt(1, id_report);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                ResultSet rs = st.executeQuery("SELECT * FROM SUBMISSION where id_sub = "+ id_report +"");
                while (rs.next()){
                    int insert_data = st.executeUpdate("UPDATE SUBMISSION set report = 1 where id_sub = "+id_report+"");
                }
            }
            else{
                Stage stage;
                Parent root;

                stage = new Stage();
                try {
                    root = FXMLLoader.load(getClass().getResource("PopUp.fxml"));
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            preparedStatement.close();
            resultSet.close();
            con.close();

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
    void userSearchClicked(ActionEvent event) {
        String datefinal,dates;
        //get params
        String id = userIdSearch.getText();
        if (id.equals("") || userIdSearch.getText().isEmpty() )
            id="0";
        if (userDateSearch.getValue()==null){
            datefinal = "";
        }
        else {
            //get date value and convert the format to dd/mm/yyyy
            dates = String.valueOf(userDateSearch.getValue());
            String[] newdate = dates.split("-");
            StringBuilder date = new StringBuilder();
            for(int i = newdate.length-1; i>=0; i--) {
                date.append(newdate[i]);
                if (i == 0)
                    break;
                date.append("/");
            }
            datefinal=String.valueOf(date);
        }
        int id_sub = Integer.parseInt(id);
        //get keyword search
        String key = userKeywordSearch.getText();
        if (id_sub==0 && searchBC(datefinal,key))
            searchBC(datefinal,key);
        else if (key.equalsIgnoreCase("") && searchAB(id_sub,datefinal))
            searchAB(id_sub,datefinal);
        else if (datefinal.equalsIgnoreCase("") &&  searchAC(id_sub,key))
            searchAC(id_sub,key);
        else if (id_sub==0 && key.equalsIgnoreCase("") && searchdate(datefinal))
            searchdate(datefinal);
        else if (id_sub==0 && datefinal.equalsIgnoreCase("") && searchkeyword(key))
            searchkeyword(key);
        else if (datefinal.equalsIgnoreCase("") && key.equalsIgnoreCase("") && searchid(id_sub))
            searchid(id_sub);
        else if (!(datefinal.equalsIgnoreCase("")) && !(key.equalsIgnoreCase("")) && !(id_sub==0) && searchABC(id_sub,datefinal,key))
            searchABC(id_sub,datefinal,key);
        else{
            noSearchResult.setOpacity(1);
            userKeywordSearch.setText("");
            userIdSearch.setText("");
        }
    }

    @FXML
    void userSubmitConfessionClicked(ActionEvent event) {
        //initialise image variable
        imageURL = null;
        int id_rep = 0;
        //get inputs
        String id_reply = this.replyID.getText();
        if (!(this.replyID.getText().trim().equalsIgnoreCase(""))) {
            id_rep = Integer.parseInt(id_reply);
        }
        String content = this.userPostConfession.getText();
        String date = java.time.LocalDate.now().toString();

        //change the format of date to dd/mm/yyyy
        String[] newdate = date.split("-");
        StringBuilder dates = new StringBuilder();
        for(int i = newdate.length-1; i>=0; i--){
            dates.append(newdate[i]);
            if(i==0)
                break;
            dates.append("/");
        }

        //get localtime in string
        String time = java.time.LocalTime.now().toString();
        //change the format of time to hh:mm
        String[] newtime = time.split("[:.]");
        StringBuilder times = new StringBuilder();
        times.append(newtime[0]);
        times.append(":");
        times.append(newtime[1]);
        System.out.println(String.valueOf(times));

        //set id reply to 0 if null
        if (this.replyID.getText().isEmpty())
            id_rep = 0;

        //check for valid reply id
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();
            //if no id reply
            if (id_rep==0){
                //create Post object and push into queue
                Post newConfession = new Post(id_rep,content,imageURL,dates.toString(),times.toString(),this.userID);



                if (spamcheck(newConfession)){
                    Stage stage;
                    Parent root;
                    userPostConfession.setText("");
                    replyID.setText("");
                    System.out.println(waiting.toString());
                    stage = new Stage();
                    try {
                        root = FXMLLoader.load(getClass().getResource("PopUpReject.fxml"));
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    waiting.enqueue(newConfession);
                    System.out.println(waiting.toString());
                    userPostConfession.setText("");
                    replyID.setText("");
                    Stage stage;
                    Parent root;

                    stage = new Stage();
                    try {
                        root = FXMLLoader.load(getClass().getResource("PopUpSubmit.fxml"));
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            //if there is id reply
            else {
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission WHERE id_sub = ?");
                preparedStatement.setInt(1,id_rep );
                ResultSet resultSet = preparedStatement.executeQuery();
                //if the reply id is valid
                if (resultSet.next()){
                    Post newConfession = new Post(id_rep,content,imageURL,dates.toString(),times.toString(),this.userID);
                    if (spamcheck(newConfession)){
                        Stage stage;
                        Parent root;
                        userPostConfession.setText("");
                        replyID.setText("");
                        System.out.println(waiting.toString());
                        stage = new Stage();
                        try {
                            root = FXMLLoader.load(getClass().getResource("PopUpReject.fxml"));
                            stage.setScene(new Scene(root));
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.showAndWait();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        waiting.enqueue(newConfession);
                        System.out.println(waiting.toString());
                        userPostConfession.setText("");
                        replyID.setText("");
                        Stage stage;
                        Parent root;

                        stage = new Stage();
                        try {
                            root = FXMLLoader.load(getClass().getResource("PopUpSubmit.fxml"));
                            stage.setScene(new Scene(root));
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.showAndWait();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                //if reply id is not valid
                else {
                    Stage stage;
                    Parent root;

                    stage = new Stage();
                    try {
                        root = FXMLLoader.load(getClass().getResource("PopUp.fxml"));
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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
    public boolean searchAB(int id, String date){
        approvedConfessions.getChildren().clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where id_sub = "+id+" and date = '"+date+"'");

            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else{
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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
        return true;
    }
    public boolean searchAC(int id, String key){
        approvedConfessions.getChildren().clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where id_sub = "+id+" and content LIKE '%"+key+"%'");

            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else{
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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
        return true;
    }
    public boolean searchBC(String date, String key){
        approvedConfessions.getChildren().clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where date = '"+date+"' and content LIKE '%"+key+"%'");

            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else{
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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
        return true;
    }
    public boolean searchABC(int id, String date, String key){
        approvedConfessions.getChildren().clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where id_sub = "+id+" and date = '"+date+"' and content LIKE '%"+key+"%'");

            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else{
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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
        return true;
    }

    public boolean searchid(int x){
        approvedConfessions.getChildren().clear();
        int id_sub = x;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where id_sub = "+id_sub+"");

            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else{
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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
        return true;
    }

    public boolean searchdate(String x){
        approvedConfessions.getChildren().clear();
        String date = x;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where date= '"+date+"'");
            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
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
        return true;
    }

    public boolean searchkeyword(String x){
        approvedConfessions.getChildren().clear();
        String key = x;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery(" SELECT * FROM submission WHERE content LIKE '%"+key+"%'");
            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
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
        return true;
    }


    EventHandler<ActionEvent> addImageButtonListener= new EventHandler<ActionEvent>(){

        public void handle(ActionEvent actionEvent) {
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG =
                    new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
            FileChooser.ExtensionFilter extFilterjpg =
                    new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPNG =
                    new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
            FileChooser.ExtensionFilter extFilterpng =
                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
            fileChooser.getExtensionFilters()
                    .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);


            String url=file.getAbsolutePath();
            System.out.println(url);
            imageURL = url;
        }};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission");
            while (res.next()){
                Post confession = new Post(res.getInt(2),res.getInt(1),res.getString(3),res.getString(7),res.getString(5),res.getString(6),res.getByte(4));
                if (res.getBytes(4)==null){
                    try {
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PostOnly.fxml"));
                        VBox vBox;
                        vBox = Loader.load();
                        PostOnly postOnly = Loader.getController();
                        postOnly.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("PostPicture.fxml"));
                        VBox vBox;
                        vBox = fxmlLoader.load();
                        PostPicture postPicture = fxmlLoader.getController();
                        postPicture.setData(confession);
                        approvedConfessions.getChildren().add(vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
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

    public boolean spamcheck(Post check){
        int queueLength = waiting.getSize();
        for (int i=0 ; i<queueLength ; i++){
            Post post = waiting.getElement(i);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime oldPost = LocalTime.parse(post.getTime(),dateTimeFormatter);
            LocalTime newPost = LocalTime.parse(check.getTime(),dateTimeFormatter);
//            System.out.println(dateTimeFormatter.format(now));
//            System.out.println(waiting.getSize());
            Duration diff = Duration.between(oldPost,newPost);
            if ((check.getContent().equalsIgnoreCase(post.getContent())) && (diff.compareTo(Duration.ofSeconds(60))<=0) && (post.getUserID().compareTo(check.getUserID())==0))
                return true;
        }
             return false;
    }
}
