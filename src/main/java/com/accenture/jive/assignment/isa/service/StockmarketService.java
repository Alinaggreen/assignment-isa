package com.accenture.jive.assignment.isa.service;

import com.accenture.jive.assignment.isa.persistence.Stockmarket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Stockmarket> showStockmarket (int stockId) throws SQLException {
        String sql = "SELECT * FROM stockmarket WHERE stock_id = ? ORDER BY market_date DESC LIMIT 10";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, stockId);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Stockmarket> stockmarkets = new ArrayList<>();
        while(resultSet.next()) {
            Stockmarket stockmarket = new Stockmarket();
            stockmarket.setStockId(stockId);
            stockmarket.setMarketPrice(resultSet.getFloat("market_price"));
            stockmarket.setMarketDate(resultSet.getDate("market_date"));

            stockmarkets.add(stockmarket);
        }

        return stockmarkets;
    }

    public Stockmarket showMax (int stockId) throws SQLException {
        String sql = "SELECT MAX(market_price) AS max FROM stockmarket WHERE stock_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, stockId);
        ResultSet resultSet = preparedStatement.executeQuery();

        Stockmarket stockmarket = new Stockmarket();
        if (resultSet.next()) {
            stockmarket.setStockId(stockId);
            stockmarket.setMarketPrice(resultSet.getFloat("max"));
        }

        return stockmarket;
    }

    public Stockmarket showMin (int stockId) throws SQLException {
        String sql = "SELECT MIN(market_price) AS min FROM stockmarket WHERE stock_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, stockId);
        ResultSet resultSet = preparedStatement.executeQuery();

        Stockmarket stockmarket = new Stockmarket();
        if (resultSet.next()) {
            stockmarket.setStockId(stockId);
            stockmarket.setMarketPrice(resultSet.getFloat("min"));
        }

        return stockmarket;
    }
}