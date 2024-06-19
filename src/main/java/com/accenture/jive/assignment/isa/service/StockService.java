package com.accenture.jive.assignment.isa.service;

import com.accenture.jive.assignment.isa.persistence.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Stock> searchStockIdPlaceholder(String userCommando) throws SQLException {
        String sql = "SELECT * FROM stock WHERE stock_name LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userCommando + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Stock> stocks = new ArrayList<>();
        while (resultSet.next()) {
            Stock stock = new Stock();
            stock.setId(resultSet.getInt("stock_id"));
            stock.setName(resultSet.getString("stock_name"));

            stocks.add(stock);
        }
        return stocks;
    }

    public String showStockIndustry (int stockId) throws SQLException {
        String sql = "SELECT * FROM stock JOIN industry ON stock.stock_industry_id = industry.industry_id WHERE stock_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, stockId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("industry_name");
        } else {
            return null;
        }
    }

    public int updateStock (int stockId, int industryId) throws SQLException {
        String sql = "UPDATE stock SET stock_industry_id = ? WHERE stock_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, industryId);
        preparedStatement.setInt(2, stockId);
        return preparedStatement.executeUpdate();
    }
}