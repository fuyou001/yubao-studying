/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */
public class AutoReconnectExample {

    public static void main(String[] args) {
        demonstrateRetryLogicSameStatement();
        demonstrateRetryLogicPreparedStatement(false);
        demonstrateRetryLogicPreparedStatement(true);


    }

    private static void demonstrateRetryLogicPreparedStatement(boolean serverSide) {
        Connection conn = null;
        try {

            System.out.println("Demonstration of timeout when using PreparedStatment with server-side set to: " + serverSide);

            Properties props = new Properties();
            props.setProperty("autoReconnect", "true");
            props.setProperty("useServerPrepStmts", new Boolean(serverSide).toString());

            conn = DemoConstants.getConnection(props);
            PreparedStatement stmt = conn.prepareStatement("SELECT ?");
            stmt.setInt(1, 1);

            System.out.println("Setting session timeout to 1 second.");
            conn.createStatement().execute("SET @@session.wait_timeout=1");

            System.out.println("Letting connection idle for 2 seconds...");
            Thread.sleep(2000);

            System.out.println("Executing SELECT statement...");


            try {
                stmt.executeQuery();
            } catch (SQLException e) {
                System.out.println("Caught Exception:  " + e.getMessage());
                ResultSet rs = stmt.executeQuery();
                rs.next();
                System.out.println("Result:  " + rs.getInt(1));
            } finally {
                DemoConstants.cleanUpConnection(conn);
            }

            System.out.println("Finished!");

        } catch (Exception e) {
            e.printStackTrace();;
        }

    }

    private static void demonstrateRetryLogicSameStatement() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Properties props = new Properties();
            props.setProperty("autoReconnect", "true");
            Connection conn = DriverManager.getConnection(DemoConstants.URL, props);
            Statement stmt = conn.createStatement();

            System.out.println("Starting demonstration of auto-reconnect using same statement.");
            System.out.println("Setting session timeout to 1 second.");
            conn.createStatement().execute("SET @@session.wait_timeout=1");

            System.out.println("Letting connection idle for 2 seconds...");
            Thread.sleep(2000);

            System.out.println("Executing SELECT statement...");
            try {
                stmt.execute("SELECT 1");
            } catch (SQLException e) {
                System.out.println("Caught Exception:  " + e.getMessage());
                System.out.println("About to retry using same Statement object.");
                stmt.execute("SELECT 2");
                System.out.println("No problem here!");
            }

            System.out.println("Finished!");

        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
}
