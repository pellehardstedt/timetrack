/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static timetrack.TimeTrack.getConnection;

/**
 *
 * @author lenovo
 */
public class User {
    private int ID;
    private String name;
    private String email;
    private String password;
    private int admin;
    
    public User() {   
    }
    
    public User(String name, String email, String password, int admin) throws Exception {
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
        register();
    }
    
    public void register() throws Exception {
        
        try {
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO users (name, email, password, admin) VALUES ('"+ this.name +"', '"+ this.email +"', '"+ this.password + "', '"+ this.admin + "' ) ", Statement.RETURN_GENERATED_KEYS);
            posted.executeUpdate();

            ResultSet result = posted.getGeneratedKeys();
            int generatedKey = 0;
            if(result.next()) {
                generatedKey = result.getInt(1);
            }
            this.ID = generatedKey;
        } catch(Exception e) { System.out.println(e);}
        
    }
    
    public void createProject(String name, String description, String owner, String customer) throws Exception {
        Project myProject = new Project(name, description, owner, customer);
        System.out.println("New project created with properties: " + myProject.getName() + " " + myProject.getDescription());
    }
    
    //without start and endtime
    public void createTimeFrame(int projectID) {
        TimeFrame myTimeFrame = new TimeFrame(projectID, this.getID());
        System.out.println("New time frame created with properities: " + myTimeFrame.getID() + " " + myTimeFrame.getProjectID());
    }
    
    //with start and endtime
    public void createTimeFrame(int projectID, int userID, int startTime, int endTime) {
        TimeFrame myTimeFrame = new TimeFrame(projectID, userID, startTime, endTime);
        System.out.println("New time frame created with properities: " + myTimeFrame.getID() + " " + myTimeFrame.getProjectID());
    }
    
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int isAdmin() {
        return admin;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
    
}
