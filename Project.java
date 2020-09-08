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
public class Project {
    private int ID;
    private String name;
    private String description;
    private String owner;
    private String customer;
    
    public Project(String name, String description, String owner, String customer) throws Exception {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.customer = customer;
        this.ID = getIdFromDb();
        register();
    }
    
    public void register() throws Exception {
        
        try {
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO projects (name, description, owner, customer) VALUES ('"+ this.name +"', '"+ this.description +"', '"+ this.owner + "', '"+ this.customer + "' ) ", Statement.RETURN_GENERATED_KEYS);
            posted.executeUpdate();

            ResultSet result = posted.getGeneratedKeys();
            int generatedKey = 0;
            if(result.next()) {
                generatedKey = result.getInt(1);
            }
            this.ID = generatedKey;
        } catch(Exception e) { System.out.println(e);}
        
    }

    public int getIdFromDb() {
        return 0;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    
}
