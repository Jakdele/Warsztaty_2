package pl.coderslab.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static  final String DB_URL = "jdbc:mysql://localhost:3306/workshop_2?useSSL=false&characterEncoding=utf8";
    private static  final String DB_USER= "root";
    private static  final String DB_PASS= "coderslab";

    private static DBManager instance;
    private static Connection connection;
    private DBManager () {
    }

    public static DBManager getInstance() {
        if(instance==null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection==null || connection.isClosed()){
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
        }
        return connection;
    }
    public static void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }



}
