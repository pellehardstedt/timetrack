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
import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
public class Service {
    
    public Service() {
        
    }
    
    //search with a string
    //need to handle date format and sum the times
    public String searchAndGetSum(String searchName){
        sumTimeOfProject(searchProjectName(searchName));
        return "";
    }
    
    //search project with ID, returns an ArrayList with times in strings
    public ArrayList<String> sumTimeOfProject(int projectID) {
        ArrayList<String> resultArray = new ArrayList<String>();
        //our query, plus projectID converted to string
        String query = "SELECT TIMEDIFF(endTime, startTime) FROM timeframes WHERE projectID = " + String.valueOf(projectID);
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            
            ResultSet result = statement.executeQuery();
            while(result.next()){
                resultArray.add(result.getString("TIMEDIFF(endTime, startTime)"));
                //System.out.println(result.getString("TIMEDIFF(endTime, startTime)"));
            }
        } catch(Exception e) { System.out.println(e);}
        return resultArray;
    }
    
    //find project by name, returns projectID
    public int searchProjectName(String searchProjectName) {
        String query = "SELECT id FROM projects WHERE name = '" + searchProjectName + "'";
        int returnResult;
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("id");
            
        } catch(Exception e) {System.out.println(e);}
        
       
        return 0;
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
            finally{System.out.println("Function complete, table created if it didnt already exist");};   
        }
    }
    
}
