package com.example.demo;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
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
        if (!waiting.isEmpty())
            System.out.println("test");
        Queue<Post> waitingList = this.waiting;
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
            LocalDate today = LocalDate.now();
            LocalTime time = LocalTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String postDate = String.valueOf(dateFormatter.format(today));
            String postTime = String.valueOf(timeFormatter.format(time));
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            if (post.getReply_id()>0){
                PreparedStatement statement = con.prepareStatement("SELECT * FROM submission WHERE id_sub = "+post.getReply_id());
                ResultSet res = statement.executeQuery();
                //if reply id is valid
                if (res.next()){
                    //if there is no image
                    if (post.getImageURL().equalsIgnoreCase(null)){
                        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO submission(id_reply,content,date,time,user) VALUES ("
                                +post.getReply_id()+",'"
                                +post.getContent()+"','"
                                +postDate+"','"
                                +postTime+"','"
                                +post.getUserID()+"')");
                        preparedStatement.close();
                    }
                    //if there is image
                    else {
                        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO submission(id_reply,content,image,date,time,user) VALUES ("
                                +post.getReply_id()+",'"
                                +post.getContent()+"','"
                                +post.getImageURL()+"','"
                                +postDate+"','"
                                +postTime+"','"
                                +post.getUserID()+"')");
                        preparedStatement.close();
                    }
                }
                else{
                    /**show popup saying invalid id and try again
                     * and reload main page
                     */
                }
                res.close();
                statement.close();
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

