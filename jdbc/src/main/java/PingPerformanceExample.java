/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;

/**
 *
 * @author tofarmer
 */
public class PingPerformanceExample {

    private static final String GOOD_PING = "/* ping */ SELECT 1";
    private static final String BAD_PING = "SELECT 1";
    private static final int ITERATIONS = 100000;
    
    /* Demo showing difference in performance between COM_PING and simple
     * query.
     */
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DemoConstants.getConnection();
            
            long start_time = System.currentTimeMillis();
            for(int i = 0; i < ITERATIONS; i++){
                conn.createStatement().executeQuery(GOOD_PING);
            }
            long end_time = System.currentTimeMillis();
            System.out.println("Total time for " + ITERATIONS + " iterations with ping: " + (end_time - start_time));
            
            start_time = System.currentTimeMillis();
            for(int i = 0; i < ITERATIONS; i++){
                conn.createStatement().executeQuery(BAD_PING);
            }
            end_time = System.currentTimeMillis();
            System.out.println("Total time for " + ITERATIONS + " iterations without ping: " + (end_time - start_time));

             

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DemoConstants.cleanUpConnection(conn);
        }
    }
}
