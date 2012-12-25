/*
 * Copyright (c) 2012 Qunar.com. All Rights Reserved.
 */

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author yubao.fu created on 12/25/12 7:03 PM
 * @version $Id$
 * serverPrepareStatement 与clientPrepareStatement的不同
 */
public class BatchTest {

    public static void main(String[] args) throws SQLException {
        Connection conn = DemoConstants.getConnection();
        PreparedStatement statement = conn.prepareStatement("update test set version = ? where id = ? ");
        PrintWriter printWriter = new PrintWriter(System.out);
        DriverManager.setLogWriter(printWriter);
        int i = 0;
        for (; i < 10; i++) {
            i++;
            statement.setInt(1, 50);
            statement.setInt(2, 387312);
            statement.addBatch();
        }
        statement.executeBatch();
        System.out.println("succ");
    }
}
