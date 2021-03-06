/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author lenovo
 */
public class TimeTrack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Service service = new Service();
        getConnection();
        createTables();
        
        //create user
        User user1 = new User("Nina", "email", "password", 0);
        //create project
        Project myProject = new Project("second project", "also good project", "me and I", "they");
        //user creates timeframe for project 1
        user1.createTimeFrame(1);
        
        //print times from projectID
        System.out.println(
            "Time of project ID 3 " +
            service.sumTimeOfProject(3)
        );
        
        //print times from project name search
        System.out.println(
            "Times from search for project: " +
            service.sumTimeOfProject(service.searchProjectName("Total tertiary toolset"))
        );
    }

    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/timetrack";
            String username = "root";
            String password = keys.dbPassword;
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch(Exception e){System.out.println(e);}
        return null;
    }
    
    public static void createTables() throws Exception {
        int i;
        String[] tablesArray = {
            "CREATE TABLE IF NOT EXISTS users(id int NOT NULL AUTO_INCREMENT, name varchar(255), email varchar(255), password varchar(255), admin TINYINT, PRIMARY KEY(id))",
            "CREATE TABLE IF NOT EXISTS projects(id int NOT NULL AUTO_INCREMENT, name varchar(255), description varchar(255), owner varchar(255), customer varchar(255), PRIMARY KEY(id))",
            "CREATE TABLE IF NOT EXISTS timeframes (id int NOT NULL AUTO_INCREMENT, projectID INT references projects(id), userID INT references users(id), starttime varchar(255), endtime varchar(255), value INT, currency varchar(255), PRIMARY KEY(id))"
        };
        
        //loop over array, execute querys
        for(i = 0; i < tablesArray.length; i++) {
            try{
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement(tablesArray[i]);
            create.executeUpdate();
            
            } catch(Exception e) {System.out.println(e);}  
        }
    }
}