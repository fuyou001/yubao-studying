/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plugins;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */
public class ExampleStatementInterceptor implements com.mysql.jdbc.StatementInterceptorV2 {

    @Override
    public void init(Connection cnctn, Properties prprts) throws SQLException {
        
    }

    @Override
    public ResultSetInternalMethods preProcess(String string, Statement stmnt, Connection cnctn) throws SQLException {
        if(string.contains("/* test */")) {
            return (ResultSetInternalMethods) cnctn.createStatement().executeQuery("SELECT NOW()");
        }
        return null;
        
        
    }

    @Override
    public boolean executeTopLevelOnly() {
        return false;
    }

    @Override
    public void destroy() {
       
    }

    @Override
    public ResultSetInternalMethods postProcess(String string, Statement stmnt, ResultSetInternalMethods rsim, Connection cnctn, int i, boolean bln, boolean bln1, SQLException sqle) throws SQLException {
        return null;
    }
    
    
}
