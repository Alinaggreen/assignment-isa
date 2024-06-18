package com.accenture.jive.assignment.isa.service;

import com.accenture.jive.assignment.isa.persistence.Industry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IndustryService {

    private final Connection connection;

    public IndustryService(Connection connection) {
        this.connection = connection;
    }

    public void deleteIndustry() throws SQLException {
        String sql = "DELETE FROM industry";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
    }

    public void addIndustry (String industry) throws SQLException {
        String sql = "INSERT IGNORE INTO industry (industry_name) VALUES(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, industry);
        preparedStatement.execute();
    }

    public int searchIndustryId (String industry) throws SQLException {
        String sqlId = "SELECT industry_id FROM industry WHERE industry_name = ?";
        PreparedStatement preparedStatementId = connection.prepareStatement(sqlId);
        preparedStatementId.setString(1, industry);
        ResultSet resultSet = preparedStatementId.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("industry_id");
        } else {
            return 0;
        }
    }

    public List<Industry> listIndustry () throws SQLException {
        String sql = "SELECT industry_id, industry_name, COUNT(stock_name) as stock_count " +
                "FROM industry JOIN stock ON industry_id = stock_industry_id " +
                "GROUP BY industry_name ORDER BY industry_id";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Industry> industries = new ArrayList<>();
        while (resultSet.next()) {
            Industry industry = new Industry();
            industry.setId(resultSet.getInt("industry_id"));
            industry.setName(resultSet.getString("industry_name"));
            industry.setStockCount(resultSet.getInt("stock_count"));
            industries.add(industry);
        }

        return industries;
    }
}