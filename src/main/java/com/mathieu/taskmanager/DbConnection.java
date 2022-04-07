package com.mathieu.taskmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    // Connect to MySQL
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "gt_db";
        String userName = "root";
        String password = "";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password)
    {


        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionURL, userName,
                    password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }
}
