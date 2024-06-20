package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.StockService;
import java.sql.SQLException;
import java.util.List;

//TODO: Exception
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
                String userCommando = userInteraction.readSearchCompany();
                List<Stock> stocks = stockService.searchStockIdPlaceholder(userCommando);
                userInteraction.printCompany(stocks, userCommando);
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