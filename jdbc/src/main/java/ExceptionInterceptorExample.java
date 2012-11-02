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
public class ExceptionInterceptorExample {

        /* Demonstration of ExceptionInterceptor by changing SQLException
           triggered by invalid SQL syntax */
        public static void main(String[] args) {
        Connection conn = null;
        try {

            System.out.println("Demonstration of exception handling.");

            Properties props = new Properties();
            props.setProperty("exceptionInterceptors", "plugins.ExampleExceptionInterceptor");

            conn = DemoConstants.getConnection(props);

            try {
                System.out.println("Executing command with bad syntax...");
                conn.createStatement().execute("NO SUCH SQL COMMAND");
            } catch (SQLException e) {
                System.out.println("Caught Exception:  " + e.getMessage());
            } finally {
                DemoConstants.cleanUpConnection(conn);
            }

            System.out.println("Finished!");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
