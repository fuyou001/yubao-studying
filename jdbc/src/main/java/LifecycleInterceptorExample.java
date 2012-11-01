/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */
public class LifecycleInterceptorExample {
    
    /* Demonstration of LifecycleInterceptor by printing stack trace when 
       Connection.rollbach() is called */    
    public static void main (String[] args ){
        
        Connection conn = null;
        Properties props = new Properties();
        props.setProperty("connectionLifecycleInterceptors", "demo.connectorj.plugins.ExampleLifecycleInterceptor");
        try {
            conn = DemoConstants.getConnection(props);
            
            conn.setAutoCommit(false);
            conn.rollback();
            System.out.println("Demo complete!");
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DemoConstants.cleanUpConnection(conn);
        }
        
        
    }
    
    
}
