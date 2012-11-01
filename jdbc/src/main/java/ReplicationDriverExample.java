/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author tofarmer
 */
public class ReplicationDriverExample {
    
    /* Demo how setReadOnly() directs queries to replication slaves */
    public static void main(String args[]) {
        
        
        Connection conn = null;
        try {
            conn = DemoConstants.getConnection("jdbc:mysql:replication://localhost:3307,localhost:3308/test");
            ResultSet rs = conn.createStatement().executeQuery("SELECT @@global.port");
            rs.next();
            System.out.println("Port when read-only set to false:  " + rs.getString(1));
            rs.close();
            conn.setReadOnly(true);
            rs = conn.createStatement().executeQuery("SELECT @@global.port");
            rs.next();
            System.out.println("Port when read-only set to true:  " + rs.getString(1));
            rs.close();
            try {
                conn.createStatement().execute("DROP TABLE IF EXISTS t1");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
           
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DemoConstants.cleanUpConnection(conn);
        }
        
        
        
        
    }
    
}
