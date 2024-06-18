package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AddCommando implements Commando {

    private final Scanner scanner;
    private final DateService dateService;
    private final StockService stockService;
    private final StockmarketService stockmarketService;

    public AddCommando(Scanner scanner, DateService dateService, StockService stockService, StockmarketService stockmarketService) {
        this.scanner = scanner;
        this.dateService = dateService;
        this.stockService = stockService;
        this.stockmarketService = stockmarketService;
    }

    @Override
    public boolean execute() throws CommandoException {
        System.out.println("Please enter the stock market data you want to add:");

        System.out.println("Do you know the company id you want to add?");
        String searchId = scanner.nextLine();

        if ("no".equalsIgnoreCase(searchId)) {
            System.out.println("What company id do you want to search for? Please enter the first characters:");
            String userCommando = scanner.nextLine();
            try {
                List<Stock> stocks = stockService.searchStockIdPlaceholder(userCommando);
                if (stocks.isEmpty()) {
                    System.out.println("There is currently no company starting with " + userCommando + " in the database.");
                } else {
                    System.out.println("The following companies start with " + userCommando + ":");
                    for (Stock stock : stocks) {
                        System.out.println("ID: " + stock.getId() + " - " + stock.getName());
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // TODO: Company can only be searched for once, then Id must be entered.
        System.out.println("Please enter the company id:");
        String id = scanner.nextLine();
        int stockId = Integer.parseInt(id);
        System.out.println("Please enter the price in Euro:");
        String price = scanner.nextLine();
        float priceParsed = Float.parseFloat(price);
        System.out.println("Please enter the date in dd.mm.yyyy Format:");
        String date = scanner.nextLine();
        Date dateFormatted = dateService.readDate(date);

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