package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExportCommando implements Commando {

    private final StockmarketService stockmarketService;

    public ExportCommando(StockmarketService stockmarketService) {
        this.stockmarketService = stockmarketService;
    }

    @Override
    public boolean execute() throws CommandoException {

        List<String[]> csvData = new ArrayList<>();
        String[] header = {"stockname", "price", "price_date", "industry"};
        csvData.add(header);

        try {
            List<Stockmarket> stockmarkets = stockmarketService.exportStockmarket();

            for (Stockmarket stockmarket : stockmarkets) {
                String[] record = {stockmarket.getStockName(), String.valueOf(stockmarket.getMarketPrice()),
                        String.valueOf(stockmarket.getMarketDate()), stockmarket.getIndustryName()};

                csvData.add(record);
            }

            try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter("C://dev1//isa//test.csv"))
                    .withSeparator(';')
                    .build()) {
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