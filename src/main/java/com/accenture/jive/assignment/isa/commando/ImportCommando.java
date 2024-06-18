package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ImportCommando implements Commando{

    private final StockService stockService;
    private final IndustryService industryService;
    private final StockmarketService stockmarketService;
    private final DateService dateService;

    public ImportCommando(StockService stockService, IndustryService industryService, StockmarketService stockmarketService, DateService dateService) {
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
        this.dateService = dateService;
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

                String price = fields[1].replace(",", ".").substring(1);
                float priceParsed = Float.parseFloat(price);

                Date date = dateService.readDate(fields[2]);

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

    @Override
    public boolean shouldExecute(String line) {
        return "import".equalsIgnoreCase(line);
    }
}