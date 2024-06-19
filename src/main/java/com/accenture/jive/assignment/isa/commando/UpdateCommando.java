package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UpdateCommando implements Commando {

    private final Scanner scanner;
    private final StockService stockService;
    private final IndustryService industryService;
    private final UserInteraction userInteraction;

    public UpdateCommando(Scanner scanner, StockService stockService, IndustryService industryService, UserInteraction userInteraction) {
        this.scanner = scanner;
        this.stockService = stockService;
        this.industryService = industryService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {

        System.out.println("Please enter the company id you want to update the industry of.");

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

        try {
            String industry = stockService.showStockIndustry(stockId);
            System.out.println("The company is currently assigned to the following industry: " + industry);

            System.out.println("Which industry should the company be assigned to instead?");
            industry = scanner.nextLine();
            int industryId = industryService.searchIndustryId(industry);

            int updatedRows = stockService.updateStock(stockId, industryId);
            System.out.println("You successfully updated " + updatedRows + " industry.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "update".equalsIgnoreCase(line);
    }
}