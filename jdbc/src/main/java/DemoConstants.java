/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */
public class DemoConstants {

    public static final String URL = "jdbc:mysql://localhost:3306/fuyou";
    public static final String USER = "root";
    public static final String PASS = "123456";

    public static final Connection getConnection() throws SQLException {
        
        Properties props = new Properties();
        props.setProperty("user", DemoConstants.USER);
        props.setProperty("password", DemoConstants.PASS);

        return getConnection(props);
    }

    public static final Connection getConnection(Properties p) throws SQLException {
        return getConnection(DemoConstants.URL, p);
    }
    
    public static final Connection getConnection(String url, Properties p) throws SQLException{
        if(p == null) {
            p = new Properties();
        }
        if(!p.containsKey("user")) {
            p.setProperty("user", DemoConstants.USER);
        }
        if(!p.containsKey("password")) {
            p.setProperty("password", DemoConstants.PASS);
        }
        
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            throw new SQLException("Unable to find driver");
        }
        return DriverManager.getConnection(url, p);
       
    }
    
    public static final Connection getConnection(String url) throws SQLException {
        return getConnection(url, null);
    }
    
    public static final void cleanUpConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {}
        }
    }
}
