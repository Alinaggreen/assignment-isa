package com.accenture.jive.assignment.isa.service;

import java.sql.Connection;
import java.sql.Date;
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

    public int addStockmarket(int stockId, float priceParsed, Date date) throws SQLException {
        String sql = "INSERT INTO stockmarket VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, stockId);
        preparedStatement.setFloat(2, priceParsed);
        preparedStatement.setDate(3, date);
        return preparedStatement.executeUpdate();
    }

}
