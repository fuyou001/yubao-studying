/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */

/* Demonstration of live manipulation of load-balanced hosts, start with 
   -Dcom.sun.management.jmxremote JVM flag and start jconsole */
    
public class LoadBalanceHostManagementExample {

    private static final Properties props = new Properties();
    private static String URL = "jdbc:mysql:loadbalance://"
            + "localhost:3307/test?"
            + "loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true";


    public static void main(String[] args) throws Exception {
        new Thread(new Repeater()).start();
        new Thread(new Repeater()).start();
        new Thread(new Repeater()).start();
    }

    static Connection getNewConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(URL, "root", "");
    }

    static void executeSimpleTransaction(Connection c, int conn, int trans) {
        try {
            c.setAutoCommit(false);
            Statement s = c.createStatement();
            s.executeQuery("SELECT SLEEP(1) /* Connection: " + conn + ", transaction: " + trans + " */");
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Repeater implements Runnable {

        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Connection c = getNewConnection();
                    for (int j = 0; j < 10; j++) {
                        executeSimpleTransaction(c, i, j);
                        Thread.sleep(Math.round(100 * Math.random()));
                    }
                    c.close();
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}