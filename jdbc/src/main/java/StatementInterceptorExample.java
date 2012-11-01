/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */
public class StatementInterceptorExample {
    
       /* Demo StatementInterceptors return different values */
       public static void main (String[] args ){
        
        Connection conn = null;
        Properties props = new Properties();
        props.setProperty("statementInterceptors", "demo.connectorj.plugins.ExampleStatementInterceptor");
        try {
            conn = DemoConstants.getConnection(props);
            ResultSet rs = conn.createStatement().executeQuery("SELECT 1");
            rs.next();
            System.out.println("First result:  " + rs.getString(1));
            rs.close();
            rs = conn.createStatement().executeQuery("SELECT /* test */ 1");
            rs.next();
            System.out.println("Second result:  " + rs.getString(1));
            rs.close();
           
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DemoConstants.cleanUpConnection(conn);
        }
        
        
    }
}
