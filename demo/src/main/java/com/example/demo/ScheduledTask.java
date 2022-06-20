package com.example.demo;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Queue;
import java.util.TimerTask;
import java.util.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScheduledTask extends TimerTask {

    Date now;

    private Queue<Post> waitingList;

    public void setWaitingList(Queue waitingList) {
        this.waitingList = waitingList;
    }

    @Override
    public void run() {
        Queue<Post> waitingList = this.waitingList;
        if (!waitingList.isEmpty()){
            int queueLength = waitingList.size();
            Post top = waitingList.peek();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(top.getTime(),dateTimeFormatter);
            LocalTime now = LocalTime.now();
            System.out.println(dateTimeFormatter.format(now));
            Duration diff = Duration.between(startTime,now);

            if (waitingList.size()<=5 && diff.compareTo(Duration.ofSeconds(900))>=0){
                Post push = waitingList.poll();
                submit(push);
            }

            else if (waitingList.size()<=10 && diff.compareTo(Duration.ofSeconds(600))>=0){
                Post push = waitingList.poll();
                submit(push);
            }

            else if (waitingList.size()>10 && diff.compareTo(Duration.ofSeconds(300))>=0){
                Post push = waitingList.poll();
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
}

