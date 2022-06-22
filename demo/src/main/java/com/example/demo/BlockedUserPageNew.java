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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    private Text blockedUserPosts;

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
    private VBox approvedConfessions;

    @FXML
    void blockedUserPostsClicked(MouseEvent event) {

    }

    @FXML
    void blockedUserHomeClicked(MouseEvent event) {
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
    void blockedUserInsertImageClicked(MouseEvent event) {

    }

    @FXML
    void imageButtonClicked(ActionEvent event){

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
        Scene scene = new Scene(root, 1454, 841);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void blockedUserReportClicked(ActionEvent event) {

        String id_rep = this.blockedUserIdReport.getText();
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
    void blockedUserSearchClicked(ActionEvent event) {
        String datefinal,dates;
        //get params
        String id = blockedUserIdSearch.getText();
        if (id.equals("") || blockedUserIdSearch.getText().isEmpty() )
            id="0";
        if (blockedUserDateSearch.getValue()==null){
            datefinal = "";
        }
        else {
            //get date value and convert the format to dd/mm/yyyy
            dates = String.valueOf(blockedUserDateSearch.getValue());
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
        String key = blockedUserKeywordSearch.getText();
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
            blockedUserKeywordSearch.setText("");
            blockedUserIdSearch.setText("");
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

    @FXML
    void blockedUserSubmitConfessionClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        blockedUserPostConfession.setEditable(false);
        blockedUserIdConfession.setEditable(false);
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
}
