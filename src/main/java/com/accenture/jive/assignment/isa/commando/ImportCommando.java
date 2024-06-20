package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

//TODO: Exception
public class ImportCommando implements Commando{

    private final StockService stockService;
    private final IndustryService industryService;
    private final StockmarketService stockmarketService;
    private final DateService dateService;
    private final UserInteraction userInteraction;

    public ImportCommando(StockService stockService, IndustryService industryService, StockmarketService stockmarketService, DateService dateService, UserInteraction userInteraction) {
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
        this.dateService = dateService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        String filePath = userInteraction.readImportName();

        //TODO: Exception FileNotFoundException
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter(";");
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");

                String price = fields[1].replace(",", ".").substring(1);
                BigDecimal priceParsed = new BigDecimal(price);

                LocalDate date = dateService.importDate(fields[2]);

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
            userInteraction.successImport();
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