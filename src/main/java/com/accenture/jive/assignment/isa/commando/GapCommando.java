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
    private final UserInteraction userInteraction;

    public GapCommando(Scanner scanner, StockService stockService, StockmarketService stockmarketService, UserInteraction userInteraction) {
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

        try {
            int stockId = userInteraction.readCompanyId();
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