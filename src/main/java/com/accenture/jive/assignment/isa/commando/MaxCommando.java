package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

//TODO: Exception
public class MaxCommando implements Commando {

    private final StockService stockService;
    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public MaxCommando(StockService stockService, StockmarketService stockmarketService, UserInteraction userInteraction) {
        this.stockService = stockService;
        this.stockmarketService = stockmarketService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
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
                userInteraction.failedCommandoSQL();
                e.printStackTrace();
            }
        }

        try {
            int stockId = userInteraction.readCompanyId();
            boolean existStock = stockService.existStock(stockId);
            if (existStock) {
                Stockmarket stockmarket = stockmarketService.showMax(stockId);
                if (stockmarket.getStockId() != null) {
                    userInteraction.maxPrice(stockmarket);
                } else {
                    userInteraction.noEntries();
                }
            } else {
                userInteraction.missingStock();
                execute();
            }
        } catch (SQLException e) {
            userInteraction.failedCommandoSQL();
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "max".equalsIgnoreCase(line);
    }
}