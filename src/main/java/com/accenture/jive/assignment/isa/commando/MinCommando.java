package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MinCommando implements Commando {

    private final Scanner scanner;
    private final StockService stockService;
    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public MinCommando(Scanner scanner, StockService stockService, StockmarketService stockmarketService, UserInteraction userInteraction) {
        this.scanner = scanner;
        this.stockService = stockService;
        this.stockmarketService = stockmarketService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        userInteraction.startCommando();
        String searchId = userInteraction.knowCompany();

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

        // TODO: Also output latest date on which the price was reached?
        try {
            int stockId = userInteraction.readCompanyId();
            Stockmarket stockmarket = stockmarketService.showMin(stockId);
            if (stockmarket.getStockId() != null) {
                BigDecimal minPrice = stockmarket.getMarketPrice();
                userInteraction.minPrice(minPrice);
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
        return "min".equalsIgnoreCase(line);
    }

}