package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class UserPageNew {
    private String userID, imageURL;
    private Queue<Post> waitingList;

    public void setWaitingList(Queue waitingList) {
        this.waitingList = waitingList;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    Stage stage;

    @FXML
    private VBox approvedConfessions;


    @FXML
    private Button imageButton;

    @FXML
    private ScrollPane approvedConfessionsScroll;

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

    @FXML
    void imageButtonClicked(ActionEvent event) {
        imageButton.setOnAction(addImageButtonListener);
    }
    @FXML
    void userHomeClicked(MouseEvent event) {

    }

    @FXML
    void userLogOutClicked(MouseEvent event) {
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
    void userReportClicked(ActionEvent event) {

    }

    @FXML
    void userSearchClicked(ActionEvent event) {

    }

    @FXML
    void userSubmitConfessionClicked(ActionEvent event) {

        //get inputs
        String id_reply = String.valueOf(this.replyID);
        int id_rep = Integer.parseInt(id_reply);
        String content = String.valueOf(this.userPostConfession);
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

        //create Post object
        Post newConfession = new Post(content , userID, String.valueOf(times), id_rep);

    }

    public static boolean searchid(){
        Scanner z = new Scanner (System.in);
        System.out.print("Please enter the id you wanna search: ");
        int id_sub = z.nextInt(); //tukar jd getString, casting jadi integer then baru check in database

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();

            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission WHERE id_sub = ?");
            preparedStatement.setInt(1, id_sub);
            ResultSet resultSet = preparedStatement.executeQuery();

            //if id exist, akan masuk if statement, kalau takde masuk else statement
            if(resultSet.next()){
                ResultSet rs = st.executeQuery("SELECT * FROM SUBMISSION where id_sub = "+ id_sub +"");
                String r1 = "";
                while (rs.next()){
                    //this part untuk create post based on id yang dicari
                    r1 = rs.getString("content"); //change to post object, can store in arraylist the object, then display all
                    break;
                }
                //print the post in interface
                System.out.println(r1);
            }
            //change to pop up
            else{
                System.out.println("The post you wanna find is not available."); //prompt if not available, post.setText("This room is unavailable at the selected time!");
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
        return true;
    }

//    public boolean submit(int id_rep, String content, String date){
//        Scanner z = new Scanner (System.in);
//        int checking = 0;
//        String lol = "null";
//        String image = z.nextLine(); //KIV, nak pakai code mai untuk auto catch URL
//        //change the format of date to dd/mm/yyyy
//        String[] newdate = date.split("-");
//        StringBuilder dates = new StringBuilder();
//        for(int i = newdate.length-1; i>=0; i--){
//            dates.append(newdate[i]);
//            if(i==0)
//                break;
//            dates.append("/");
//        }
//
//        String time = java.time.LocalTime.now().toString(); //get localtime in string
//        //change the format of time to hh:mm
//        String[] newtime = time.split("[:.]");
//        StringBuilder times = new StringBuilder();
//        times.append(newtime[0]);
//        times.append(":");
//        times.append(newtime[1]);
//
//        //create Post object
//        if (String.valueOf(id_rep).compareTo(lol)==0){
//            Post newConfession = new Post(content , userID, String.valueOf(times), this.replyID);
//        }
//        else{
//            Post newConfession = new Post(content , userID, String.valueOf(times),this.replyID);
//        }
//
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
//            Statement st =  con.createStatement();
//
//            if (image.equalsIgnoreCase(lol)){ //if no image is insert, then it will insert id_reply and content only
//                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission WHERE id_sub = ?");
//                preparedStatement.setInt(1, id_rep);
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                //if id_sub is valid, id_reply = id_sub that want to reply
//                if(resultSet.next()){
//                    int insert_data = st.executeUpdate("INSERT INTO submission (id_reply, content, date, time) VALUES (" + id_rep + ",'" +content+ "','" +dates+ "','" +times+ "');");
//                }
//                //if id_sub is not valid, id_reply = 0
//                else{
//                    id_rep = 0;
//                    int insert_data = st.executeUpdate("INSERT INTO submission (id_reply, content, date, time) VALUES (" + id_rep + ",'" +content+"','" +dates+ "','" +times+ "');");
//                }
//
//                preparedStatement.close();
//                resultSet.close();
//                con.close();
//            }
//
//            // if image is inserted, then it will sent 3 data (id reply, content and image) , need to think about the ways to detect the directory is valid
//            else {
//                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission WHERE id_sub = ?");
//                preparedStatement.setInt(1, id_rep);
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                if(resultSet.next()){
//                    int insert_data = st.executeUpdate("INSERT INTO submission (id_reply, content, image) VALUES (" + id_rep + ",'" +content+"', '" +image+ "','" +dates+ "','" +times+ "');");
//                }
//                else{
//                    id_rep = 0;
//                    int insert_data = st.executeUpdate("INSERT INTO submission (id_reply, content, image) VALUES (" + id_rep + ",'" +content+"', '" +image+ "','" +dates+ "','" +times+ "');");
//                }
//
//                preparedStatement.close();
//                resultSet.close();
//                con.close();
//            }
//
//        }
//        catch (ClassNotFoundException e) {
//            System.out.println("Error 1");
//            System.out.println(e);
//        }
//        catch (SQLException e){
//            System.out.println("Error 2");
//            System.out.println(e);
//        }
//        return true;
//    }

    public static boolean searchdate(){
        Scanner z = new Scanner (System.in);
        System.out.print("Please enter the date of the posts you wanna see (in format dd/mm/yyy): ");
        String date = z.nextLine(); //get string date from calendar in UI

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();

            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM submission WHERE date = ?");
            preparedStatement.setString(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();

            //if the date is valid
            if(resultSet.next()){
                ResultSet rs = st.executeQuery("SELECT * FROM SUBMISSION where date = '"+ date +"'");
                String r1 = ""; //store each post in string, need to change based on UI
                //while loop for display all post that having the same date as input
                while (rs.next()){
                    r1 = rs.getString("content"); //change to post object, can store in arraylist the object, then display all
                    System.out.println(r1);
                }
            }
            else{
                System.out.println("The post you wanna find is not available."); //prompt if not available, post.setText("no post on that day");
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
        return true;
    }

    public static boolean searchkeyword(){
        Scanner z = new Scanner (System.in);
        System.out.print("Please enter the key you wanna search: ");
        String key = z.nextLine(); //get the keyword from UI

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();
            ResultSet resultSet = st.executeQuery(" SELECT * FROM confession.submission WHERE content LIKE '%"+key+"%'");

            //if any keyword or char detected in content
            if(resultSet.next()){
                ResultSet rs = st.executeQuery(" SELECT * FROM confession.submission WHERE content LIKE '%"+key+"%'");
                String r1 = ""; //change to post object, can store in arraylist the object, then display all
                while (rs.next()){
                    r1 = rs.getString("content");
                    System.out.println(r1);
                }
            }
            else{
                System.out.println("The post you wanna find is not available."); //prompt if not available, post.setText("This room is unavailable at the selected time!");
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

    public static boolean batchremoval(){
        Scanner z = new Scanner (System.in);
        System.out.print("Please enter id post you wanna delete: ");
        int post = z.nextInt();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();
            ResultSet resultSet = st.executeQuery(" SELECT * FROM confession.submission WHERE id_sub LIKE "+post+"");

            if(resultSet.next()){
                ResultSet rs = st.executeQuery(" SELECT * FROM confession.submission WHERE id_sub LIKE "+post+"");
                int check;
                while (rs.next()){
                    check = rs.getInt("id_sub");
                    remove(check); //call remove method
                }
            }
            else{
                System.out.println("The post you wanna find and delete is not available."); //prompt if not available, post.setText("This room is unavailable at the selected time!");
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
            ResultSet resultSet = st.executeQuery(" SELECT * FROM confession.submission WHERE id_reply LIKE "+x+"");

            if(resultSet.next()){
                ResultSet rs = st.executeQuery(" SELECT * FROM confession.submission WHERE id_reply LIKE "+x+"");
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
                st.executeUpdate(" DELETE FROM confession.submission WHERE id_sub LIKE "+x+""); //delete the entire row
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

    final FileChooser fileChooser = new FileChooser();
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
            imageURL = url;
        }};

}
