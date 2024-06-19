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
    private final UserInteraction userInteraction;

    public MaxCommando(Scanner scanner, StockService stockService, StockmarketService stockmarketService, UserInteraction userInteraction) {
        this.scanner = scanner;
        this.stockService = stockService;
        this.stockmarketService = stockmarketService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        System.out.println("Please enter the company id you want to see the highest price of.");

        System.out.println("Do you know the company id you want to see?");
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