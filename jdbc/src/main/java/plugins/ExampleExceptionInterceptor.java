/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package plugins;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ExceptionInterceptor;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author tofarmer
 */
public class ExampleExceptionInterceptor implements ExceptionInterceptor {

    @Override
    public SQLException interceptException(SQLException sqle, Connection cnctn) {
        return new SQLException("Bad news!", sqle);

    }

    @Override
    public void init(Connection cnctn, Properties prprts) throws SQLException {
        
    }

    @Override
    public void destroy() {
    }
}
