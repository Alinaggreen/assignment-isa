package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExportCommando implements Commando {

    private final StockmarketService stockmarketService;
    private final DateService dateService;

    public ExportCommando(StockmarketService stockmarketService, DateService dateService) {
        this.stockmarketService = stockmarketService;
        this.dateService = dateService;
    }

    @Override
    public boolean execute() throws CommandoException {

        List<String[]> csvData = new ArrayList<>();
        String[] header = {"stockname", "price", "price_date", "industry"};
        csvData.add(header);

        try {
            List<Stockmarket> stockmarkets = stockmarketService.exportStockmarket();

            for (Stockmarket stockmarket : stockmarkets) {
                String price = "€" + stockmarket.getMarketPrice();
                String[] record = {stockmarket.getStockName(), price,
                        dateService.exportDate(stockmarket.getMarketDate()), stockmarket.getIndustryName()};

                csvData.add(record);
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter("C://dev1//isa//test.csv"), ';',
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.RFC4180_LINE_END)) {
                writer.writeAll(csvData);

            } catch (IOException e) {
                System.out.println("Exception");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "export".equalsIgnoreCase(line);
    }
}