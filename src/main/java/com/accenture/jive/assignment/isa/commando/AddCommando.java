package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AddCommando implements Commando {

    private final Scanner scanner;
    private final DateService dateService;
    private final StockService stockService;
    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public AddCommando(Scanner scanner, DateService dateService, StockService stockService, StockmarketService stockmarketService, UserInteraction userInteraction) {
        this.scanner = scanner;
        this.dateService = dateService;
        this.stockService = stockService;
        this.stockmarketService = stockmarketService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        System.out.println("Please enter the stock market data you want to add:");

        System.out.println("Do you know the company id you want to add?");
        String searchId = scanner.nextLine();

        if ("no".equalsIgnoreCase(searchId)) {
            try {
                boolean shouldRun;
                do {
                    String userCommando = userInteraction.readSearchCompany();
                    List<Stock> stocks = stockService.searchStockIdPlaceholder(userCommando);
                    userInteraction.printCompany(stocks, userCommando);
                    shouldRun = userInteraction.foundCompany();
                } while(shouldRun);
            } catch (SQLException e) {
                System.out.println("SQLException");
                e.printStackTrace();
            }
        }

        System.out.println("Please enter the company id:");
        String id = scanner.nextLine();
        int stockId = Integer.parseInt(id);
        System.out.println("Please enter the price in Euro:");
        String price = scanner.nextLine();
        BigDecimal priceParsed = new BigDecimal(price);
        System.out.println("Please enter the date in dd.mm.yyyy Format:");
        String date = scanner.nextLine();
        LocalDate dateFormatted = dateService.importDate(date);

        try {
            int addedRows = stockmarketService.addStockmarket(stockId, priceParsed, dateFormatted);
            System.out.println("You successfully added " + addedRows + " new stock market entry.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "add".equalsIgnoreCase(line);
    }
}