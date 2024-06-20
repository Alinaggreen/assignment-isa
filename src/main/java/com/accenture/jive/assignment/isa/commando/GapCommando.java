package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

//TODO: Exception
public class GapCommando implements Commando {

    private final StockService stockService;
    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public GapCommando(StockService stockService, StockmarketService stockmarketService, UserInteraction userInteraction) {
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
                execute();
            }

        } catch (SQLException e) {
            userInteraction.failedCommandoSQL();
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "gap".equalsIgnoreCase(line);
    }
}