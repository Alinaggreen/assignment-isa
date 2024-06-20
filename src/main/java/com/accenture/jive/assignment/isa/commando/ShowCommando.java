package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.SQLException;
import java.util.List;

public class ShowCommando implements Commando {

    private final StockService stockService;
    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public ShowCommando(StockService stockService, StockmarketService stockmarketService,
                        UserInteraction userInteraction) {
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
                throw new CommandoException(userInteraction.failedCommandoSQL(), e);
            }
        }

        try {
            int stockId = userInteraction.readCompanyId();
            boolean existStock = stockService.existStock(stockId);
            if (existStock) {
                List<Stockmarket> stockmarkets = stockmarketService.showStockmarket(stockId);
                userInteraction.printPrice(stockmarkets);
            } else {
                userInteraction.missingStock();
            }
        } catch (SQLException e) {
            throw new CommandoException(userInteraction.failedCommandoSQL(), e);
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "show".equalsIgnoreCase(line);
    }
}