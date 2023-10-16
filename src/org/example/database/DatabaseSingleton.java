package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSingleton {
    private static DatabaseSingleton instance;
    private static Connection connection;

    private String url = "jdbc:mysql://localhost:3306/trial";
    private String userName = "root";
    private String password = "asda";

    private DatabaseSingleton(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url,userName,password);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static synchronized DatabaseSingleton getInstance(){
        if (instance == null){
            instance = new DatabaseSingleton();
        }
        return instance;
    }

    public static Connection getConnection(){
        return connection;
    }
    public static void closeDatabaseConnection(){
        if (connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
