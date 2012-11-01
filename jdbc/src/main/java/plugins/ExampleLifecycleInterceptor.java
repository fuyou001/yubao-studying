/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package plugins;
import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */
public class ExampleLifecycleInterceptor implements com.mysql.jdbc.ConnectionLifecycleInterceptor {

    @Override
    public void close() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean commit() throws SQLException {
        return true;
    }

    @Override
    public boolean rollback() throws SQLException {
        System.out.println("Rollback called!  Stack trace:  ");
        Thread.dumpStack();
        return true;
    }

    @Override
    public boolean rollback(Savepoint svpnt) throws SQLException {
       return true;
    }

    @Override
    public boolean setAutoCommit(boolean bln) throws SQLException {
        return true;
    }

    @Override
    public boolean setCatalog(String string) throws SQLException {
        return true;
    }

    @Override
    public boolean transactionBegun() throws SQLException {
        return true;
    }

    @Override
    public boolean transactionCompleted() throws SQLException {
        return true;
    }

    @Override
    public void init(Connection cnctn, Properties prprts) throws SQLException {
        
    }

    @Override
    public void destroy() {
    }
    
}
