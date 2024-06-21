package com.accenture.jive.assignment.isa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Establishes database connection.
 */

public class Connector {

    public Connection getConnection() throws SQLException {
        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "");

        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/assignment_isa",
                connectionProps);

        System.out.println("Connected to database");
        return conn;
    }
}