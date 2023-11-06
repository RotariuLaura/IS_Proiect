package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnectionWrapper {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static final int TIMEOUT = 5;
    private Connection connection;

    public JDBCConnectionWrapper(String schema){
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + schema, USER, PASSWORD);
            //Bootstrap
            createTables();
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException{
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS book(" +
                "id bigint NOT NULL AUTO_INCREMENT," +
                "author varchar(500) NOT NULL," +
                "title  varchar(500) NOT NULL," +
                "publishedDate datetime DEFAULT  NULL," +
                "PRIMARY KEY(id)," +
                "UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8";
        statement.execute(sql);
    }

    public boolean testConnection() throws SQLException{
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection() {

        return connection;
    }
}
