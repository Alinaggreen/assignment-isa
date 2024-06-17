package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.StockService;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class ImportCommando implements Commando{

    private final Connection connection;
    private final StockService stockService;

    public ImportCommando(Connection connection, StockService stockService) {
        this.connection = connection;
        this.stockService = stockService;
    }

    @Override
    public boolean execute() throws CommandoException {

        // TODO: change path back to STOCK_DATA.csv
        String filePath = "C://dev1//isa//STOCK_DATA.csv";

        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter(";");

            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");

                String price = fields[1].replace(",", ".").substring(2);

                String name = fields[0];
                stockService.addStock(name, readIndustry(fields[3]));
                int stockId = stockService.searchStockId(name);

                String sql = "INSERT INTO stockmarket VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, stockId);
                preparedStatement.setFloat(2, Float.parseFloat(price));
                preparedStatement.setDate(3, readDate(fields));
            }
            System.out.println("You successfully imported the data to the database!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }

        return true;
    }

    public int readIndustry(String industry) throws SQLException {
        if ("n/a".equals(industry)) {
            industry = "Unknown";
        }
        String sql = "INSERT IGNORE INTO industry (industry_name) VALUES(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, industry);
        preparedStatement.execute();

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

    public Date readDate(String[] fields) {
        String importDate = fields[2];
        String[] dayMonthYear = importDate.split("\\.");
        String day = dayMonthYear[0];
        String month = dayMonthYear[1];
        String year = dayMonthYear[2];
        int dayParsed = Integer.parseInt(day);
        int monthParsed = Integer.parseInt(month);
        int yearParsed = Integer.parseInt(year);
        LocalDate localdate = LocalDate.of(yearParsed, monthParsed, dayParsed);
        return Date.valueOf(localdate);
    }

    @Override
    public boolean shouldExecute(String line) {
        return "import".equalsIgnoreCase(line);
    }
}