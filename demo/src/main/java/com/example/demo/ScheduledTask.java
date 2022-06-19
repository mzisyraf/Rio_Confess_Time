package com.example.demo;

import java.sql.*;
import java.time.Duration;
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

            if (waitingList.size()<=5 && diff.compareTo(Duration.ofSeconds(900))==1){

            }
        }

    }

    public void submit(Post post){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confession_time", "root", "root");
            Statement st =  con.createStatement();


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
}

