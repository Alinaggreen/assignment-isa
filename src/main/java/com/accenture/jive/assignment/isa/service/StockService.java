package com.accenture.jive.assignment.isa.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockService {

    private final Connection connection;

    public StockService(Connection connection) {
        this.connection = connection;
    }

    public void deleteStock() throws SQLException {
        String sql = "DELETE FROM stock";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
    }

    public void addStock(String name, int industryId) throws SQLException {
        String sql = "INSERT IGNORE INTO stock (stock_name, stock_industry_id) VALUES(?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, industryId);
        preparedStatement.execute();
    }

    public int searchStockId(String name) throws SQLException {
        String sqlId = "SELECT stock_id FROM stock WHERE stock_name = ?";
        PreparedStatement preparedStatementId = connection.prepareStatement(sqlId);
        preparedStatementId.setString(1, name);
        ResultSet resultSet = preparedStatementId.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("stock_id");
        } else {
            return 0;
        }
    }
}