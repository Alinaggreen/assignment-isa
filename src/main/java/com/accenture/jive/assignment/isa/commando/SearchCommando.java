package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.StockService;
import java.sql.SQLException;
import java.util.List;

/**
 * Shows id for a stock by its name.
 * Supports name placeholders.
 */

public class SearchCommando implements Commando {

    private final StockService stockService;
    private final UserInteraction userInteraction;

    public SearchCommando(StockService stockService, UserInteraction userInteraction) {
        this.stockService = stockService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        try {
            boolean shouldRun;
            do {
                String searchStock = userInteraction.readSearchCompany();
                List<Stock> stocks = stockService.searchStockIdPlaceholder(searchStock);
                userInteraction.printCompanyPlaceholder(stocks, searchStock);
                shouldRun = userInteraction.foundCompany();
            } while(shouldRun);
        } catch (SQLException e) {
            throw new CommandoException(userInteraction.failedCommandoSQL(), e);
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "search".equalsIgnoreCase(line);
    }
}