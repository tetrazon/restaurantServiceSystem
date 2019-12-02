package dao.dataSourse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static DataSource instance = null;

    private DataSource(){
    }

    public static DataSource getInstance(){
        if (instance==null)
            instance = new DataSource();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Context ctx;
        Connection connection = null;
        try {
            ctx = new InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/postgreSQLpool");
            connection = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return connection;
    }
}