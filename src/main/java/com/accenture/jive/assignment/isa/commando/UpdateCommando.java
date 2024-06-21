package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Industry;
import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import java.sql.SQLException;
import java.util.List;

/**
 * Updates a stock's industry in the database by its ID.
 * Stock ID can be searched for using company name.
 * Industry name can be searched for.
 */

public class UpdateCommando implements Commando {

    private final StockService stockService;
    private final IndustryService industryService;
    private final UserInteraction userInteraction;

    public UpdateCommando(StockService stockService, IndustryService industryService,
                          UserInteraction userInteraction) {
        this.stockService = stockService;
        this.industryService = industryService;
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
                String currentIndustry = stockService.showStockIndustry(stockId);
                userInteraction.showIndustry(currentIndustry);
                String searchIdIndustry = userInteraction.knowIndustry();
                if ("no".equalsIgnoreCase(searchIdIndustry)) {
                    try {
                        boolean shouldRun;
                        do {
                            String searchIndustry = userInteraction.readSearchIndustry();
                            List<Industry> industries = industryService.searchIndustryIdPlaceholder(searchIndustry);
                            userInteraction.printIndustryPlaceholder(industries, searchIndustry);
                            shouldRun = userInteraction.foundIndustry();
                        } while(shouldRun);
                    } catch (SQLException e) {
                        System.out.println(userInteraction.failedCommandoSQL());
                    }
                }
                String industry = userInteraction.readIndustry();
                int industryId = industryService.searchIndustryId(industry);
                stockService.updateStock(stockId, industryId);
                userInteraction.successfulCommando();
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
        return "update".equalsIgnoreCase(line);
    }
}