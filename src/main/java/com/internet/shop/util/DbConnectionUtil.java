package com.internet.shop.util;

import com.internet.shop.exeptions.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "Vi4ft5q7Ktrr9T6toPcN");
        String url = "jdbc:mysql://localhost:3306/internet_shop?serverTimezone=UTC";

        try {
            return DriverManager.getConnection(url, connectionProps);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't establish the connection to DB:/n" + e);
        }
    }
}
