package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection(){
        try {
            if (conn == null){
                Properties properties = loadProperties();
                String url = properties.getProperty("db.url");
                String user = properties.getProperty("db.user");
                String password = properties.getProperty("db.password");
                conn = DriverManager.getConnection(url,user,password);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return conn;
    }


    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")) {

            Properties properties = new Properties();
            properties.load(fs);
            return properties;

        } catch (IOException e){
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection(){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement statement)  {
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

}
