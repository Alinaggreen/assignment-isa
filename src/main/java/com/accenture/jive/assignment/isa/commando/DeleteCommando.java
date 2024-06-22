package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.SQLException;

/**
 * Deletes all data from the database.
 */

public class DeleteCommando implements Commando {

    private final StockService stockService;
    private final IndustryService industryService;
    private final StockmarketService stockmarketService;
    private final UserInteraction userInteraction;

    public DeleteCommando(StockService stockService, IndustryService industryService,
                          StockmarketService stockmarketService, UserInteraction userInteraction) {
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        String userCommando = userInteraction.shouldDelete();
        if ("yes".equalsIgnoreCase(userCommando)) {
            try {
                stockmarketService.deleteStockmarket();
                stockService.deleteStock();
                industryService.deleteIndustry();
                userInteraction.successfulCommando();
            } catch (SQLException e) {
                throw new CommandoException(userInteraction.failedCommandoSQL(), e);
            }
        } else {
            userInteraction.successTermination();
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "delete".equalsIgnoreCase(line);
    }
}