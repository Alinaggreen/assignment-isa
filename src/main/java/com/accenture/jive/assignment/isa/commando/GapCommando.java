package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GapCommando implements Commando {

    private final Scanner scanner;
    private final StockService stockService;
    private final StockmarketService stockmarketService;

    public GapCommando(Scanner scanner, StockService stockService, StockmarketService stockmarketService) {
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

        try {
            Stockmarket stockmarketMax = stockmarketService.showMax(stockId);
            BigDecimal priceMax = stockmarketMax.getMarketPrice();
            Stockmarket stockmarketMin = stockmarketService.showMin(stockId);
            BigDecimal priceMin = stockmarketMin.getMarketPrice();

            // TODO: max == min -> only 1 entry
            BigDecimal gap = priceMax.subtract(priceMin);
            System.out.println("The highest price was " + priceMax + "€ - the lowest price was " + priceMin + "€.");
            System.out.println("The difference between highest and lowest price there is " + gap + "€.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "gap".equalsIgnoreCase(line);
    }
}