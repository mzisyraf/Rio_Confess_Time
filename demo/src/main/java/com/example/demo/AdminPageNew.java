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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    private VBox approvedConfessions;

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
    void adminDeleteClicked(ActionEvent event) {
        String delete_id = adminIdDelete.getText().trim();
        int del_id = Integer.parseInt(delete_id);
        batchremoval(del_id);
        Stage stage;
        Parent root;

        stage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("PopUpBan.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void adminDeleteBanClicked(ActionEvent event) {
        //get username
        String delete_id = adminIdDelete.getText().trim();
        int del_id = Integer.parseInt(delete_id);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();

            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission WHERE id_sub = ?");
            preparedStatement.setInt(1, del_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                noDeleteId.setOpacity(0);
                ResultSet rs = st.executeQuery("SELECT * FROM SUBMISSION where id_sub = "+ del_id +"");
                String user = "";
                while (rs.next()){
                    user = rs.getString("user");
                }
                int insert_data = st.executeUpdate("update user set class = 2 where userID = '"+user+"'");
                batchremoval(del_id);
                Stage stage;
                Parent root;

                stage = new Stage();
                try {
                    root = FXMLLoader.load(getClass().getResource("PopUpBan.fxml"));
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                noDeleteId.setOpacity(1);
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
    void adminHomeClicked(MouseEvent event) {
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
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
        Scene scene = new Scene(root, 1454, 841);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void adminReportsClicked(MouseEvent event) {
        approvedConfessions.getChildren().clear();
        waiting = new Queue<>();
        Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(waiting); // Instantiate ScheduledTask class
        time.schedule(st, 0, 1000);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where report = 1");
            while (res.next()){
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
    void adminSearchClicked(ActionEvent event) {
        String datefinal,dates;
        //get params
        String id = adminIdSearch.getText();
        if (id.equals("") || adminIdSearch.getText().isEmpty() )
            id="0";
        if (adminDateSearch.getValue()==null){
            datefinal = "";
        }
        else {
            //get date value and convert the format to dd/mm/yyyy
            dates = String.valueOf(adminDateSearch.getValue());
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
        String key = adminKeywordSearch.getText();
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
            adminKeywordSearch.setText("");
            adminIdSearch.setText("");
        }
    }

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
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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

    public boolean batchremoval(int post){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();
            ResultSet resultSet = st.executeQuery(" SELECT * FROM submission WHERE id_sub LIKE "+post+"");

            if(resultSet.next()){
                noDeleteId.setOpacity(0);
                ResultSet rs = st.executeQuery(" SELECT * FROM submission WHERE id_sub LIKE "+post+"");
                int check;
                while (rs.next()){
                    check = rs.getInt("id_sub");
                    remove(check); //call remove method
                }
            }
            else{
                noDeleteId.setOpacity(1);
            }

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
        return true;
    }

    public static int remove (int x){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();
            ResultSet resultSet = st.executeQuery(" SELECT * FROM submission WHERE id_reply LIKE "+x+"");

            if(resultSet.next()){
                ResultSet rs = st.executeQuery(" SELECT * FROM submission WHERE id_reply LIKE "+x+"");
                int check;
                while (rs.next()){
                    check = rs.getInt("id_sub");
                    if(remove(check)==1){ //call remove method
                        remove(x); //call remove method
                    }
                    else{
                        remove(check); //call remove method
                    }
                }
            }
            else{
                st.executeUpdate(" DELETE FROM submission WHERE id_sub LIKE "+x+""); //delete the entire row
                return 1;
            }

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
        return 1;
    }

    public boolean searchAB(int id, String date){
        approvedConfessions.getChildren().clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement statement =  con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM submission where id_sub = "+id+" and date = '"+date+"'");

            while (res.next()){
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
                Post confession = new Post(res.getInt(2),
                        res.getInt(1),
                        res.getString(3),
                        res.getString(7),
                        res.getString(5),
                        res.getString(6),
                        res.getByte(4));
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
}
