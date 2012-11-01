/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;

/**
 *
 * @author tofarmer
 */
public class CommunicationExceptionExample {

    /* Utility method to set the global server wait_timeout */
    
    private static void setGlobalWaitTimeout(int duration) {
        try {
           Connection conn = DemoConstants.getConnection();

            System.out.println("Setting global timeout to: " + duration);
            conn.createStatement().execute("SET @@global.wait_timeout=" + duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Demonstration of CommunicationTimeout caused by exceeding session wait_timeout */
    private static void demonstrateCommunicationException() {
        try {

           Connection conn = DemoConstants.getConnection();

            System.out.println("Setting session timeout to 1 seconds.");
            conn.createStatement().execute("SET @@session.wait_timeout=1");

            System.out.println("Executing long-running SELECT statement...");
            conn.createStatement().execute("SELECT SLEEP(2)");
            System.out.println("No problem!");

            System.out.println("Letting connection idle for 2 seconds...");
            Thread.sleep(2000);

            System.out.println("Executing SELECT statement...");
            conn.createStatement().execute("SELECT 1");

            System.out.println("This won't work!");

        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
    
    /* Demonstration of CommunicationTimeout message text
     * caused by exceeding server wait_timeout */
    private static void demonstrateCommunicationExceptionFromTimeout() {
        try {

            Connection conn = DemoConstants.getConnection();

            System.out.println("Letting connection idle for 2 seconds...");
            Thread.sleep(2000);

            System.out.println("Executing SELECT statement...");
            conn.createStatement().execute("SELECT 1");

            System.out.println("This won't work!");

        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    public static void main(String[] args) {
        demonstrateCommunicationException();
        setGlobalWaitTimeout(1);
        demonstrateCommunicationExceptionFromTimeout();
        setGlobalWaitTimeout(28800);
    }
}
