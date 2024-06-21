package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Shows difference between highest and lowest price ever recorded for a stock by its ID.
 * Stock ID can be searched for using company name.
 */

public class GapCommando implements Commando {

    private final StockService stockService;
    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public GapCommando(StockService stockService, StockmarketService stockmarketService,
                       UserInteraction userInteraction) {
        this.stockService = stockService;
        this.stockmarketService = stockmarketService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        String userCommando = userInteraction.knowCompany();
        if ("no".equalsIgnoreCase(userCommando)) {
            try {
                boolean shouldRun;
                do {
                    String searchStock = userInteraction.readSearchCompany();
                    List<Stock> stocks = stockService.searchStockIdPlaceholder(searchStock);
                    userInteraction.printCompanyPlaceholder(stocks, searchStock);
                    shouldRun = userInteraction.foundCompany();
                } while(shouldRun);
            } catch (SQLException e) {
                System.out.println(userInteraction.failedCommandoSQL());
            }
        }

        try {
            int stockId = userInteraction.readCompanyId();
            boolean existStock = stockService.existStock(stockId);
            if (existStock) {
                Stockmarket stockmarketMax = stockmarketService.showMax(stockId);
                Stockmarket stockmarketMin = stockmarketService.showMin(stockId);

                // TODO: max == min -> only 1 entry
                // TODO : case no entry
                BigDecimal gapPrice = stockmarketMax.getMarketPrice().subtract(stockmarketMin.getMarketPrice());
                userInteraction.maxPrice(stockmarketMax);
                userInteraction.minPrice(stockmarketMin);
                userInteraction.gapPrice(gapPrice);
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
        return "gap".equalsIgnoreCase(line);
    }
}