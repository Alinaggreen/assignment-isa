package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.StockService;

import java.sql.SQLException;
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

        System.out.println("What company do you want to search for? Please enter the first characters:");
        String userCommand = scanner.nextLine();

        try {
            stockService.searchStockIdPlaceholder(userCommand);
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
