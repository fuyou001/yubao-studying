/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */
public class ClasspathExample {


    public static void main(String[] args) {
        try{

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DemoConstants.URL);
        
        System.out.println("Connection OK!");

        } catch (Exception e) {
            e.printStackTrace();;
        }

    }
}
