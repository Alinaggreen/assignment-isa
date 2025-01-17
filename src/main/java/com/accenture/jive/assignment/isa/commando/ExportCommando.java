package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Exports all data from the database to a CSV file.
 */

public class ExportCommando implements Commando {

    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public ExportCommando(StockmarketService stockmarketService, UserInteraction userInteraction) {
        this.stockmarketService = stockmarketService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        List<String[]> csvData = new ArrayList<>();
        String[] header = {"stockname", "price", "price_date", "industry"};
        csvData.add(header);

        String filePath = userInteraction.readExportName();
        String fileExtension = filePath.substring(filePath.lastIndexOf(".")+1);
        if (!"csv".equals(fileExtension)) {
            userInteraction.missingExtension();
            execute();
        } else {
            try (CSVWriter writer = new CSVWriter(new FileWriter(filePath), ';',
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.RFC4180_LINE_END)) {

                List<Stockmarket> stockmarkets = stockmarketService.exportStockmarket();
                for (Stockmarket stockmarket : stockmarkets) {
                    String date = DateTimeFormatter.ofPattern("dd.MM.yy").format(stockmarket.getMarketDate());
                    String[] record = {stockmarket.getStockName(), "€" + stockmarket.getMarketPrice(),
                            date, stockmarket.getIndustryName()};
                    csvData.add(record);
                }

                String userCommando = userInteraction.shouldExport();
                if ("yes".equalsIgnoreCase(userCommando)) {
                    writer.writeAll(csvData);
                    userInteraction.successfulCommando();
                } else {
                    userInteraction.successTermination();
                }
            } catch (SQLException e) {
                throw new CommandoException(userInteraction.failedCommandoSQL(), e);
            } catch (IOException e) {
                throw new CommandoException(userInteraction.failedCommandoIO(), e);
            }
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "export".equalsIgnoreCase(line);
    }
}