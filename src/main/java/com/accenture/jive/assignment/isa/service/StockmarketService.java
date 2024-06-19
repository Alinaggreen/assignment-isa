package com.accenture.jive.assignment.isa.service;

import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
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

    public int addStockmarket(int stockId, BigDecimal priceParsed, LocalDate date) throws SQLException {
        String sql = "INSERT INTO stockmarket VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, stockId);
        preparedStatement.setBigDecimal(2, priceParsed);
        preparedStatement.setDate(3, Date.valueOf(date));
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
            stockmarket.setMarketPrice(resultSet.getBigDecimal("market_price"));
            stockmarket.setMarketDate(resultSet.getDate("market_date").toLocalDate());

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
            stockmarket.setMarketPrice(resultSet.getBigDecimal("max"));
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
            stockmarket.setMarketPrice(resultSet.getBigDecimal("min"));
        }

        return stockmarket;
    }

    public List<Stockmarket> exportStockmarket () throws SQLException {
        String sql = "SELECT stock_name, market_price, market_date, industry_name FROM stockmarket \n" +
                "JOIN (SELECT stock_id, stock_name, industry_name FROM stock \n" +
                "JOIN industry ON stock_industry_id = industry_id) AS stockindustry\n" +
                "ON stockmarket.stock_id = stockindustry.stock_id;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Stockmarket> stockmarkets = new ArrayList<>();
        while (resultSet.next()) {
            Stockmarket stockmarket = new Stockmarket();
            stockmarket.setStockName(resultSet.getString("stock_name"));
            stockmarket.setMarketPrice(resultSet.getBigDecimal("market_price"));
            stockmarket.setMarketDate(resultSet.getDate("market_date").toLocalDate());
            stockmarket.setIndustryName(resultSet.getString("industry_name"));

            stockmarkets.add(stockmarket);
        }
        return stockmarkets;
    }
}