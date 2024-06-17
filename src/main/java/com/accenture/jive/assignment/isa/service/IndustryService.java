package com.accenture.jive.assignment.isa.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustryService {

    private final Connection connection;

    public IndustryService(Connection connection) {
        this.connection = connection;
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
}