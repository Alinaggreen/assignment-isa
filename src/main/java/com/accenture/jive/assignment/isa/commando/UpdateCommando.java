package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import java.sql.SQLException;
import java.util.List;

//TODO: Exception
public class UpdateCommando implements Commando {

    private final StockService stockService;
    private final IndustryService industryService;
    private final UserInteraction userInteraction;

    public UpdateCommando(StockService stockService, IndustryService industryService, UserInteraction userInteraction) {
        this.stockService = stockService;
        this.industryService = industryService;
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
                String industry = stockService.showStockIndustry(stockId);
                userInteraction.showIndustry(industry);

                //TODO: user must know industry name
                industry = userInteraction.readIndustry();
                int industryId = industryService.searchIndustryId(industry);
                int updatedRows = stockService.updateStock(stockId, industryId);
                userInteraction.successfulCommando();
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
        return "update".equalsIgnoreCase(line);
    }
}