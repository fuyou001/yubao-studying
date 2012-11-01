/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author tofarmer
 */
public class PingExample {

    /* Demo showing that queries with ping flag use low-overhead internal 
     * COM_PING protocol.
     */
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DemoConstants.getConnection();

            System.out.println("Setting session timeout to 1 second.");
            ResultSet rs = conn.createStatement().executeQuery("SHOW SESSION STATUS LIKE 'com_select'");
            rs.next();
            System.out.println("SELECT count is: " + rs.getInt(2));
            rs = conn.createStatement().executeQuery("SHOW SESSION STATUS LIKE 'com_select'");
            rs.next();
            System.out.println("SELECT count is: " + rs.getInt(2));
            rs = conn.createStatement().executeQuery("/* ping */ SELECT 5");
            rs.next();
            System.out.println("SELECT result is (expecting 5): " + rs.getInt(1));
            rs = conn.createStatement().executeQuery("SHOW SESSION STATUS LIKE 'com_select'");
            rs.next();
            System.out.println("SELECT count is: " + rs.getInt(2));
            rs = conn.createStatement().executeQuery(" /* ping */ SELECT 6");
            rs.next();
            System.out.println("SELECT results is: " + rs.getInt(1));
            rs = conn.createStatement().executeQuery("SHOW SESSION STATUS LIKE 'com_select'");
            rs.next();
            System.out.println("SELECT count is: " + rs.getInt(2));
             

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DemoConstants.cleanUpConnection(conn);
        }

    }
}
