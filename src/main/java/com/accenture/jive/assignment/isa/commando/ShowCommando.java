package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ShowCommando implements Commando {

    private final Scanner scanner;
    private final StockService stockService;
    private final StockmarketService stockmarketService;

    public ShowCommando(Scanner scanner, StockService stockService, StockmarketService stockmarketService) {
        this.scanner = scanner;
        this.stockService = stockService;
        this.stockmarketService = stockmarketService;
    }

    @Override
    public boolean execute() throws CommandoException {
        System.out.println("Please enter the company id you want to see the last ten prices of.");

        System.out.println("Do you know the company id you want to add?");
        String searchId = scanner.nextLine();

        if ("no".equalsIgnoreCase(searchId)) {
            System.out.println("What company id do you want to search for? Please enter the first characters:");
            String userCommando = scanner.nextLine();
            try {
                List<Stock> stocks = stockService.searchStockIdPlaceholder(userCommando);
                System.out.println("The following companies start with " + userCommando + ":");
                for (Stock stock : stocks) {
                    System.out.println("ID: " + stock.getId() + " - " + stock.getName());
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
           List<Stockmarket> stockmarkets = stockmarketService.showStockmarket(stockId);
            System.out.println("These are the last ten prices for your desired company:");
            for (Stockmarket stockmarket : stockmarkets) {
                System.out.println(stockmarket.getMarketPrice() + " € on " + stockmarket.getMarketDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "show".equalsIgnoreCase(line);
    }
}