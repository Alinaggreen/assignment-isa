package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MaxCommando implements Commando {

    private final Scanner scanner;
    private final StockService stockService;
    private final StockmarketService stockmarketService;

    public MaxCommando(Scanner scanner, StockService stockService, StockmarketService stockmarketService) {
        this.scanner = scanner;
        this.stockService = stockService;
        this.stockmarketService = stockmarketService;
    }

    @Override
    public boolean execute() throws CommandoException {
        System.out.println("Please enter the company id you want to see the highest price of.");

        System.out.println("Do you know the company id you want to see?");
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

        // TODO: Also output latest date on which the price was reached?
        try {
            Stockmarket stockmarket = stockmarketService.showMax(stockId);
            if (stockmarket.getStockId() != null) {
                System.out.println("The highest price was " + stockmarket.getMarketPrice() + "€.");
            } else {
                System.out.println("There are currently no stock market entries for this company.");
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "max".equalsIgnoreCase(line);
    }
}