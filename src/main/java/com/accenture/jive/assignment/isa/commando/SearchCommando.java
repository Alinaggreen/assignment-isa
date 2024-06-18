package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.StockService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SearchCommando implements Commando {

    private final Scanner scanner;
    private final StockService stockService;

    public SearchCommando(Scanner scanner, StockService stockService) {
        this.scanner = scanner;
        this.stockService = stockService;
    }

    @Override
    public boolean execute() throws CommandoException {

        try {
            boolean shouldRun = true;
            do {
                System.out.println("What company id do you want to search for? Please enter the first characters:");
                String userCommando = scanner.nextLine();

                List<Stock> stocks = stockService.searchStockIdPlaceholder(userCommando);

                if (stocks.isEmpty()) {
                    System.out.println("There is currently no company starting with " + userCommando + " in the database.");
                } else {
                    System.out.println("The following companies start with " + userCommando + ":");
                    for (Stock stock : stocks) {
                        System.out.println("ID: " + stock.getId() + " - " + stock.getName());
                    }
                }

                System.out.println("Did you find the desired company id?");
                userCommando = scanner.nextLine();
                if ("yes".equalsIgnoreCase(userCommando)) {
                    shouldRun = false;
                }

            } while(shouldRun);

        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "search".equalsIgnoreCase(line);
    }
}