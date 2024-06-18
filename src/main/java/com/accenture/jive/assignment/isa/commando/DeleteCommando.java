package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteCommando implements Commando {

    private final Scanner scanner;
    private final StockService stockService;
    private final IndustryService industryService;
    private final StockmarketService stockmarketService;

    public DeleteCommando(Scanner scanner, StockService stockService, IndustryService industryService, StockmarketService stockmarketService) {
        this.scanner = scanner;
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
    }

    @Override
    public boolean execute() {
        System.out.println("Are you sure you want to delete everything from the database?");
        String userCommando = scanner.nextLine();
        if ("yes".equalsIgnoreCase(userCommando)) {
            try {
                // TODO: Muss hier jeder DELETE einzeln ausgeführt werden?
                stockmarketService.deleteStockmarket();
                stockService.deleteStock();
                industryService.deleteIndustry();
                System.out.println("You successfully deleted everything from the database!");
            } catch (SQLException e) {
                System.out.println("SQLException");
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "delete".equalsIgnoreCase(line);
    }
}