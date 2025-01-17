package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Imports all data from a CSV file to the database.
 */

public class ImportCommando implements Commando{

    private final StockService stockService;
    private final IndustryService industryService;
    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public ImportCommando(StockService stockService, IndustryService industryService,
                          StockmarketService stockmarketService, UserInteraction userInteraction) {
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException, RuntimeException {
        String filePath = userInteraction.readImportName();
        String fileExtension = filePath.substring(filePath.lastIndexOf(".")+1);
        if (!"csv".equals(fileExtension)) {
            userInteraction.missingExtension();
            execute();
        } else {
            try (Scanner scanner = new Scanner(new File(filePath))) {
                scanner.useDelimiter(";");
                scanner.nextLine();
                String userCommando = userInteraction.shouldImport();
                if ("yes".equalsIgnoreCase(userCommando)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] fields = line.split(";");

                        String price = fields[1].replace(",", ".").substring(1);
                        BigDecimal priceParsed = new BigDecimal(price);
                        LocalDate date = LocalDate.parse(fields[2], DateTimeFormatter.ofPattern("dd.MM.yy"));
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
                    userInteraction.successfulCommando();
                } else {
                    userInteraction.successTermination();
                }
            } catch (NumberFormatException e) {
                System.out.println(userInteraction.failedCommandoNumberFormat());
            } catch (DateTimeParseException e) {
                System.out.println(userInteraction.failedCommandoDateTime());
            } catch (FileNotFoundException e) {
                System.out.println(userInteraction.failedCommandoFile());
            } catch (SQLException e) {
                throw new CommandoException(userInteraction.failedCommandoSQL(), e);
            }
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "import".equalsIgnoreCase(line);
    }
}