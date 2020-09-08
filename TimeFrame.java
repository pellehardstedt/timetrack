/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetrack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static timetrack.TimeTrack.getConnection;

/**
 *
 * @author lenovo
 */
public class TimeFrame {
    int ID;
    int projectID;
    int userID;
    int startTime;
    int endTime;
    int value;
    String currency;
    
    //constructor without start and endtime
    public TimeFrame(int projectID, int userID) {
        this.projectID = projectID;
        this.userID = userID;
        register();
    }
    
    //WITH start and endtime
    public TimeFrame(int projectID, int userID, int startTime, int endTime) {
        this.projectID = projectID;
        this.userID = userID;
        register();
    }

    public void register() {
        try {
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO timeframes (projectID, userID) VALUES ('"+ this.projectID +"', '"+ this.userID +"') ", Statement.RETURN_GENERATED_KEYS);
            posted.executeUpdate();

            ResultSet result = posted.getGeneratedKeys();
            int generatedKey = 0;
            if(result.next()) {
                generatedKey = result.getInt(1);
            }
            this.ID = generatedKey;
        } catch(Exception e) { System.out.println(e);}
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    
}
