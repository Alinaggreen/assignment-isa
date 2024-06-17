package com.accenture.jive.assignment.isa.commando;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InsertCommando implements Commando{

    private final Connection connection;

    public InsertCommando(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean execute() throws CommandoException {

        // TODO: change path back to STOCK_DATA.csv
        String filePath = "C://dev1//isa//STOCK_DATA - Copy.csv";

        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter(";");

            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");

                String name = fields[0];
                String price = fields[1].replace(",", ".").substring(2);
                float priceParsed = Float.parseFloat(price);

                Date date = readDate(fields);

                /*String sql = "INSERT INTO stockmarket VALUES (1, 1, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDate(1, date);
                System.out.println(preparedStatement.executeUpdate());*/

                String industry = fields[3];

                String sqlIndustry = "INSERT IGNORE INTO industry (industry_name) VALUES(?)";
                PreparedStatement preparedStatementIndustry = connection.prepareStatement(sqlIndustry);
                preparedStatementIndustry.setString(1, industry);
                preparedStatementIndustry.execute();

                String sqlIndustryId = "SELECT industry_id FROM industry WHERE industry_name = ?";
                PreparedStatement preparedStatementIndustryId = connection.prepareStatement(sqlIndustryId);
                preparedStatementIndustryId.setString(1, industry);
                ResultSet resultSet = preparedStatementIndustryId.executeQuery();
                if (resultSet.next()) {
                    int industryId = resultSet.getInt("industry_id");
                    System.out.println(industryId);
                }

                // TODO: set unknown industry for n/a
                // TODO: insert stock with indsutry_id if not yet exists
                // TODO: insert stockmarket with price & date if not yet exists

                for (String field : fields) {
                    System.out.println(field + " ");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }

        return true;
    }

    private static Date readDate(String[] fields) {
        String insertDate = fields[2];
        String[] dayMonthYear = insertDate.split("\\.");
        String day = dayMonthYear[0];
        String month = dayMonthYear[1];
        String year = dayMonthYear[2];
        int dayParsed = Integer.parseInt(day);
        int monthParsed = Integer.parseInt(month);
        int yearParsed = Integer.parseInt(year);
        LocalDate localdate = LocalDate.of(yearParsed, monthParsed, dayParsed);
        Date date = Date.valueOf(localdate);
        return date;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "insert".equalsIgnoreCase(line);
    }
}