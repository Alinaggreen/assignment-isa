package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class ImportCommando implements Commando{

    private final StockService stockService;
    private final IndustryService industryService;
    private final StockmarketService stockmarketService;

    public ImportCommando(StockService stockService, IndustryService industryService, StockmarketService stockmarketService) {
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
    }

    @Override
    public boolean execute() throws CommandoException {

        String filePath = "C://dev1//isa//STOCK_DATA.csv";

        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter(";");

            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");

                String price = fields[1].replace(",", ".").substring(2);
                float priceParsed = Float.parseFloat(price);

                Date date = readDate(fields[2]);

                String name = fields[0];
                String industry = fields[3];
                if ("n/a".equals(industry)) {
                    industry = "Unknown";
                }
                industryService.addIndustry(industry);
                int industryId = industryService.searchIndustryId(industry);
                stockService.addStock(name, industryId);
                int stockId = stockService.searchStockId(name);

                stockmarketService.addStockmarket(stockId, priceParsed, date);
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

    public Date readDate(String importDate) {
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