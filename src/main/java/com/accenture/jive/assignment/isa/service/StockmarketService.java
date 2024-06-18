package com.accenture.jive.assignment.isa.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockmarketService {

    private final Connection connection;

    public StockmarketService(Connection connection) {
        this.connection = connection;
    }

    public void deleteStockmarket () throws SQLException {
        String sql = "DELETE FROM stockmarket";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
    }

}
