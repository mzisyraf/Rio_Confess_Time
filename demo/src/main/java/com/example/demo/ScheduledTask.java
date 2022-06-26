package com.example.demo;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ScheduledTask extends TimerTask implements Initializable {

    Date now;

    private Queue<Post> waitingList;
    Queue<Post> waiting;

    public ScheduledTask(Queue<Post> waitingList) {
        this.waiting = waitingList;
    }

    @Override
    public void run() {
        if (!(this.waiting.getSize()==0)){
            int queueLength = waiting.getSize();
            Post top = waiting.peek();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(top.getTime(),dateTimeFormatter);
            LocalTime now = LocalTime.now();
            System.out.println(dateTimeFormatter.format(now));
            System.out.println(waiting.getSize());
            Duration diff = Duration.between(startTime,now);

            if (queueLength<=5 && diff.compareTo(Duration.ofSeconds(900))>=0){
                Post push = waiting.dequeue();
                submit(push);
                System.out.println("success");
            }

            else if (queueLength<=10 && diff.compareTo(Duration.ofSeconds(600))>=0){
                Post push = waiting.dequeue();
                submit(push);
            }

            else if (queueLength>10 && diff.compareTo(Duration.ofSeconds(300))>=0){
                Post push = waiting.dequeue();
                submit(push);
            }
        }

    }

    public void submit(Post post){
        try{
            //get submit time
            LocalTime time = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String postTime = String.valueOf(timeFormatter.format(time));

            //get submit date
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
            String postDate = String.valueOf(dates);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st = con.createStatement();
            //if there is reply
            if (post.getReply_id()>0){
                PreparedStatement statement = con.prepareStatement("SELECT * FROM submission WHERE id_sub = "+post.getReply_id());
                ResultSet res = statement.executeQuery();
                //if reply id is valid
                if (res.next()){
                    System.out.println(post.getUserID());
                    int insert_data = st.executeUpdate("INSERT INTO submission(id_reply,content,date,time,user) VALUES ("
                            +post.getReply_id()+",'"
                            +post.getContent()+"','"
                            +postDate+"','"
                            +postTime+"','"
                            +post.getUserID()+"')");
                    System.out.println("data pushed");
                }
                res.close();
                statement.close();
            }
            //if the post is not replying to any other post
            else {
                System.out.println(post.getUserID());
                int insert_data = st.executeUpdate("INSERT INTO submission(id_reply,content,date,time,user) VALUES ("
                        +post.getReply_id()+",'"
                        +post.getContent()+"','"
                        +postDate+"','"
                        +postTime+"','"
                        +post.getUserID()+"')");
                System.out.println("data pushed");

            }

            con.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class Not Found");
            System.out.println(e);
        }
        catch (SQLException e){
            System.out.println("SQL Exception");
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waiting = new Queue<>();
    }
}

